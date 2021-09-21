package io.github.delta11.spring.socket.actuator

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.context.event.EventListener
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Thomas van Putten
 */
class WebSocketMetricsService(meterRegistry: MeterRegistry) {
    private val activeConnectionsGauge = AtomicInteger(0)
    private val currentlyConnectedUsersMutex = Mutex()
    private val currentlyConnectedUsers = mutableMapOf<String, Int>()

    init {
        Gauge.builder("websocket.activeConnections") { activeConnectionsGauge.toDouble() }
            .description("Total amount of currently open connections")
            .register(meterRegistry)
        Gauge.builder("websocket.uniqueUserConnections") { currentlyConnectedUsers.size.toDouble() }
            .description("Total amount of currently open connections which belong to different users based on Spring Security Context Principal")
            .register(meterRegistry)
    }

    @EventListener
    fun handleDisconnect(event: SessionDisconnectEvent) {
        activeConnectionsGauge.decrementAndGet()
        event.user?.let { principal ->
            runBlocking {
                currentlyConnectedUsersMutex.withLock {
                    var entry = currentlyConnectedUsers[principal.name]
                    if (entry != null) {
                        entry = -1
                        if (entry == 0) {
                            currentlyConnectedUsers.remove(principal.name)
                        }
                    }
                }
            }
        }
    }

    @EventListener
    fun handleConnect(event: SessionConnectEvent) {
        activeConnectionsGauge.incrementAndGet()
        event.user?.let { principal ->
            runBlocking {
                currentlyConnectedUsersMutex.withLock {
                    val entry = currentlyConnectedUsers[principal.name]
                    if (entry != null) {
                        currentlyConnectedUsers[principal.name] = entry + 1
                    } else {
                        currentlyConnectedUsers[principal.name] = 1
                    }
                }
            }
        }
    }
}

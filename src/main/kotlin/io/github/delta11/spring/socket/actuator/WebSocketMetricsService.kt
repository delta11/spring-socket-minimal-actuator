package io.github.delta11.spring.socket.actuator

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.messaging.simp.user.SimpUserRegistry

/**
 * @author Thomas van Putten
 */
class WebSocketMetricsService(meterRegistry: MeterRegistry, simpUserRegistry: SimpUserRegistry) {

    init {
        Gauge.builder("websocket.userCount") { simpUserRegistry.userCount }
            .description("The count of all connected users")
            .register(meterRegistry)
    }
}

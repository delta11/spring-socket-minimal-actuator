package me.thomasvanputten.spring.socket.actuator

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
open class WebSocketActuatorAutoConfiguration {
    @Bean
    @Lazy(false)
    @ConditionalOnProperty(value = ["management.metrics.export.socket"], havingValue = "true", matchIfMissing = true)
    open fun webSocketMetricsService(meterRegistry: MeterRegistry) = WebSocketMetricsService(meterRegistry)
}

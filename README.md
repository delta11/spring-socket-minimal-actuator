# Spring socket minimal actuator

Get the minimal [Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html) metrics from
your [Spring Socket](https://spring.io/guides/gs/messaging-stomp-websocket/).

## Configuration

When using Spring Boot, it will auto-configure and can be turned off by setting `management.metrics.export.socket` to
false.

When configuring manually give the class `me.thomasvanputten.spring.socket.actuator.WebSocketMetricsService`
a `io.micrometer.core.instrument.MeterRegistry` and you should be in business.


## Metrics

This library just exposes 1 metrics `websocket.userCount` The count of all connected users

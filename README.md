# Spring socket minimal actuator

Get the minimal [Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html) metrics from
your [Spring Socket](https://spring.io/guides/gs/messaging-stomp-websocket/).

## Configuration

When using Spring Boot, it will auto-configure and can be turned off by setting `management.metrics.export.socket` to
false.

When configuring manually give the class `me.thomasvanputten.spring.socket.actuator.WebSocketMetricsService`
a `io.micrometer.core.instrument.MeterRegistry` and you should be in business.


## Metrics

This library just exposes 2 metrics

* `websocket.activeConnections` Total amount of currently open connections
* `websocket.uniqueUserConnections` Total amount of currently open connections which belong to different users based on Spring Security Context Principal

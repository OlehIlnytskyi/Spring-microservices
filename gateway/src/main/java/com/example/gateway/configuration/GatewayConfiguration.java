package com.example.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("hangar-service-route", r -> r
                        .path("/hangar/**")
                        .filters(f -> f
                                .rewritePath("/hangar/(?<segment>.*)", "/api/hangar/${segment}"))
                        .uri("lb://hangar-service"))

                .route("orders-service-route", r -> r
                        .path("/orders/**")
                        .filters(f -> f
                                .rewritePath("/orders/(?<segment>.*)", "/api/orders/${segment}"))
                        .uri("lb://orders-service"))
                .build();
    }
}

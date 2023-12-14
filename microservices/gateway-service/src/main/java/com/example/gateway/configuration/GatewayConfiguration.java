package com.example.gateway.configuration;

import com.example.gateway.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfiguration {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("security-service-route", r -> r
                        .path("/security/**")
                        .filters(f -> f
                                .rewritePath("/security/(?<segment>.*)", "/api/security/${segment}"))
                        .uri("lb://security-service")
                )
                .route("hangar-service-route", r -> r
                        .path("/hangar/**")
                        .filters(f -> f
//                                .filter(authenticationFilter)
                                .rewritePath("/hangar/(?<segment>.*)", "/api/hangar/${segment}"))
                        .uri("lb://hangar-service"))

                .route("orders-service-route", r -> r
                        .path("/orders/**")
                        .filters(f -> f
//                                .filter(authenticationFilter)
                                .rewritePath("/orders/(?<segment>.*)", "/api/orders/${segment}"))
                        .uri("lb://orders-service"))
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClient(){
        return WebClient.builder();
    }

}

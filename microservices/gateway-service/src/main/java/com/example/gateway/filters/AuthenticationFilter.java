package com.example.gateway.filters;

import com.example.gateway.service.discovery.ServiceNames;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RefreshScope
@Component
@Log
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    private WebClient.Builder webBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if (isAuthMissing(request)) {
            log.info("No token");
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        }

        final String token = getAuthHeader(request);
        final String url = "lb://" + ServiceNames.SECURITY_SERVICE + "/api/security/checkToken" ;

        return webBuilder.build()
                .get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .toEntity(Void.class)
                .flatMap(response -> {
                    // Handle the response asynchronously
                    if (response == null || response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                        log.info("Failed to authorize");

                        return onError(exchange, HttpStatus.UNAUTHORIZED);
                    }

                    log.info("Authorized");
                    return chain.filter(exchange);
                });
    }

    @Override
    public ShortcutType shortcutType() {
        return GatewayFilter.super.shortcutType();
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return GatewayFilter.super.shortcutFieldOrder();
    }

    @Override
    public String shortcutFieldPrefix() {
        return GatewayFilter.super.shortcutFieldPrefix();
    }

    private HttpEntity<?> createRequestEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        return new HttpEntity<>(headers);
    }


    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}

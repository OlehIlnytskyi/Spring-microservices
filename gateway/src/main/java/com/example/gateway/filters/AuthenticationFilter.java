package com.example.gateway.filters;

import com.example.gateway.service.discovery.ServiceNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if (isAuthMissing(request)) {
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        }

        final String token = getAuthHeader(request);
        final String url = "http://" + ServiceNames.SECURITY_SERVICE + "/api/security/checkToken" ;

        webClient
                .get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve();

        return chain.filter(exchange);
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

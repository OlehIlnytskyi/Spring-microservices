package com.example.orders.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OrdersConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


//    Circuit Breaker
//
//    @Bean
//    public TimeLimiterConfig timeLimiterConfig() {
//        return TimeLimiterConfig.custom()
//                .timeoutDuration(Duration.ofSeconds(4))
//                .build();
//    }
//
//    @Bean
//    public CircuitBreakerConfig circuitBreakerConfig() {
//        return CircuitBreakerConfig.custom()
//                .failureRateThreshold(50)
//                .waitDurationInOpenState(Duration.ofMillis(1000))
//                .slidingWindowSize(2)
//                .build();
//    }
//
//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(
//            TimeLimiterConfig timeLimiterConfig,
//            CircuitBreakerConfig circuitBreakerConfig) {
//
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .timeLimiterConfig(timeLimiterConfig)
//                .circuitBreakerConfig(circuitBreakerConfig)
//                .build());
//    }
}

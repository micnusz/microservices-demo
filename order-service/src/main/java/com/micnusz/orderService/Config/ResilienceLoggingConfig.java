package com.micnusz.orderService.Config;


import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryRegistry;

@Configuration
public class ResilienceLoggingConfig {

    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;

    public ResilienceLoggingConfig(CircuitBreakerRegistry circuitBreakerRegistry,
                                   RetryRegistry retryRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.retryRegistry = retryRegistry;
    }

    @PostConstruct
    public void registerListeners() {

        var cb = circuitBreakerRegistry.circuitBreaker("userService");

        cb.getEventPublisher()
                .onSuccess(event -> log("CB SUCCESS", event))
                .onError(event -> log("CB ERROR", event))
                .onStateTransition(event -> log("CB STATE", event));

        var retry = retryRegistry.retry("userService");

        retry.getEventPublisher()
                .onRetry(event -> log("RETRY", event));
    }

    private void log(String type, Object event) {
        System.out.println(type + " -> " + event);
    }
}
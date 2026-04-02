package com.micnusz.sbag.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

@RestController
public class FallbackController {

    private static final Logger log = LoggerFactory.getLogger(FallbackController.class);

    @GetMapping("/fallback/user")
    public Mono<Map<String, Object>> userFallback(ServerWebExchange exchange) {
        log.warn("User service fallback triggered for path: {}", exchange.getRequest().getPath());

        return Mono.just(Map.of(
                "timestamp", Instant.now().toString(),
                "status", 503,
                "error", "Service Unavailable",
                "message", "User service is temporarily unavailable. Please try again later.",
                "path", exchange.getRequest().getPath().toString()
        ));
    }

    @GetMapping("/fallback/order")
    public Mono<Map<String, Object>> orderFallback(ServerWebExchange exchange) {
        log.warn("Order service fallback triggered for path: {}", exchange.getRequest().getPath());

        return Mono.just(Map.of(
                "timestamp", Instant.now().toString(),
                "status", 503,
                "error", "Service Unavailable",
                "message", "Order service is temporarily unavailable. Please try again later.",
                "path", exchange.getRequest().getPath().toString()
        ));
    }
}
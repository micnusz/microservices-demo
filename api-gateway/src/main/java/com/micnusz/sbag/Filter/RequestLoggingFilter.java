package com.micnusz.sbag.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long start = System.currentTimeMillis();

        String correlationId = exchange.getRequest().getHeaders()
                .getFirst("X-Correlation-Id");

        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        exchange.getResponse().getHeaders()
                .add("X-Correlation-Id", correlationId);

        String finalCorrelationId = correlationId;

        return chain.filter(exchange)
                .doOnError(ex -> log.error("[{}] ERROR: {}", finalCorrelationId, ex.getMessage()))
                .then(Mono.fromRunnable(() -> {

                    long time = System.currentTimeMillis() - start;

                    int status = exchange.getResponse().getStatusCode() != null
                            ? exchange.getResponse().getStatusCode().value()
                            : -1;

                    log.info("[{}] {} {} : {} ({} ms)",
                            finalCorrelationId,
                            exchange.getRequest().getMethod(),
                            exchange.getRequest().getURI(),
                            status,
                            time
                    );
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
package com.micnusz.orderService.Service;

import com.micnusz.orderService.Client.UserClient;
import com.micnusz.orderService.Exception.UserNotFoundException;
import com.micnusz.orderService.Model.UserRef;
import com.micnusz.orderService.Model.UserResponse;
import feign.RetryableException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.ConnectException;

@Service
public class UserIntegrationService {

    private static final Logger log = LoggerFactory.getLogger(UserIntegrationService.class);
    private final UserClient userClient;

    public UserIntegrationService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Bulkhead(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallback")
    @Retry(name = "userService")
    public UserRef getUser(Long id) {

        UserResponse response = userClient.getUser(id);

        return new UserRef(response.id(), response.name());
    }

    private UserRef fallback(Long id, Throwable ex) {
        if (ex instanceof CallNotPermittedException) {
            log.warn("Circuit breaker OPEN - fast failing for user: {}", id);
            return new UserRef(id, "service-unavailable");
        }
        if (ex instanceof RetryableException || ex instanceof ConnectException) {
            log.warn("Connection error after retries for user: {}", id);
            return new UserRef(id, "connection-failed");
        }
        if (ex instanceof UserNotFoundException) {
            throw (UserNotFoundException) ex;
        }

        log.error("Unexpected error for user: {}", id, ex);
        return new UserRef(id, "fallback-user");
    }
}

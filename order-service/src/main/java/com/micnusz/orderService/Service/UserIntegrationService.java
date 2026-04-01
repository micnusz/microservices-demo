package com.micnusz.orderService.Service;

import com.micnusz.orderService.Client.UserClient;
import com.micnusz.orderService.Model.UserRef;
import com.micnusz.orderService.Model.UserResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class UserIntegrationService {

    private final UserClient userClient;

    public UserIntegrationService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Bulkhead(name = "userService")
    @Retry(name = "userSErvice")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallback")
    public UserRef getUser(Long id) {

        UserResponse response = userClient.getUser(id);

        return new UserRef(response.id(), response.name());
    }

    public UserRef fallback(Long id, Throwable ex) {
        return new UserRef(id, "fallback-user");
    }
}

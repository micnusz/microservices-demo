package com.micnusz.orderService.Service;

import com.micnusz.orderService.Model.*;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final UserIntegrationService userIntegrationService;

    public OrderService(UserIntegrationService userIntegrationService) {
        this.userIntegrationService = userIntegrationService;
    }

    public OrderResponse getOrder(Long orderId, Long userId) {

        UserRef user = userIntegrationService.getUser(userId);

        return new OrderResponse(
                orderId,
                user
        );
    }
}
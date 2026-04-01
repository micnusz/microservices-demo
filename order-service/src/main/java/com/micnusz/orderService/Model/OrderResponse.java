package com.micnusz.orderService.Model;

public record OrderResponse(
        Long orderId,
        UserRef user
) {}
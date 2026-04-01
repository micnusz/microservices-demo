package com.micnusz.orderService.Model;

public record Order(
        Long id,
        Long userId,
        String product
) {}

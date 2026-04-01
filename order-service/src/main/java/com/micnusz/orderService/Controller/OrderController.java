package com.micnusz.orderService.Controller;

import com.micnusz.orderService.Model.OrderResponse;
import com.micnusz.orderService.Service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}/user/{userId}")
    public OrderResponse getOrder(@PathVariable Long orderId,
                                  @PathVariable Long userId) {
        return orderService.getOrder(orderId, userId);
    }
}

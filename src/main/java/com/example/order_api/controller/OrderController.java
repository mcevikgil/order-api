package com.example.order_api.controller;

import com.example.order_api.dto.request.OrderRequest;
import com.example.order_api.dto.response.OrderResponse;
import com.example.order_api.dto.response.OrderResponseWithUser;
import com.example.order_api.dto.response.OrderSummaryResponse;
import com.example.order_api.repository.OrderRepository;
import com.example.order_api.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderSummaryResponse>> getAllOrders() {
        List<OrderSummaryResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderResponseWithUser> getOrder(@PathVariable("uuid") UUID uuid) {
        OrderResponseWithUser order = orderService.getOrderByUuid(uuid);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable("userId") Long userId) {
        List<OrderResponse> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable("uuid") UUID uuid, @Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.updateOrder(uuid, request);
        return ResponseEntity.ok(response);
    }
}

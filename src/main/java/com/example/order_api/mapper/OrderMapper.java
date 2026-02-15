package com.example.order_api.mapper;

import com.example.order_api.dto.response.OrderResponse;
import com.example.order_api.dto.response.OrderResponseWithUser;
import com.example.order_api.dto.response.OrderSummaryResponse;
import com.example.order_api.entity.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class OrderMapper {

    private final UserMapper userMapper;

    public OrderMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setUuid(order.getUuid());
        response.setOrderNumber(order.getOrderNumber());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setShippingAddress(order.getShippingAddress());
        response.setOrderDate(order.getOrderDate());
        return response;
    }

    public OrderResponseWithUser toResponseWithUser(Order order) {
        OrderResponseWithUser responseWithUser = new OrderResponseWithUser();
        responseWithUser.setUuid(order.getUuid());
        responseWithUser.setOrderNumber(order.getOrderNumber());
        responseWithUser.setTotalAmount(order.getTotalAmount());
        responseWithUser.setStatus(order.getStatus());
        responseWithUser.setShippingAddress(order.getShippingAddress());
        responseWithUser.setOrderDate(order.getOrderDate());
        return responseWithUser;
    }

    public OrderSummaryResponse toSummaryResponse(Order order) {
        OrderSummaryResponse response = new OrderSummaryResponse();
        response.setUuid(order.getUuid());
        response.setOrderNumber(order.getOrderNumber());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setOrderDate(order.getOrderDate());
        return response;
    }

}

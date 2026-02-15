package com.example.order_api.service;

import com.example.order_api.dto.request.OrderRequest;
import com.example.order_api.dto.response.OrderResponse;
import com.example.order_api.dto.response.OrderResponseWithUser;
import com.example.order_api.dto.response.OrderSummaryResponse;
import com.example.order_api.entity.Order;
import com.example.order_api.entity.OrderStatus;
import com.example.order_api.entity.User;
import com.example.order_api.exception.ResourceNotFoundException;
import com.example.order_api.mapper.OrderMapper;
import com.example.order_api.repository.OrderRepository;
import com.example.order_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(request.getTotalAmount());
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setNotes(request.getNotes());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    public List<OrderSummaryResponse> getAllOrders(){
        return orderRepository.findAll().stream().map(orderMapper::toSummaryResponse).collect(Collectors.toList());
    }

    public OrderResponseWithUser getOrderByUuid(UUID uuid){
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "uuid", uuid));
        return orderMapper.toResponseWithUser(order);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrder(UUID uuid, OrderRequest request) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "uuid", uuid));

        if (request.getStatus() != null) {
            order.setStatus(OrderStatus.valueOf(request.getStatus()));
        }

        if (request.getShippingAddress() != null) {
            order.setShippingAddress(request.getShippingAddress());
        }

        if (request.getNotes() != null) {
            order.setNotes(request.getNotes());
        }

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    public void cancelOrder(UUID uuid) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "uuid", uuid));
        order.setStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }
}

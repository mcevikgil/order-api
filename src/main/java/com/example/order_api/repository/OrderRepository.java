package com.example.order_api.repository;

import com.example.order_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUuid(UUID uuid);

    Optional<Order> findByOrderNumber(String orderNumber);

    Optional<Order> findByUserId(Long userId);

}

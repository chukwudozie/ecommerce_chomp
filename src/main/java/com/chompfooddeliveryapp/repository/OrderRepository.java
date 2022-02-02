package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderById(Long id);
    Optional<Order>findOrderByIdAndAndUserId(Long orderId, Long userId);
    List<Order> findAllByUserId(Long userId);
}

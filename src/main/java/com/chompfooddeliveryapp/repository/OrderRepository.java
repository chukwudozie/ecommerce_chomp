package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderById(Long id);
}

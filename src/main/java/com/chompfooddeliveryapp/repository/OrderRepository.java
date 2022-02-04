package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderById(Long id);
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
    Optional<Order> findOrderByIdEqualsAndUserIdEquals(Long orderId, Long userId);
    List<Order> findByUserId(Long userId);
    Optional<Order> getOrderByIdIsAndUserIdIs(Long orderId, Long userId);

}

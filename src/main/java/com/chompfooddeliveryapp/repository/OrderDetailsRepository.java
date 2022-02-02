package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.orders.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {

    Optional<OrderDetail> findOrderDetailByOrderIdAndAndMenuId(Long orderId, Long menuId);
    //findOrderDetailByOrderIdAndMenuId(Long orderId, Long menuId);

    Optional<OrderDetail> findOrderDetailById(Long orderId);

    List<OrderDetail> findAllByOrderId(Long orderId);
}


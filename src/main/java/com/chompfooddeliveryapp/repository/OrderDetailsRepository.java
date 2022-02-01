package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.orders.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {

    Optional<OrderDetail> findOrderDetailByOrderIdAndAndMenuId(Long orderId, Long menuId);
    //findOrderDetailByOrderIdAndMenuId(Long orderId, Long menuId);

    Optional<OrderDetail> findOrderDetailById(Long orderId);

    List<OrderDetail> findAllByOrderId(Long orderId);
    Optional<List<OrderDetail>> findAllByOrderIdEquals(Long orderId);
}


package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.orders.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
}

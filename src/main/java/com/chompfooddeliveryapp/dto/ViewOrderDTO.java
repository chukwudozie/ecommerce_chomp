package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.enums.OrderStatus;
import com.chompfooddeliveryapp.model.orders.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderDTO {
    private Long orderId;
    private Long quantity;
    private OrderStatus status;
    private String name;
    private String image;
    private Long price;
    private Timestamp orderDate;
    private Timestamp deliveredDate;
    private String description;
}
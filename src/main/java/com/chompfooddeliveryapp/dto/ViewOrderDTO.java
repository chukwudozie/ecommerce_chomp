package com.chompfooddeliveryapp.dto;

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
    private String status;
    private String name;
    private String image;
    private Long price;
    private Timestamp dateCreated;
    private Timestamp order_date;
    private Timestamp delivered_date;
    private String transaction;
}
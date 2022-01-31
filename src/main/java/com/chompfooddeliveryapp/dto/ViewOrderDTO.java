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



//    dateOrdered
// endpoint to view details of a particular order
//My Orders: As a registered user, I want to see all my orders so that I can manage my order
// history and keep track of new orders.
//
//Acceptance Criteria:
//should be able to see details of only orders the user has made.
//Should be able to see the following for each order:
//Image, quantity, date ordered, date delivered, status
//Status should be either- Delivered, pending or canceled
// payment details per order
//Item’s total
//Delivery fee
//Total
//Delivery method
//Shipping address
//Status of each order should be automatically updated.
//Should be able to directly order any delivered items again
//Should be able to return a delivered item
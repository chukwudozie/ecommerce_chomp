package com.chompfooddeliveryapp.dto;


import com.chompfooddeliveryapp.model.enums.OrderStatus;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminViewOrderDTO {
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private Long customerOrderQuantity;
    private Double amount;
    private PaymentMethod paymentType;
    private OrderStatus status;
    private LocalDateTime dateOrdered;
}

package com.chompfooddeliveryapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ViewUserOrdersDTO {
    private String image;
    private Long quantity;
    private LocalDateTime order_date;
    private LocalDateTime delivered_date;
    private String status;
    private String deliveryMethod;

    @Override
    public String toString() {
        return "ViewUserOrdersDTO {" +
                "image: " + image +
                ", quantity: " + quantity +
                ", order_date: " + order_date +
                ", delivered_date: " + delivered_date +
                ", status: " + status +
                ", deliveryMethod: " + deliveryMethod +
                "}";
    }
}

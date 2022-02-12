package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class ViewUserOrdersDTO {
    private String image;
    private Long quantity;
    private Timestamp order_date;
    private Timestamp delivered_date;
    private String status;
    private String deliveryMethod;
    private ShippingAddressDTO shippingAddress;

    @Override
    public String toString() {
        return "ViewUserOrdersDTO {" +
                "image: " + image +
                ", quantity: " + quantity +
                ", order_date: " + order_date +
                ", delivered_date: " + delivered_date +
                ", status: " + status +
                ", deliveryMethod: " + deliveryMethod +
                ", shippingAddress: " + shippingAddress +
                "}";
    }
}

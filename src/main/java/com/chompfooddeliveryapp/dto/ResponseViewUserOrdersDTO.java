package com.chompfooddeliveryapp.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseViewUserOrdersDTO {
    List<ViewUserOrdersDTO> allOrders;
    ShippingAddressDTO shippingAddress;
    PaymentDetailsDTO paymentDetails;


    @Override
    public String toString() {
        return "ResponseViewUserOrdersDTO {" +
                "allOrders: " + allOrders +
                ", shippingAddress: " + shippingAddress +
                ", paymentDetails: " + paymentDetails +
                "}";
    }
}

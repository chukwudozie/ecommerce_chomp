package com.chompfooddeliveryapp.dto;

import java.util.List;

public class ResponseViewUserOrdersDTO {
    List<ViewUserOrdersDTO> allOrders;
    ShippingAddressDTO shippingAddress;
    PaymentDetailsDTO paymentDetails;
}

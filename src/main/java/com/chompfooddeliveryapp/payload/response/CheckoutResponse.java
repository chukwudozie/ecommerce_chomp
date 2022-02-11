package com.chompfooddeliveryapp.payload.response;

import com.chompfooddeliveryapp.dto.PaymentDetailsDTO;
import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.payload.ViewCartResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {
    private List<ProductSummary> usersCartItems;
    private ShippingAddressDTO shippingAddressDTO;
}



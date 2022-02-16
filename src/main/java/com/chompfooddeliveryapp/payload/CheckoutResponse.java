package com.chompfooddeliveryapp.payload;

import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
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



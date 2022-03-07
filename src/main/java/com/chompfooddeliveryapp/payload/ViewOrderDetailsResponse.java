package com.chompfooddeliveryapp.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderDetailsResponse {
    private List<?> viewOrderDtoList;
    private Optional<?> shippingAddress;
}

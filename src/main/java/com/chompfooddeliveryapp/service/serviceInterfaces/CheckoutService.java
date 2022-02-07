package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ResponseViewUserOrdersDTO;
import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.payload.AllCartItems;

import java.util.List;

public interface CheckoutService {
    List<MenuItem> getOrderSummary(Long userId);
    ResponseViewUserOrdersDTO getAllOrdersByUserId(Long userId);
    String saveShippingAddress(long userId, ShippingAddressDTO shippingAddressDTO);
    AllCartItems checkoutCartItems(Long userId);
}

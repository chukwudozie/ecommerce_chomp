package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.users.ShippingAddress;

public interface CheckoutService {
    ShippingAddress saveShippingAddress(long userId, ShippingAddressDTO shippingAddressDTO);
    Order createOrderFromCartItem(long userId, long cartId);
}

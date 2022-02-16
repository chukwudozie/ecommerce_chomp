package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.CheckoutResponse;
import com.chompfooddeliveryapp.payload.ProductSummary;

import java.util.List;

public interface CheckoutService {
    List<String> saveShippingAddress(long userId, ShippingAddressDTO shippingAddressDTO);
    CheckoutResponse createOrderFromCartItem(long userId, long cartId);
    List<ProductSummary> getAllCartItems(long userId, long cartId);
    ShippingAddressDTO getDefaultShippingAddress(User user);
    List<ShippingAddressDTO> getAllAddress(long userId);
}

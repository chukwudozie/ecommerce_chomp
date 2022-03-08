package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ResponseViewUserOrdersDTO;
import com.chompfooddeliveryapp.dto.ViewOrderDTO;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.payload.UpdatePayLoad;
import com.chompfooddeliveryapp.payload.ViewOrderDetailsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

   ViewOrderDetailsResponse getOrderDetails(Long userId, Long orderId);
    List<MenuItem> getOrderSummary(Long userId);
    ResponseViewUserOrdersDTO getAllOrdersByUserId(Long userId);
    AllCartItems checkoutCartItems(Long userId);
    UpdatePayLoad updateOrderStatus(Long orderId);
}

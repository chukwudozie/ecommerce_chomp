package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ResponseViewUserOrdersDTO;
import com.chompfooddeliveryapp.dto.ViewOrderDTO;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.payload.AllCartItems;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    List<ViewOrderDTO> getOrderDetails(Long userId, Long orderId);
    List<MenuItem> getOrderSummary(Long userId);
    ResponseViewUserOrdersDTO getAllOrdersByUserId(Long userId);
    AllCartItems checkoutCartItems(Long userId);
}

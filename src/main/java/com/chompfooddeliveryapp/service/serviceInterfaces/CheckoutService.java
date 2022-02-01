package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.model.meals.MenuItem;

import java.util.List;

public interface CheckoutService {
    List<MenuItem> getOrderSummary(Long userId);
}

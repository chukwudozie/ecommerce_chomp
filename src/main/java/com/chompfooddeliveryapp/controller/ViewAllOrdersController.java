package com.chompfooddeliveryapp.controller;


import com.chompfooddeliveryapp.dto.ResponseViewUserOrdersDTO;
import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class ViewAllOrdersController {

    private final CheckoutService checkoutService;

    public ViewAllOrdersController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }




    @PostMapping("/{userId}/shipping-address")
    public ResponseEntity<String> saveShippingAddress(@PathVariable("userId") Long userId,
                                                  @RequestBody ShippingAddressDTO shippingAddress) {
        var responseText = checkoutService.saveShippingAddress(userId, shippingAddress);
        return new ResponseEntity<>(responseText, HttpStatus.OK);
    }

    @GetMapping("/{userId}/view-all-orders")
    public ResponseEntity<ResponseViewUserOrdersDTO> viewAllOrders(@PathVariable("userId") long userId) {
        var allUserOrders = checkoutService.getAllOrdersByUserId(userId);
        return new ResponseEntity<>(allUserOrders, HttpStatus.OK);
    }
}

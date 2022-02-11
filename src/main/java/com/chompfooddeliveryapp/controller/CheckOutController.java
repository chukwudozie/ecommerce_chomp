package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.service.serviceInterfaces.ViewOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckOutController {

    private final ViewOrderService viewOrderService;

    @Autowired
    public CheckOutController(ViewOrderService viewOrderService) {
        this.viewOrderService = viewOrderService;
    }

    @GetMapping("/{userId}/checkout")
    public ResponseEntity<AllCartItems> checkoutOrders(@PathVariable("userId") long userId) {
        var checkoutItems = viewOrderService.checkoutCartItems(userId);
        return ResponseEntity.ok().body(checkoutItems);
    }

    public ResponseEntity<List<ShippingAddressDTO>> getUserAddress(@PathVariable long userId) {
        return ResponseEntity.ok().build();
    }
}

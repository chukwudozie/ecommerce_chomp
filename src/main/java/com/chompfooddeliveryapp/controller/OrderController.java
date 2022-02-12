package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ResponseViewUserOrdersDTO;
import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.model.users.ShippingAddress;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class OrderController {

    private final OrderService orderService;
    private final CheckoutService checkoutService;
    @Autowired
    public OrderController(OrderService orderService, CheckoutService checkoutService) {
        this.orderService = orderService;
        this.checkoutService = checkoutService;
    }


    @GetMapping("/viewOrderDetails/{userId}/{orderId}")
    public ResponseEntity<?> viewOrderDetails(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(orderService.getOrderDetails(userId, orderId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/shipping-address")
    public ResponseEntity<List<String>> saveShippingAddress(@PathVariable("userId") Long userId,
                                                            @RequestBody ShippingAddressDTO shippingAddress) {
        var responseText = checkoutService.saveShippingAddress(userId, shippingAddress);
        return new ResponseEntity<>(responseText, HttpStatus.OK);
    }

    @GetMapping("/{userId}/view-all-orders")
    public ResponseEntity<ResponseViewUserOrdersDTO> viewAllOrders(@PathVariable("userId") long userId) {
        var allUserOrders = orderService.getAllOrdersByUserId(userId);
        return new ResponseEntity<>(allUserOrders, HttpStatus.OK);
    }

}

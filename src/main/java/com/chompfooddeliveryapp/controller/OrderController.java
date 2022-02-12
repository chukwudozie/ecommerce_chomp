package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.EditUserDetailsDto;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import com.chompfooddeliveryapp.service.serviceInterfaces.UserServiceInterface;
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
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/viewOrderDetails/{userId}/{orderId}")
    public ResponseEntity<?> viewOrderDetails(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(orderService.getOrderDetails(userId, orderId), HttpStatus.OK);
    }

}

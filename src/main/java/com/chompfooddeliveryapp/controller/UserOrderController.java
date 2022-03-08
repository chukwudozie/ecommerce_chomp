package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ResponseViewUserOrdersDTO;
import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.security.service.UserDetailsImpl;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import com.chompfooddeliveryapp.service.serviceInterfaces.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserOrderController {

    private final OrderService orderService;
    private final CheckoutService checkoutService;
    private final UserServiceInterface userService;
    @Autowired
    public UserOrderController(OrderService orderService,
                               CheckoutService checkoutService,
                               UserServiceInterface userService) {
        this.orderService = orderService;
        this.checkoutService = checkoutService;
        this.userService = userService;

    }


    @GetMapping("/view_order_details/{orderId}")
    public ResponseEntity<?> viewOrderDetails( @PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(orderService.getOrderDetails(userService.getUserIDFromSecurityContext(), orderId), HttpStatus.OK);
    }

    @PostMapping("/shipping-address")
    public ResponseEntity<List<String>> saveShippingAddress(
                                                            @RequestBody ShippingAddressDTO shippingAddress) {
        var responseText = checkoutService.saveShippingAddress(userService.getUserIDFromSecurityContext(), shippingAddress);
        return new ResponseEntity<>(responseText, HttpStatus.OK);
    }

    @GetMapping("/view-all-orders")
    public ResponseEntity<ResponseViewUserOrdersDTO> viewAllOrders( ) {
        var allUserOrders = orderService.getAllOrdersByUserId(userService.getUserIDFromSecurityContext());
        return new ResponseEntity<>(allUserOrders, HttpStatus.OK);
    }

}

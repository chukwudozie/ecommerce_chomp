package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ProcessPaymentRequest;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.chompfooddeliveryapp.service.serviceImpl.UserServiceImpl;
import com.chompfooddeliveryapp.service.serviceInterfaces.PaymentService;
import com.chompfooddeliveryapp.service.serviceInterfaces.UserServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class ProcessPaymentController {

    private final PaymentService paymentservice;
    private final UserServiceInterface userService;

@Autowired
    public ProcessPaymentController(PaymentService paymentservice, UserServiceInterface userService) {
        this.paymentservice = paymentservice;

    this.userService = userService;
}

    @PostMapping("/process/{orderId}")
    public ResponseEntity<?>processPayment(@RequestBody ProcessPaymentRequest processPaymentRequest,
            @PathVariable Long orderId){

    Object output = paymentservice.processPayment(processPaymentRequest,userService.getUserIDFromSecurityContext(), orderId);
    return new ResponseEntity<>(output, HttpStatus.ACCEPTED);
    //todo: Makera's Call back function from PayStack will redirect to the verifyPayStackPayment endpoint
    }

    @PostMapping("/verifyPayment/{orderId}")
    public ResponseEntity<?> verifyPayStackPayment(@RequestBody VerifyTransactionDto transactionDto,
              @PathVariable Long orderId)
            throws JsonProcessingException {
    return ResponseEntity.ok(paymentservice.verifyPayStackPayment(transactionDto,userService.getUserIDFromSecurityContext(),orderId));


    }
}

package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ProcessPaymentRequest;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.chompfooddeliveryapp.service.serviceInterfaces.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class ProcessPaymentController {

    private final PaymentService paymentservice;


@Autowired
    public ProcessPaymentController(PaymentService paymentservice) {
        this.paymentservice = paymentservice;

}

    @PostMapping("/process/{userId}/{orderId}")
    public ResponseEntity<?>processPayment(@RequestBody ProcessPaymentRequest processPaymentRequest,
             @PathVariable Long userId, @PathVariable Long orderId){

    Object output = paymentservice.processPayment(processPaymentRequest,userId, orderId);
    return new ResponseEntity<>(output, HttpStatus.ACCEPTED);
    //todo: Makera's Call back function from PayStack will redirect to the verifyPayStackPayment endpoint
    }

    @PostMapping("/verifyPayment/{userId}/{orderId}")
    public ResponseEntity<?> verifyPayStackPayment(@RequestBody VerifyTransactionDto transactionDto,
               @PathVariable Long userId, @PathVariable Long orderId)
            throws JsonProcessingException {
    return ResponseEntity.ok(paymentservice.verifyPayStackPayment(transactionDto,userId,orderId));


    }
}

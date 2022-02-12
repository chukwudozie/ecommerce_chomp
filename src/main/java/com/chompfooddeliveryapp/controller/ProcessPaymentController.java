package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.PayStackResponseDto;
import com.chompfooddeliveryapp.dto.ProcessPaymentRequest;
import com.chompfooddeliveryapp.dto.VerificationResponse;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.enums.TransactionStatus;
import com.chompfooddeliveryapp.model.wallets.Transaction;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.service.serviceImpl.OrderServiceImplementation;
import com.chompfooddeliveryapp.service.serviceImpl.PaystackServiceImpl;
import com.chompfooddeliveryapp.service.serviceInterfaces.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class ProcessPaymentController {

    private final PaymentService paymentservice;
    private final PaystackServiceImpl paystackService;
    private final OrderServiceImplementation orderService;
    private final TransactionRepository transactionRepository;

@Autowired
    public ProcessPaymentController(PaymentService paymentservice, PaystackServiceImpl paystackService,
                                    OrderServiceImplementation orderService, TransactionRepository transactionRepository) {
        this.paymentservice = paymentservice;
        this.paystackService = paystackService;
    this.orderService = orderService;
    this.transactionRepository = transactionRepository;
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
         //todo: change the state of the transaction from ur service to successful (done)
       //todo: change the status of the user's order from ur service to confirmed(not done)

    }
}

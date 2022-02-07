package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.PayStackResponseDto;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.chompfooddeliveryapp.dto.PayStackRequestDto;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.WalletPayload;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceImpl.PaystackServiceImpl;
import com.chompfooddeliveryapp.service.serviceImpl.TransactionServiceImpl;
import com.chompfooddeliveryapp.service.serviceImpl.WalletServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static com.chompfooddeliveryapp.model.enums.PaymentMethod.*;
import static com.chompfooddeliveryapp.model.enums.TransactionType.*;

@RestController
@RequestMapping("/user")
//@RequiredArgsConstructor
public class WalletController {


    private final String secret_key = "sk_test_2d5c43445c023f91a8f13334df77580390a395c9";

    private final WebClient.Builder webClient;
    private final WalletServiceImpl walletService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final TransactionServiceImpl transactionService;
    private final PaystackServiceImpl paystackService;

    public WalletController(WebClient.Builder webClient, WalletServiceImpl walletService,
                            TransactionRepository transactionRepository, UserRepository userRepository,
                            ObjectMapper objectMapper, TransactionServiceImpl transactionService, PaystackServiceImpl paystackService) {
        this.webClient = webClient;
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.transactionService = transactionService;
        this.paystackService = paystackService;
    }


    @PostMapping("/fundwallet/{userId}")
    public Object initializeWalletTransaction(@RequestBody PayStackRequestDto payStackRequestDto,
                                              @PathVariable long userId){

        String transactionReference = transactionService.getTransactionRefence(userId,
                CREDIT, PAYSTACK);
        return paystackService.initializePaystackTransaction(payStackRequestDto, userId, CREDIT);

    }

    @PostMapping("/verifytransaction")
    public ResponseEntity<?> PayStackDto(@RequestBody VerifyTransactionDto verifyTransactionDto) throws IOException {

        PayStackResponseDto responseDto = paystackService.verifyPaystackTransaction(verifyTransactionDto);

       WalletPayload walletPayload =  walletService.fundUsersWallet(verifyTransactionDto.getTransactionReference(),
               responseDto.getStatus(), responseDto.getData().get("status").toString(),
               responseDto.getData().get("amount").toString());
       walletPayload.setGatewayResponse(responseDto.getData().get("gateway_response").toString());

       return new ResponseEntity<>(walletPayload, HttpStatus.OK);

    }

    @GetMapping("/walletBalance/{userId}")
    public String checkWalletBalance(@PathVariable Long userId) {
        return walletService.getWalletBalance(userId);
    }




}

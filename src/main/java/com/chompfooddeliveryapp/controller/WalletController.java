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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static com.chompfooddeliveryapp.model.enums.PaymentMethod.*;
import static com.chompfooddeliveryapp.model.enums.TransactionType.*;

@RestController
@RequestMapping("/user/wallet")
//@RequiredArgsConstructor
public class WalletController {


    private final WalletServiceImpl walletService;
    private final PaystackServiceImpl paystackService;

    @Autowired
    public WalletController( WalletServiceImpl walletService, PaystackServiceImpl paystackService) {

        this.walletService = walletService;
        this.paystackService = paystackService;
    }


    @PostMapping("/fundwallet/{userId}")
    public Object initializeWalletTransaction(@RequestBody PayStackRequestDto payStackRequestDto,
                                              @PathVariable long userId){

        return paystackService.initializePaystackTransaction(payStackRequestDto, userId, CREDIT);

    }

    @GetMapping ("/verifytransaction")
    public ResponseEntity<?> verifyWalletTransaction(@RequestBody VerifyTransactionDto verifyTransactionDto) throws Exception {

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

package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.WalletDto;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionStatus;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.wallets.Transaction;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.service.serviceImpl.WalletServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/wallet")
public class WalletController {


    private final WebClient.Builder webClient;
    private final WalletServiceImpl walletService;
    private final TransactionRepository transactionRepository;

    public WalletController(WebClient.Builder webClient, WalletServiceImpl walletService, TransactionRepository transactionRepository) {
        this.webClient = webClient;
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
    }


    @PostMapping("/fundwallet/{userId}/{walletId}")
    public Object initializeWalletTransaction(@RequestBody WalletDto walletDto, @PathVariable long userId, @PathVariable long walletId){

//        if (walletService.initializeFundWallet())

//        walletDto.setReference(transaction.getId());

        return new ResponseEntity<>("The User or the wallet cannot be null", HttpStatus.BAD_REQUEST );

        return webClient.build().post().uri("https://api.paystack.co/transaction/initialize").header("Authorization", "Bearer " + secret_key ).bodyValue(walletDto)
                .retrieve().bodyToMono(Object.class).block();

    }




}

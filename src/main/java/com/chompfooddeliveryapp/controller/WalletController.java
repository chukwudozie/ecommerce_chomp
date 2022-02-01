package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.WalletDto;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceImpl.WalletServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@RestController
@RequestMapping("/wallet")
public class WalletController {


    private final String secret_key = "sk_test_2d5c43445c023f91a8f13334df77580390a395c9";

    private final WebClient.Builder webClient;
    private final WalletServiceImpl walletService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public WalletController(WebClient.Builder webClient, WalletServiceImpl walletService, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.webClient = webClient;
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }


    @PostMapping("/fundwallet/{userId}/")
    public Object initializeWalletTransaction(@RequestBody WalletDto walletDto, @PathVariable long userId){
        String transactionReference = walletService.setFundWalletTransactionReference(userId);
        if (!transactionReference.contains("chompT")){
            return new ResponseEntity<>(transactionReference, HttpStatus.BAD_REQUEST );
        }
        User user = userRepository.findById(userId).get();
        String[] channels = {"card", "bank"};
        walletDto.setReference(transactionReference);
        walletDto.setEmail(user.getEmail());
        walletDto.setChannels(channels);

        return webClient.build().post().uri("https://api.paystack.co/transaction/initialize").header("Authorization", "Bearer " + secret_key ).bodyValue(walletDto)
                .retrieve().bodyToMono(Object.class).block();

    }




}

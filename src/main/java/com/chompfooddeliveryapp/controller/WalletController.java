package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.PayStackDto;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.chompfooddeliveryapp.dto.WalletDto;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.WalletPayload;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceImpl.WalletServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class WalletController {


    private final String secret_key = "sk_test_2d5c43445c023f91a8f13334df77580390a395c9";

    private final WebClient.Builder webClient;
    private final WalletServiceImpl walletService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public WalletController(WebClient.Builder webClient, WalletServiceImpl walletService,
                            TransactionRepository transactionRepository, UserRepository userRepository,
                            ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }


    @PostMapping("/fundwallet/{userId}")
    public Object initializeWalletTransaction(@RequestBody WalletDto walletDto, @PathVariable long userId){
        String transactionReference = walletService.setFundWalletTransactionReference(userId);

        if (!transactionReference.contains("chompT")){
            return new ResponseEntity<>(transactionReference, HttpStatus.BAD_REQUEST );
        }
        User user = userRepository.findById(userId).get();
        String[] channels = {"card", "bank"};
        walletDto.setAmount(walletDto.getAmount()*100);
        walletDto.setReference(transactionReference);
        walletDto.setEmail(user.getEmail());
        walletDto.setChannels(channels);

        return webClient.build().post().uri("https://api.paystack.co/transaction/initialize").
                header("Authorization", "Bearer " + secret_key ).bodyValue(walletDto)
                .retrieve().bodyToMono(Object.class).block();

    }

    @GetMapping("/verifytransaction")
    public ResponseEntity<?> PayStackDto(@RequestBody VerifyTransactionDto verifyTransactionDto) throws IOException {

        //verifying the transaction with the transaction ID from paystack's API
        String paystackObject =  webClient.build().get().
                uri("https://api.paystack.co/transaction/verify/" + verifyTransactionDto.getTransactionReference()).
                header("Authorization", "Bearer " + secret_key)
                .retrieve().bodyToMono(String.class).block();

        //mapping the response from paystack's API to a DTO object
        PayStackDto payStackDto = objectMapper.readValue( paystackObject, PayStackDto.class);
        System.out.println(payStackDto.getData().get("amount"));

       return walletService.fundUsersWallet(verifyTransactionDto.getTransactionReference(),
               payStackDto.getStatus(), payStackDto.getData().get("status").toString(),
               payStackDto.getData().get("amount").toString());



    }




}

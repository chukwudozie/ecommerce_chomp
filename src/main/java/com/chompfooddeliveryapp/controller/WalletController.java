package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.payload.PayStackResponse;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.chompfooddeliveryapp.dto.PayStackRequest;
import com.chompfooddeliveryapp.payload.WalletPayload;
import com.chompfooddeliveryapp.service.serviceImpl.PaystackServiceImpl;
import com.chompfooddeliveryapp.service.serviceImpl.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.chompfooddeliveryapp.model.enums.TransactionType.*;

@RestController
@RequestMapping("/user/wallet")
public class WalletController {


    private final WalletServiceImpl walletService;
    private final PaystackServiceImpl paystackService;

    @Autowired
    public WalletController( WalletServiceImpl walletService, PaystackServiceImpl paystackService) {

        this.walletService = walletService;
        this.paystackService = paystackService;
    }


    @PostMapping("/fundwallet/{userId}")
    public Object initializeWalletTransaction(@RequestBody PayStackRequest payStackRequestDto,
                                              @PathVariable long userId){

        return paystackService.initializePaystackTransaction(payStackRequestDto, userId, CREDIT);

    }

    @GetMapping ("/verifytransaction")
    public ResponseEntity<?> verifyWalletTransaction(@RequestBody VerifyTransactionDto verifyTransactionDto) throws Exception {

        PayStackResponse responseDto = paystackService.verifyPaystackTransaction(verifyTransactionDto);

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

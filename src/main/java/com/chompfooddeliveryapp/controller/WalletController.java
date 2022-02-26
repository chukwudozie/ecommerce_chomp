package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.payload.PayStackResponse;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.chompfooddeliveryapp.dto.PayStackRequest;
import com.chompfooddeliveryapp.payload.WalletPayload;
import com.chompfooddeliveryapp.service.serviceImpl.PaystackServiceImpl;
import com.chompfooddeliveryapp.service.serviceImpl.UserServiceImpl;
import com.chompfooddeliveryapp.service.serviceImpl.WalletServiceImpl;
import com.chompfooddeliveryapp.service.serviceInterfaces.UserServiceInterface;
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
    private final UserServiceInterface userService;

    @Autowired
    public WalletController(WalletServiceImpl walletService, PaystackServiceImpl paystackService, UserServiceInterface userService) {

        this.walletService = walletService;
        this.paystackService = paystackService;
        this.userService = userService;
    }


    @PostMapping("/fundwallet")
    public Object initializeWalletTransaction(@RequestBody PayStackRequest payStackRequestDto)
                                              {

        return paystackService.initializePaystackTransaction(payStackRequestDto, userService.getUserIDFromSecurityContext(), CREDIT);

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

    @GetMapping("/walletBalance")
    public String checkWalletBalance() {

        return walletService.getWalletBalance(userService.getUserIDFromSecurityContext());
    }




}

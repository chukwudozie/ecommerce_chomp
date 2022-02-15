package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ProcessPaymentRequest;
import com.chompfooddeliveryapp.payload.VerificationResponse;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PaymentService {

    Object processPayment(ProcessPaymentRequest request, Long userId, Long orderId);

    VerificationResponse verifyPayStackPayment(VerifyTransactionDto transactionDto, Long userId, Long orderId)
            throws JsonProcessingException;

    void checkLoginStatus(Long userId);
}

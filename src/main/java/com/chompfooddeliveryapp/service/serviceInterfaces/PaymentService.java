package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ProcessPaymentRequest;
import com.chompfooddeliveryapp.dto.VerificationResponse;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PaymentService {

    Object processPayment(ProcessPaymentRequest request, Long userId);

    VerificationResponse verifyPayStackPayment(VerifyTransactionDto transactionDto) throws JsonProcessingException;
}

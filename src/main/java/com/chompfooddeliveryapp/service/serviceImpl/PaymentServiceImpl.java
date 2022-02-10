package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.*;
import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.enums.OrderStatus;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionStatus;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.wallets.Transaction;
import com.chompfooddeliveryapp.repository.OrderRepository;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Objects;


@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaystackServiceImpl payStackService;
    private final WalletWithdrawServiceImpl withdrawService;
    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentServiceImpl(PaystackServiceImpl payStackService, WalletWithdrawServiceImpl withdrawService,
             TransactionRepository transactionRepository,OrderRepository orderRepository) {

        this.payStackService = payStackService;
        this.withdrawService = withdrawService;
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
    }

    //todo: Makera has refused to convert the Object type to Paystack custom class
    @Override
    public Object processPayment(ProcessPaymentRequest request, Long userId, Long orderId /**paymentDTO to contain the amount to be paid and the payment method**/)
    {

        //todo: Check the incoming request to see if the user wants to pay by card or wallet
        if (request.getPaymentMethod().equals(PaymentMethod.PAYSTACK.name())){
            PayStackRequestDto requestDto = new PayStackRequestDto();
            requestDto.setAmount(request.getAmount());
          return   payStackService.initializePaystackTransaction(requestDto,userId, TransactionType.DEBIT);
            //todo: Makera will add callback URL in front end to redirect user to verification and order confirmation

        }

        //todo: check if the payment method from the dto is for paystack by comparing it with the dto
        if(request.getPaymentMethod().equals(PaymentMethod.EWALLET.name())){
            WithdrawalRequest walletWithdraw = new WithdrawalRequest();
            walletWithdraw.setBill(request.getAmount());
            walletWithdraw.setUserId(userId);
           ResponseEntity<WithdrawalDto> output = withdrawService.walletWithdraw(walletWithdraw);
            if(Objects.equals(Objects.requireNonNull(output.getBody()).getMessageStatus(),"Withdrawal successful!")){
                Order userOrder = orderRepository.findOrderByIdAndUserId(orderId,userId)
                        .orElseThrow(() -> new BadRequestException("This order has not been made"));
                userOrder.setStatus(OrderStatus.CONFIRMED);
            }
            return withdrawService.walletWithdraw(walletWithdraw);
        }

        return null;
    }

    @Override
    public VerificationResponse verifyPayStackPayment(VerifyTransactionDto transactionDto, Long userId, Long orderId)
            throws JsonProcessingException {
        PayStackResponseDto payStack =  payStackService.verifyPaystackTransaction(transactionDto);
        VerificationResponse response = new VerificationResponse();
        response.setStatus(payStack.getStatus());
        response.setMessage(payStack.getMessage());
        response.setAmount(payStack.getData().get("amount"));
        response.setPaymentDate(payStack.getData().get("paid_at"));
        if(!Objects.equals(payStack.getData().get("gateway_response"),"Successful")
                || !Objects.equals(payStack.getData().get("reference"),transactionDto.getTransactionReference())){
            throw new BadRequestException("Verification failed, Please contact your Bank");
        }
        Transaction transaction = transactionRepository
                .findById(transactionDto.getTransactionReference())
                .orElseThrow(() -> new BadRequestException("transaction doesn't exist"));
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        Order userOrder = orderRepository.findOrderByIdAndUserId(orderId,userId)
                .orElseThrow(() -> new BadRequestException("This order has not been made"));
        userOrder.setStatus(OrderStatus.CONFIRMED);
        transactionRepository.save(transaction);
        //todo: change the state of the transaction to successful

        //todo: change the status of the user's order to confirmed
        System.out.println(payStack.getData()+" Success");
        return response;

    }
}

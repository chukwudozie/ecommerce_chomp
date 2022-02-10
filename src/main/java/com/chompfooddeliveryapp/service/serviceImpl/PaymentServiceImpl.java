package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.*;
import com.chompfooddeliveryapp.exception.BadRequestException;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaystackServiceImpl payStackService;
    private final WalletWithdrawServiceImpl withdrawService;
    private final String secret;
    private final ObjectMapper objectMapper;
    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentServiceImpl(PaystackServiceImpl payStackService, WalletWithdrawServiceImpl withdrawService,
               @Value("${paystack.Secret}") String secret, ObjectMapper objectMapper,
             TransactionRepository transactionRepository,OrderRepository orderRepository) {

        this.payStackService = payStackService;
        this.withdrawService = withdrawService;
        this.secret = secret;
        this.objectMapper = objectMapper;
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
    }

    //todo: Makera has refused to convert the Object type to Paystack custom class
    @Override
    public Object processPayment(ProcessPaymentRequest request, Long userId, Long orderId /**paymentDTO to contain the amount to be paid and the payment method**/)
    {

        //todo: Check the incoming request to see if the user wants to pay by card or wallet
        if (request.getPaymentMethod().equals(PaymentMethod.PAYSTACK.name())){
            //todo: if the user selects payStack call the paystack service
            //todo: call the initialize payStack
            PayStackRequestDto requestDto = new PayStackRequestDto();
            requestDto.setAmount(request.getAmount());
            Order userOrder = orderRepository.findOrderByIdAndUserId(orderId,userId)
                    .orElseThrow(() -> new BadRequestException("This order has not been made"));
//            userOrder.setStatus;


          return   payStackService.initializePaystackTransaction(requestDto,userId, TransactionType.DEBIT);
            //todo: If payment is successful, Use the order service to set the status of the order to confirmed
            //todo: Makera will add callback URL in front end to redirect user to verification and order confirmation

        }

        //todo: check if the payment method from the dto is for paystack by comparing it with the dto
        if(request.getPaymentMethod().equals(PaymentMethod.EWALLET.name())){
            WithdrawalRequest walletWithdraw = new WithdrawalRequest();
            walletWithdraw.setBill(request.getAmount());
            walletWithdraw.setUserId(userId);
            var output = withdrawService.walletWithdraw(walletWithdraw);
            // todo: orderRepository.findOrderById(/**Find a way to pass the order id here**/)
            return withdrawService.walletWithdraw(walletWithdraw);
            //todo: if the user selects wallet transaction, call the withdraw from wallet
            //todo: If payment is successful, Use the order service to set the status of the order to confirmed

        }

        //todo: implement the necessary deductions from the corresponding account(or check the methods for that)
        return null;
    }

    @Override
    public VerificationResponse verifyPayStackPayment(VerifyTransactionDto transactionDto)
            throws JsonProcessingException {
        PayStackResponseDto payStack =  payStackService.verifyPaystackTransaction(transactionDto);
        VerificationResponse response = new VerificationResponse();
        response.setStatus(payStack.getStatus());
        response.setMessage(payStack.getMessage());
        response.setAmount(payStack.getData().get("amount"));
        response.setPaymentDate(payStack.getData().get("paid_at"));
        if(!Objects.equals(payStack.getData().get("gateway_response"),"Successful")
                || !Objects.equals(payStack.getData().get("reference"),transactionDto.getTransactionReference())){
//            System.out.println(payStack.getData().get("reference"));
//            System.out.println(transactionDto.getTransactionReference());
//            System.out.println(payStack.getData()+"No success");
            throw new BadRequestException("Verification failed, Please contact your Bank");
        }
        Transaction transaction = transactionRepository
                .findById(transactionDto.getTransactionReference())
                .orElseThrow(() -> new BadRequestException("transaction doesn't exist"));
//        System.out.println(transaction.getPaymentMethod());
//        System.out.println(transaction.getTransactionStatus());
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.save(transaction);
        //todo: change the state of the transaction to successful

        //todo: change the status of the user's order to confirmed
        System.out.println(payStack.getData()+" Success");
        return response;

    }
}

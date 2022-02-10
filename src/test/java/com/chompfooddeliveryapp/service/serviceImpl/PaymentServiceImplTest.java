package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.PayStackRequestDto;
import com.chompfooddeliveryapp.dto.WithdrawalRequest;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.OrderRepository;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    PaystackServiceImpl payStackService;

    @Mock
    WalletWithdrawServiceImpl withdrawService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    User user;


    @BeforeEach
    void setUp() {
        user = new User();
        PayStackRequestDto requestDto = new PayStackRequestDto();
        requestDto.setAmount(700);
        payStackService.initializePaystackTransaction(requestDto, user.getId(), TransactionType.DEBIT);

        WithdrawalRequest walletWithdraw = new WithdrawalRequest();
        walletWithdraw.setBill(700);
        walletWithdraw.setUserId(user.getId());

    }

    @Test
    void processPayment() {
    }

    @Test
    void verifyPayStackPayment() {
    }
}
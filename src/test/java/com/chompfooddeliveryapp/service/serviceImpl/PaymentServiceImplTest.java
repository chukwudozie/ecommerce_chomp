package com.chompfooddeliveryapp.service.serviceImpl;

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

    @BeforeEach
    void setUp() {
    }

    @Test
    void processPayment() {
    }

    @Test
    void verifyPayStackPayment() {
    }
}
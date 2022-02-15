package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.PayStackRequest;
import com.chompfooddeliveryapp.dto.ProcessPaymentRequest;
import com.chompfooddeliveryapp.dto.WithdrawalRequest;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.OrderRepository;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

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

    @Mock
    UserRepository userRepository;



    @InjectMocks
    PaymentServiceImpl paymentService;

    User user;
    Order order;


    @BeforeEach
    void setUp() {
        user = new User();
        order = new Order();
        PayStackRequest requestDto = new PayStackRequest();
        requestDto.setAmount(700);

        WithdrawalRequest walletWithdraw = new WithdrawalRequest();
        walletWithdraw.setBill(700);
        walletWithdraw.setUserId(user.getId());

    }

    @Test
    void processPayment() {
        user.setId(1L);
        order.setId(1L);
        order.setAmount(200.00);

        paymentService = mock(PaymentServiceImpl.class);
        ProcessPaymentRequest paymentRequest = new ProcessPaymentRequest();
        paymentRequest.setPaymentMethod(PaymentMethod.EWALLET.name());
        verify(orderRepository,times(0)).findOrderByIdAndUserId(order.getId(),user.getId());
    }

}
package com.chompfooddeliveryapp.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import com.chompfooddeliveryapp.model.enums.*;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.model.wallets.Transaction;
import com.chompfooddeliveryapp.model.wallets.Wallet;
import com.chompfooddeliveryapp.payload.WalletPayload;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    @Mock
    WalletRepository walletRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    WalletServiceImpl walletServiceImpl;
    Wallet wallet;
    Transaction transaction;
    User user;
    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        wallet.setId("chomp23");
        wallet.setAccountBalance(15200);
        wallet.setBaseCurrency(Currency.NGN);
        user = new User();
        user.setId(2L);
        user.setFirstName("mark");
        user.setLastName("Apiri");
        user.setEmail("makera@gmail.com");
        user.setPassword("123456789");
        user.setDob(null);
        user.setEnabled(true);
        user.setUserGender(UserGender.MALE);
        user.setWalletId(wallet);
        user.setSubscribed(true);
        transaction = new Transaction();
        transaction.setId("chomp45");
        transaction.setWallet(wallet);
        transaction.setPaymentMethod(PaymentMethod.PAYSTACK);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setUser(user);
    }

    @Test
    void fundUsersWallet() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(walletRepository.findById(wallet.getId())).thenReturn(Optional.of(wallet));
        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        WalletPayload walletPayload = new WalletPayload();
        walletPayload.setWalletId(wallet.getId());
        walletPayload.setUser_id(user.getId());
        walletPayload.setAmountCredited(20);
        walletPayload.setAccountBalance(15220);
        walletPayload.setCurrency(Currency.NGN);
        walletPayload.setTransactionStatus("SUCCESSFUL");
        final WalletPayload result = walletServiceImpl.
                fundUsersWallet(transaction.getId(),"true", "success", "2000");
        verify(userRepository, times(1)).findById(user.getId());
        verify(walletRepository, times(1)).findById(wallet.getId());
        verify(transactionRepository,times(1)).findById(transaction.getId());
        assertEquals(result, walletPayload);
    }
    @Test
    void getWalletBalance() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(walletRepository.findById(user.getWalletId().getId())).thenReturn(Optional.of(wallet));
        final String expected = "Account Balance: " + wallet.getAccountBalance();
        final String actual = walletServiceImpl.getWalletBalance(user.getId());
        verify(userRepository, times(1)).findById(user.getId());
        verify(walletRepository, times(1)).findById(wallet.getId());
        assertEquals(expected, actual);
    }
}

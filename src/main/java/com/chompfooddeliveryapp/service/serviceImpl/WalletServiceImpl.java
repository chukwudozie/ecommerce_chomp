package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionStatus;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.model.wallets.Transaction;
import com.chompfooddeliveryapp.model.wallets.Wallet;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.repository.WalletRepository;
import com.chompfooddeliveryapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class WalletServiceImpl{


    @Autowired
    private WebClient.Builder webCLientBuilder;
    @Autowired
    private final WalletRepository walletRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TransactionRepository transactionRepository;

    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }


    public String setFundWalletTransactionReference(long userId){
        //fund wallet validation
        Optional<User> user = userRepository.findById(userId);

        if (user.equals(null)){
            return "The User  cannot be null";
        }

        Optional<Wallet> wallet = walletRepository.findById(user.get().getWalletId().getId());

       //transaction creation
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setWallet(wallet.get());
        transaction.setPaymentMethod(PaymentMethod.PAYSTACK);
        transactionRepository.save(transaction);

        return transaction.getId();

    }


    public String getWalletBalance(Long userId) {
        User user  = userRepository.findById(userId).get();

        if (user.equals(null)){
            return "The User  cannot be null";
        }

        Wallet wallet = walletRepository.findById(user.getWalletId().getId()).get();
        return "Account Balance: "+ wallet.getAccountBalance();
    }





}

package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionStatus;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.model.wallets.Transaction;
import com.chompfooddeliveryapp.model.wallets.Wallet;
import com.chompfooddeliveryapp.payload.WalletPayload;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletServiceImpl {




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
        System.out.println(transaction.getId());
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setWallet(wallet.get());
        transaction.setPaymentMethod(PaymentMethod.PAYSTACK);
        transaction.setUser(user.get());
        transactionRepository.save(transaction);

        return transaction.getId();

    }

    public ResponseEntity<?> fundUsersWallet(String transactionId, String status, String dataStatus, String amount){
        Transaction transaction = transactionRepository.findById(transactionId).get();
        Optional<User> user = userRepository.findById(transaction.getUser().getId());
        if (user.equals(null)){
            return new ResponseEntity<>("must fund a wallet attached to this user", HttpStatus.BAD_REQUEST );
        }

        if (!(status.equals("true") && dataStatus.equals("success"))){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
        }

        Wallet wallet = walletRepository.findById(user.get().getWalletId().getId()).get();
        long creditAmount = Long.parseLong(amount);
        creditAmount /= 100;

        wallet.setAccountBalance(wallet.getAccountBalance() + creditAmount);
        walletRepository.save(wallet);

        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.save(transaction);

        WalletPayload walletPayload = new WalletPayload();
        walletPayload.setAccountBalance(wallet.getAccountBalance());
        walletPayload.setAmountcredited(creditAmount);
        walletPayload.setWalletId(wallet.getId());
        walletPayload.setUser_id(user.get().getId());

        return new ResponseEntity<>(walletPayload, HttpStatus.OK);
    }

}

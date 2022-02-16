package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.model.wallets.Transaction;
import com.chompfooddeliveryapp.model.wallets.Wallet;
import com.chompfooddeliveryapp.payload.WalletPayload;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.chompfooddeliveryapp.model.enums.TransactionStatus.*;

@Service
public class WalletServiceImpl{


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



    public WalletPayload fundUsersWallet(String transactionId, String status, String dataStatus, String amount){
        Transaction transaction = transactionRepository.findById(transactionId).get();

        if (!transaction.getTransactionStatus().equals(PENDING)){
            throw new BadRequestException("This transaction has already been settled");
        }
        Optional<User> user = Optional.ofNullable(userRepository.findById(transaction.getUser().getId()).orElseThrow(() -> new
                BadRequestException("must fund a wallet attached to a user")));

        Wallet wallet = walletRepository.findById(user.get().getWalletId().getId()).get();
        WalletPayload walletPayload = new WalletPayload();


        if (!(status.equals("true") && dataStatus.equals("success"))){
            transaction.setTransactionStatus(FAILED);
            walletPayload.setTransactionStatus(FAILED.toString());
            walletPayload.setWalletId(wallet.getId());
            walletPayload.setUser_id(user.get().getId());
            walletPayload.setAccountBalance(wallet.getAccountBalance());
            transactionRepository.save(transaction);
            return walletPayload;
        }

        long creditAmount = Long.parseLong(amount);
        creditAmount /= 100;


        wallet.setAccountBalance(wallet.getAccountBalance() + creditAmount);
        walletRepository.save(wallet);

        transaction.setTransactionStatus(SUCCESSFUL);
        transactionRepository.save(transaction);


        walletPayload.setTransactionStatus(SUCCESSFUL.toString());
        walletPayload.setAccountBalance(wallet.getAccountBalance());
        walletPayload.setAmountCredited(creditAmount);
        walletPayload.setWalletId(wallet.getId());
        walletPayload.setUser_id(user.get().getId());

        return walletPayload;
    }



    public String getWalletBalance(Long userId) {
        Optional<User> user  = Optional.ofNullable(userRepository.findById(userId).
                orElseThrow(() -> new BadRequestException("the user cannot be null")));


        Wallet wallet = walletRepository.findById(user.get().getWalletId().getId()).get();
        return "Account Balance: "+ wallet.getAccountBalance();
    }

}

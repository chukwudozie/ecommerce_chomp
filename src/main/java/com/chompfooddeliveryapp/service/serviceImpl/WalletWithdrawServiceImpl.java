package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.WithdrawalDto;
import com.chompfooddeliveryapp.dto.WithdrawalRequest;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionStatus;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.model.wallets.Transaction;
import com.chompfooddeliveryapp.model.wallets.Wallet;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WalletWithdrawServiceImpl {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public WalletWithdrawServiceImpl(WalletRepository walletRepository,
                                     UserRepository userRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<WithdrawalDto> walletWithdraw(WithdrawalRequest withdrawalRequest) {
        User user  = userRepository.findById(withdrawalRequest.getUserId()).orElseThrow(
                ()-> new UsernameNotFoundException("user with this " + withdrawalRequest.getUserId() +  " is not found")
        );

        Wallet wallet = walletRepository.findById(user.getWalletId().getId()).orElseThrow(
                ()-> new RuntimeException("User wallet not found")
        );

        long checkBalance = wallet.getAccountBalance();
        long amount = withdrawalRequest.getBill();

        WithdrawalDto withdrawal = new WithdrawalDto();

        withdrawal.setAmount(amount);
        withdrawal.setEmail(user.getEmail());

        if(checkBalance < amount){
            withdrawal.setMessageStatus("Insufficient fund in the wallet!");
            withdrawal.setAccountBalance(checkBalance);
            return ResponseEntity.badRequest().body(withdrawal);
        }else {

            long newBalance = checkBalance - amount;

            Transaction transaction = new Transaction();

            wallet.setAccountBalance(newBalance);

            walletRepository.save(wallet);

            transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
            transaction.setTransactionType(TransactionType.DEBIT);
            transaction.setPaymentMethod(PaymentMethod.EWALLET);
            transaction.setUser(user);
            transaction.setWallet(wallet);

            transactionRepository.save(transaction);

            withdrawal.setAccountBalance(newBalance);
            withdrawal.setMessageStatus("Withdrawal successful!");

            return ResponseEntity.ok().body(withdrawal);
        }

    }

}

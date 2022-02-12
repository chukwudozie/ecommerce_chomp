package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionStatus;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.model.wallets.Transaction;
import com.chompfooddeliveryapp.model.wallets.Wallet;
import com.chompfooddeliveryapp.repository.TransactionRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(WalletRepository walletRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }


    public String getTransactionRefence (long userId, TransactionType type, PaymentMethod paymentMethod){
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).
                orElseThrow(() -> new BadRequestException("the user cannot be null")));

        Optional<Wallet> wallet = walletRepository.findById(user.get().getWalletId().getId());

        //transaction creation
        Transaction transaction = new Transaction();
        if (type.equals(TransactionType.CREDIT)){
            transaction.setId(transaction.getId().replace("CHOMPT", "CHOMPFND"));
            transaction.setPaymentMethod(PaymentMethod.PAYSTACK);

        }
        if (type.equals(TransactionType.DEBIT) && paymentMethod.equals(PaymentMethod.PAYSTACK)){
            transaction.setId(transaction.getId().replace("CHOMPT", "CHOMPPSTK"));
            transaction.setPaymentMethod(PaymentMethod.PAYSTACK);
        }
        if (type.equals(TransactionType.DEBIT) && paymentMethod.equals(PaymentMethod.EWALLET)){
            transaction.setId(transaction.getId().replace("CHOMPT", "CHOMPWTH"));
            transaction.setPaymentMethod(PaymentMethod.EWALLET);
        }

        transaction.setWallet(wallet.get());
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setTransactionType(type);
        transaction.setUser(user.get());
        transactionRepository.save(transaction);
        return transaction.getId();
    }


}

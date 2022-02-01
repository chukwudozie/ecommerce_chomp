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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class WalletServiceImpl {

    private final String secret_key = "sk_test_2d5c43445c023f91a8f13334df77580390a395c9";

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



    public String initializeFundWallet(long userId, long walletId){

        //fund wallet validation
        Optional<User> user = userRepository.findById(userId);
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (user.equals(null) || wallet.equals(null)){
            return "The User or the wallet cannot be null";
        }
       if (!user.get().getId().equals(walletId)){
           return "You cannot fund another person's wallet";
       }

       //transaction creation
        Transaction transaction = new Transaction();
        System.out.println(transaction.getId());
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setWalletId(walletId);
        transaction.setPaymentMethod(PaymentMethod.PAYSTACK);
        transactionRepository.save(transaction);
        return transaction.getId();

    }



//    public Wallet createUserWallet (Long user_id) throws EntityExistsException {
//        User user = userRepository.findById(user_id).orElseThrow(() -> new EntityNotFoundException("User not found"));
//        if (!walletRepository.findByUserId(user_id).equals(null)){
//            throw  new EntityExistsException("This user already has a wallet");
//        }
//            Wallet userWallet = new Wallet(user);
//            walletRepository.save(userWallet);
//
//        Wallet savedWallet = walletRepository.findByUserId(user_id);
//
//        return savedWallet;
//    }


}

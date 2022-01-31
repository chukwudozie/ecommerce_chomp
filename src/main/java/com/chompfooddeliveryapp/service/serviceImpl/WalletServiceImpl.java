package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.model.enums.Currency;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.WalletPayload;
import com.chompfooddeliveryapp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class WalletServiceImpl {

    @Autowired
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public ResponseEntity<WalletPayload> createUserWallet (long user_id) throws EntityExistsException {
        User user = new User();
        if (!walletRepository.findByUserIdIsNull(user_id)){
            throw  new EntityExistsException("This user already has a wallet");
        }
        return ResponseEntity.ok(new WalletPayload(1, user, Currency.NGN, 0));
    }


}

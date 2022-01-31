package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.wallets.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Boolean findByUserIdIsNull(long userid);
}

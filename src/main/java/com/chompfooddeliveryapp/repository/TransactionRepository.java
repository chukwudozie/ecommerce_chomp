package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.wallets.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}

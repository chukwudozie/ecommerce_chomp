package com.chompfooddeliveryapp.model.wallets;

import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionStatus;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "id", nullable = false)
    private String id = "CHOMPT" + UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Wallet.class)
    @JoinColumn(name = "wallet_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Wallet wallet;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


}

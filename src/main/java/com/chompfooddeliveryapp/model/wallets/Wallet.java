package com.chompfooddeliveryapp.model.wallets;

import com.chompfooddeliveryapp.model.enums.Currency;
import com.chompfooddeliveryapp.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    private Long accountBalance;

    private Currency baseCurrency;
}

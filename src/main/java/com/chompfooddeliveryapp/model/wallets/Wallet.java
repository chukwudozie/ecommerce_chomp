package com.chompfooddeliveryapp.model.wallets;

import com.chompfooddeliveryapp.model.enums.Currency;
import com.chompfooddeliveryapp.model.users.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    @Column(name = "id", nullable = false)
    private String id = "chompW" + UUID.randomUUID().toString();

    @NotNull
    private long accountBalance;

    @Column(name = "base_currency")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Currency baseCurrency = Currency.NGN;


}

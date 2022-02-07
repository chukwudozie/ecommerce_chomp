package com.chompfooddeliveryapp.payload;

import com.chompfooddeliveryapp.model.enums.Currency;
import com.chompfooddeliveryapp.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletPayload {
    private String  walletId;
    private long user_id;
    private Currency currency = Currency.NGN;
    private long accountBalance;
    private long amountCredited;
    private String transactionStatus;
    private String gatewayResponse;
}

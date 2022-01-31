package com.chompfooddeliveryapp.payload;

import com.chompfooddeliveryapp.model.enums.Currency;
import com.chompfooddeliveryapp.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletPayload {
    private long id;
    private User userId;
    private Currency currency;
    private long accountBalance;
}

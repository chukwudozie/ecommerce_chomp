package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.enums.Currency;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayStackRequestDto {
    private String email;
    private long amount;
    private String[] channels;
    private String reference;
//    private String callback_url;
}

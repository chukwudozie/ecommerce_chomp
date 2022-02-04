package com.chompfooddeliveryapp.dto;

import lombok.Data;

@Data
public class WithdrawalDto {
    private String email;
    private long amount;
    private long accountBalance;
    private String messageStatus;
}

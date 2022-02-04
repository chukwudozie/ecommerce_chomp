package com.chompfooddeliveryapp.dto;

import lombok.Data;

@Data
public class WithdrawalRequest {
    private Long userId;
    private long bill;
}

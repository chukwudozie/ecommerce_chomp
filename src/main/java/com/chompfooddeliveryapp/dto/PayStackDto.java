package com.chompfooddeliveryapp.dto;

import lombok.Data;

import java.util.Map;
@Data
public class PayStackDto {
    String status;
    String message;
    Map<String, Object> data;

}

package com.chompfooddeliveryapp.payload;

import lombok.Data;

import java.util.Map;
@Data
public class PayStackResponse {
    String status;
    String message;
    Map<String, Object> data;

}

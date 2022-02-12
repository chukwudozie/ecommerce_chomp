package com.chompfooddeliveryapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageResponse {
    private String response;
    private final String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}

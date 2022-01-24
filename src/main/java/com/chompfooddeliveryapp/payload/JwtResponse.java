package com.chompfooddeliveryapp.payload;

import com.chompfooddeliveryapp.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String token;

    private String type;

    private Long id;

    private UserRole role;
}

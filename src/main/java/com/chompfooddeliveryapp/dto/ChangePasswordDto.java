package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.users.User;
import lombok.*;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}



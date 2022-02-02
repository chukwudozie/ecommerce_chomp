package com.chompfooddeliveryapp.forgotpassword.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserForgotPasswordDto {
    private String first_name;
    private String last_name;
    private String email;

}

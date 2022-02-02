package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.enums.UserGender;
import com.chompfooddeliveryapp.model.enums.UserRole;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class SignupDto {

    private String email;

    private String firstName;

    private String lastName;

    private String password;

//    private UserRole roles;

    public SignupDto() {

    }
}

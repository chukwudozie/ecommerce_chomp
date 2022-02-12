package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.enums.UserGender;
import lombok.*;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDetailsDto {
    private String firstname;
    private String lastname;
    private String email;
    private UserGender gender;
    private Date dateOfBirth;

}

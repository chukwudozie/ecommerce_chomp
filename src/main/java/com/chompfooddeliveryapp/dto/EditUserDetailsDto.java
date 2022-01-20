package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.users.Gender;
import lombok.*;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDetailsDto {
    private String firstname;
    private String lastname;
    private String email;
//    private Gender gender;
    private Date dateOfBirth;

}

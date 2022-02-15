package com.chompfooddeliveryapp.payload;

import com.chompfooddeliveryapp.model.enums.UserGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminInfoResponse {
    private String firstName;

    private String lastName;

    private String email;

    private String gender;

    private Date dateOfBirth;

}

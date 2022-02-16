package com.chompfooddeliveryapp.dto;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class SignupDto {

    @Email(message = "Enter a valid Email")
    private String email;
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, message = "First name must have a minimum of 2 characters and above")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, message = "Last name must have a minimum of 2 characters and above")
    private String lastName;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be upto 8 characters long and must contain at least one capital letter, one small letter and special character")
    private String password;
    public SignupDto() {
    }
}




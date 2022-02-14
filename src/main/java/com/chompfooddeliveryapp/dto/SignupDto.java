package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.enums.UserGender;
import com.chompfooddeliveryapp.model.enums.UserRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

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
    @Size(min = 8, max = 16, message = "Password must be between 8 and i6 characters long")
    private String password;

    public SignupDto() {
    }
}

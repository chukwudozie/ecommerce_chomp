package com.chompfooddeliveryapp.model.users;

import com.chompfooddeliveryapp.model.enums.UserGender;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private UserGender gender;

    @NotNull
    private UserRole role;

    @NotNull
    private Boolean subscribed;

}

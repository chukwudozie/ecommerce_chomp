package com.chompfooddeliveryapp.model.users;

import com.chompfooddeliveryapp.model.enums.UserGender;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.wallets.Wallet;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;


    private String password;

    private Date dob;

    private Boolean enabled = false;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private  UserGender userGender;

    @OneToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet walletId;

//    @Column(name = "role")
//    @Enumerated(EnumType.STRING)
//    private UserRole userRole;

    @OneToOne
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;

    @NotNull
    private Boolean subscribed = false;


    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

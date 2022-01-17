package com.chompfooddeliveryapp.model.users;

import com.chompfooddeliveryapp.model.enums.UserGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
//@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "roles")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserGender gender;
}

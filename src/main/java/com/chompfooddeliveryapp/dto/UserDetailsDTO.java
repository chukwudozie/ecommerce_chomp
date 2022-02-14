package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.model.users.ShippingAddress;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Data
public class UserDetailsDTO {
    private String name;

    private String email;

    private ShippingAddress shippingAddressDTO;

    private String walletBalance;

    private ResponseViewUserOrdersDTO viewUserOrdersDTOS;

    private List<FavoriteMeal> favoriteDto;

}

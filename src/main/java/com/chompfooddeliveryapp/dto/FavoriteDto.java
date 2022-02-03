package com.chompfooddeliveryapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {

    private Long id;

    private Long user_id;

    private Long menu_id;
}

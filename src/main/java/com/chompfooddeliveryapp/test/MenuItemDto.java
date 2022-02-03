package com.chompfooddeliveryapp.test;

import com.chompfooddeliveryapp.model.enums.MenuCategory;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class MenuItemDto {


   private String name;
   @NotNull
   private String image;

   @NotNull
   private Long price;

   @NotNull
   private MenuCategory category;

   @NotNull
   private Timestamp dateCreated;

}

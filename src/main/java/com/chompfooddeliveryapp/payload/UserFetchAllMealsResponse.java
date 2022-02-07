

package com.chompfooddeliveryapp.payload;

import com.chompfooddeliveryapp.model.meals.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFetchAllMealsResponse {
    private String status;
    private List<MenuItem> menuItemList;
}

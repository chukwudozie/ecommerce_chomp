package com.chompfooddeliveryapp.payload;

import com.chompfooddeliveryapp.dto.ViewOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderDetailsResponse {
    List<ViewOrderDTO> viewOrderDtoList;
}

package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ViewOrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    List<ViewOrderDTO> getOrderDetails(Long userId, Long orderId);
}

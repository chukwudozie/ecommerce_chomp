package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ViewOrderDTO;
import org.springframework.stereotype.Service;

public interface OrderService {

    ViewOrderDTO getOrderDetails(Long OrderId);
}

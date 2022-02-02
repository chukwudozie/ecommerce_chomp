package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.OrderSummaryDTO;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.orders.OrderDetail;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.repository.OrderDetailsRepository;
import com.chompfooddeliveryapp.repository.OrderRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public CheckoutServiceImpl(MenuItemRepository menuItemRepository,
                                    OrderRepository orderRepository,
                                    OrderDetailsRepository orderDetailsRepository) {
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public List<MenuItem> getOrderSummary(Long userId) {
        // get order by UserId
        var listOfOrdersByUser = orderRepository.findAllByUserId(userId);

        // get order details by orderId
        List<List<OrderDetail>> listOfOrderDetailsOfListByOrderId = listOfOrdersByUser.stream()
                .map(orderByUser -> orderDetailsRepository.findAllByOrderIdEquals(orderByUser.getId()))
                .collect(Collectors.toList());

        //get MenuItem

        var listOfMenuItems = listOfOrderDetailsOfListByOrderId.stream()
                .flatMap(orderDetailList -> menuItemRepository.findAllById(orderDetailList.stream()
                                                                                          .map(OrderDetail::getMenuId)
                                                                                          .collect(Collectors.toList()))
                                                              .stream())
                .collect(Collectors.toList());


        var listOfOrderSummaryDTO = listOfMenuItems.stream().map(x -> {
            var orderSummaryDTO = new OrderSummaryDTO();
            orderSummaryDTO.setImage(x.getImage());
            orderSummaryDTO.setName(x.getName());
            orderSummaryDTO.setPrice(x.getPrice());
            orderSummaryDTO.setQuantity(1L);
            return orderSummaryDTO;
        }) .collect(Collectors.toList());


        return listOfMenuItems;
    }


}

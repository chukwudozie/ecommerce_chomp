package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.OrderSummaryDTO;
import com.chompfooddeliveryapp.dto.ViewUserOrdersDTO;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.orders.OrderDetail;
import com.chompfooddeliveryapp.repository.*;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckoutServiceImpl(MenuItemRepository menuItemRepository,
                               OrderRepository orderRepository,
                               OrderDetailsRepository orderDetailsRepository,
                               ShippingAddressRepository shippingAddressRepository,
                               UserRepository userRepository) {
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.userRepository = userRepository;
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


    public List<ViewUserOrdersDTO> getAllOrdersByUserId(Long userId) {
        //get all Orders with User ID
        var opUser = userRepository.findById(userId);
        var user = opUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User with " + userId + " not found"));
        var listOfOrdersByUser = orderRepository.findAllByUserId(userId);
        var listOfUserOrdersDto = listOfOrdersByUser.stream()
                .map(order -> {
                    var userOrdersDTO = new ViewUserOrdersDTO();
                    userOrdersDTO.setImage("/path/to/image.png");
                    userOrdersDTO.setOrder_date(order.getOrder_date());
                    userOrdersDTO.setDelivered_date(order.getDelivered_date());
                    userOrdersDTO.setStatus(order.getStatus().toString());
                    userOrdersDTO.setDeliveryMethod("");
                    return userOrdersDTO;
                }).collect(Collectors.toList());
        var userShippingAddress =
                shippingAddressRepository.findByUserAndDefaultAddress(user, true);

        return listOfUserOrdersDto;
    }
}

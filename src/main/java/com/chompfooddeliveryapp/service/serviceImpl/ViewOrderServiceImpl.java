package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.*;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.orders.OrderDetail;
import com.chompfooddeliveryapp.model.users.ShippingAddress;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.payload.ViewCartResponse;
import com.chompfooddeliveryapp.repository.*;
import com.chompfooddeliveryapp.service.serviceInterfaces.CartService;
import com.chompfooddeliveryapp.service.serviceInterfaces.ViewOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewOrderServiceImpl implements ViewOrderService {

    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    @Autowired
    public ViewOrderServiceImpl(MenuItemRepository menuItemRepository,
                                OrderRepository orderRepository,
                                OrderDetailsRepository orderDetailsRepository,
                                ShippingAddressRepository shippingAddressRepository,
                                UserRepository userRepository, CartService cartService) {
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    @Override
    public List<MenuItem> getOrderSummary(Long userId) {

        var listOfOrdersByUser = orderRepository.findAllByUserId(userId);
        List<List<OrderDetail>> listOfOrderDetailsOfListByOrderId = listOfOrdersByUser.stream()
                .map(orderByUser -> orderDetailsRepository.findAllByOrderIdEquals(orderByUser.getId()))
                .collect(Collectors.toList());


        var listOfMenuItems = listOfOrderDetailsOfListByOrderId.stream()
                .flatMap(orderDetailList -> menuItemRepository.findAllById(orderDetailList.stream()
                                                                                          .map(OrderDetail::getId)
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

    @Override
    public ResponseViewUserOrdersDTO getAllOrdersByUserId(Long userId) {

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

        ShippingAddressDTO shippingAddressDTO = new ShippingAddressDTO();
        userShippingAddress.ifPresent(shippingAddress -> {
            shippingAddressDTO.setEmail(shippingAddress.getEmail());
            shippingAddressDTO.setFullName(shippingAddress.getFullName());
            shippingAddressDTO.setCity(shippingAddress.getCity());
            shippingAddressDTO.setState(shippingAddress.getState());
            shippingAddressDTO.setStreet(shippingAddress.getStreet());
            shippingAddressDTO.setPhone(shippingAddress.getPhone());
            shippingAddressDTO.setDefaultAddress(shippingAddress.getDefaultAddress());
        });

        List<MenuItem> menuItemsList = getOrderSummary(userId);

        long itemsTotal = menuItemsList.stream().mapToLong(MenuItem::getPrice).reduce(0L, Long::sum);
        long VAT = 10L;
        long deliveryFee = VAT * menuItemsList.size() * 300;

        PaymentDetailsDTO paymentDetailsDTO = new PaymentDetailsDTO();
        paymentDetailsDTO.setItemsTotal(itemsTotal);
        paymentDetailsDTO.setDeliveryFee(deliveryFee);
        paymentDetailsDTO.setTotal(itemsTotal + deliveryFee);


        return new ResponseViewUserOrdersDTO(listOfUserOrdersDto, shippingAddressDTO, paymentDetailsDTO);
    }

    @Override
    public AllCartItems checkoutCartItems(Long userId) {
        List<ViewCartResponse> checkoutDetails = cartService.getAllProductsByUser(userId);
        AllCartItems allCartItems = new AllCartItems();
        allCartItems.setUsersCartItems(checkoutDetails);
        return allCartItems;
    }


}

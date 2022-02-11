package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.*;
import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.orders.OrderDetail;
import com.chompfooddeliveryapp.model.users.ShippingAddress;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.payload.ViewCartResponse;
import com.chompfooddeliveryapp.repository.*;
import com.chompfooddeliveryapp.service.serviceInterfaces.CartService;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final MenuItemRepository menuItemRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final CartService cartService;

    @Autowired
    public OrderServiceImplementation(UserRepository userRepository, OrderRepository orderRepository,
                                      OrderDetailsRepository orderDetailsRepository, MenuItemRepository menuItemRepository, ShippingAddressRepository shippingAddressRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.menuItemRepository = menuItemRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartService = cartService;
    }

    @Override
    public List<ViewOrderDTO> getOrderDetails(Long userId, Long orderId) {
        var userOp = userRepository.findUserById(userId);

        var user = userOp.orElseThrow(() -> new BadRequestException("No Such User"));

        System.out.println(user.toString());

        List<Order> orderList = orderRepository.findByUserId(userId);
        var order1 = orderRepository.getOrderByIdIsAndUserIdIs(orderId, userId);

        var orders = order1.orElseThrow(() -> new BadRequestException("No order with Order id: " + orderId + " found"));

        var orderDetail = orderDetailsRepository
                .findAllByOrderId(orders.getId());
        var listOfMenuIds = orderDetail.stream()
                .map(x -> x.getMenu().getId())
                .collect(Collectors.toList());
        var listOfMenuItems = menuItemRepository.findAllById(listOfMenuIds);

        System.out.println(listOfMenuIds);

        var listViewOrderDTO = listOfMenuItems.stream()
                .map(menuItem -> {
                    ViewOrderDTO viewOrderDetails = new ViewOrderDTO();
                    viewOrderDetails.setImage(menuItem.getImage());
                    viewOrderDetails.setName(menuItem.getName());
                    viewOrderDetails.setDescription(menuItem.getDescription());
                    viewOrderDetails.setPrice(menuItem.getPrice());
                    viewOrderDetails.setOrderDate(orders.getOrder_date());
                    viewOrderDetails.setDeliveredDate(orders.getDelivered_date());
                    viewOrderDetails.setStatus(orders.getStatus());
                    viewOrderDetails.setQuantity(0L);
                    return viewOrderDetails;
                })
                .collect(Collectors.toList());

        return listViewOrderDTO;
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

        ShippingAddressDTO shippingAddressDTO = shippinAddresResponse(userShippingAddress);

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

    static ShippingAddressDTO shippinAddresResponse(Optional<ShippingAddress> userShippingAddress) {
        ShippingAddressDTO shippingAddressDTO = new ShippingAddressDTO();
        userShippingAddress.ifPresent(shippingAddress -> {
            mapShippingAddress(shippingAddressDTO, shippingAddress);
        });
        return shippingAddressDTO;
    }

    static void mapShippingAddress(ShippingAddressDTO shippingAddressDTO, ShippingAddress shippingAddress) {
        shippingAddressDTO.setEmail(shippingAddress.getEmail());
        shippingAddressDTO.setFullName(shippingAddress.getFullName());
        shippingAddressDTO.setCity(shippingAddress.getCity());
        shippingAddressDTO.setState(shippingAddress.getState());
        shippingAddressDTO.setStreet(shippingAddress.getStreet());
        shippingAddressDTO.setPhone(shippingAddress.getPhone());
        shippingAddressDTO.setDefaultAddress(shippingAddress.getDefaultAddress());
    }

    @Override
    public AllCartItems checkoutCartItems(Long userId) {
        List<ViewCartResponse> checkoutDetails = cartService.getAllProductsByUser(userId);
        AllCartItems allCartItems = new AllCartItems();
        allCartItems.setUsersCartItems(checkoutDetails);
        return allCartItems;
    }


}
package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.*;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.orders.OrderDetail;
import com.chompfooddeliveryapp.model.users.ShippingAddress;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.payload.ViewCartResponse;
import com.chompfooddeliveryapp.payload.ViewOrderDetailsResponse;
import com.chompfooddeliveryapp.repository.*;
import com.chompfooddeliveryapp.service.serviceInterfaces.CartService;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public ViewOrderDetailsResponse getOrderDetails(Long userId, Long orderId) {

        List<OrderDetail> orderDetail = orderDetailsRepository.findAllByOrderId(orderId);

        List<ViewOrderDTO> viewOrderDTOS = new ArrayList<>();
        for (OrderDetail order:orderDetail) {
            MenuItem menuItem = menuItemRepository.findById(order.getMenu().getId()).get();
            Order orders = orderRepository.findById(order.getOrder().getId()).get();
            ViewOrderDTO viewOrderDetails = new ViewOrderDTO();
            viewOrderDetails.setImage(menuItem.getImage());
            viewOrderDetails.setName(menuItem.getName());
            viewOrderDetails.setDescription(menuItem.getDescription());
            viewOrderDetails.setPrice(menuItem.getPrice());
            viewOrderDetails.setOrderDate(orders.getOrder_date());
            viewOrderDetails.setDeliveredDate(orders.getDelivered_date());
            viewOrderDetails.setStatus(orders.getStatus());
            viewOrderDetails.setQuantity(order.getQuantity());
            viewOrderDTOS.add(viewOrderDetails);
        }
        ViewOrderDetailsResponse viewOrderDetailsResponse = new ViewOrderDetailsResponse();
       viewOrderDetailsResponse.setViewOrderDtoList(viewOrderDTOS);

        return viewOrderDetailsResponse;
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

        ShippingAddressDTO shippingAddressDTO = shippingAddressResponse(userShippingAddress);

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

    static ShippingAddressDTO shippingAddressResponse(Optional<ShippingAddress> userShippingAddress) {
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

    public List<AdminViewOrderDTO> fetchAllOrdersToAdminDashboard() {

        List<Order> orderList = orderRepository.findAll();
        List<List<OrderDetail>> listOfOrderDetailsList = orderList.stream()
                                                                  .map(order -> orderDetailsRepository.findAllByOrder_Id(order.getId()))
                                                                  .collect(Collectors.toList());
        List<AdminViewOrderDTO> adminOrderList = orderList.stream().map(order -> {
            AdminViewOrderDTO response = new AdminViewOrderDTO();
            response.setStatus(order.getStatus());
            response.setAmount(order.getAmount());
            response.setDateOrdered(LocalDateTime.parse(order.getOrder_date().toString()));
            response.setCustomerAddress(getShippingAddress(order.getUser()));
            response.setCustomerEmail(order.getUser().getEmail());
            response.setCustomerName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
            response.setPaymentType(PaymentMethod.EWALLET);
            response.setCustomerOrderQuantity(orderDetailsRepository.findAllByOrder_Id(order.getId())
                                                                    .stream()
                                                                    .mapToLong(OrderDetail::getQuantity)
                                                                    .sum()
                                             );
            response.setAmount(order.getAmount());
            return response;
        }).collect(Collectors.toList());

        if (adminOrderList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            return adminOrderList;
        }
    }

    private String getShippingAddress(User user) {
        Optional<ShippingAddress> optionalShippingAddress = shippingAddressRepository.findByUserAndDefaultAddress(user, true);
        if (optionalShippingAddress.isPresent()) {
            ShippingAddress shippingAddress = optionalShippingAddress.get();
            return shippingAddress.getStreet() + "\n" +shippingAddress.getCity() + "\n" +shippingAddress.getState();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user: " + user.getFirstName() + " " + user.getLastName() +
                    " does not have a default shipping Address");
        }
    }



}
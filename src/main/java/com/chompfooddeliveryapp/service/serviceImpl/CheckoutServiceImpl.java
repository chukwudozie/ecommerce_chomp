package com.chompfooddeliveryapp.service.serviceImpl;


import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.model.carts.Cart;
import com.chompfooddeliveryapp.model.carts.CartItem;
import com.chompfooddeliveryapp.model.enums.OrderStatus;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.orders.OrderDetail;
import com.chompfooddeliveryapp.model.users.ShippingAddress;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.*;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final UserRepository userRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartItemRepository cartItemRepository;

    public CheckoutServiceImpl(UserRepository userRepository, ShippingAddressRepository shippingAddressRepository,
                               CartRepository cartRepository, OrderRepository orderRepository,
                               OrderDetailsRepository orderDetailsRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    public ShippingAddress saveShippingAddress(long userId, ShippingAddressDTO shippingAddressDTO) {
        var opUser = userRepository.findById(userId);
        ShippingAddress shippingAddress = new ShippingAddress();
        if (opUser.isPresent()) {
            shippingAddress.setEmail(shippingAddressDTO.getEmail());
            shippingAddress.setFullName(shippingAddressDTO.getFullName());
            shippingAddress.setDefaultAddress(shippingAddressDTO.getDefaultAddress());
            shippingAddress.setCity(shippingAddressDTO.getCity());
            shippingAddress.setPhone(shippingAddressDTO.getPhone());
            shippingAddress.setStreet(shippingAddressDTO.getStreet());
            shippingAddress.setState(shippingAddressDTO.getState());
            shippingAddress.setUser(opUser.get());

            return shippingAddressRepository.save(shippingAddress);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with id " + userId + " was not found");
        }
    }

    public Order createOrderFromCartItem(long userId, long cartId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        User user = optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                                    "User with Id " + userId + " does not exist"));

        Optional<Cart> optionalUserCart = cartRepository.findByIdAndUser_Id(cartId, userId);

        Cart cart = optionalUserCart.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "cart with id: " + cartId
        + " cannot be found for userId: " + userId));

        List<CartItem> listOfCartItems = cartItemRepository.findAllByCart_Id(cartId);

        if (listOfCartItems.size() < 1) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        var amount = listOfCartItems.stream()
                .map(cartItem -> cartItem.getMenuId().getPrice() * cartItem.getQuantity()).reduce(0L, Long::sum);

        Order newOrder = new Order();

        newOrder.setUser(user);
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setOrder_date(Timestamp.valueOf(LocalDateTime.now()));
        newOrder.setDelivered_date(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        newOrder.setAmount(amount);

        Order order = orderRepository.save(newOrder);

        List<OrderDetail> orderDetailList = listOfCartItems.stream().map(cartItem -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setMenu(cartItem.getMenuId());
            orderDetail.setQuantity((long) cartItem.getQuantity());
            orderDetail.setOrder(order);
            return orderDetailsRepository.save(orderDetail);
        }).collect(Collectors.toList());

        return order;
    }
}

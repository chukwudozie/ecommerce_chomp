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
import com.chompfooddeliveryapp.payload.response.CheckoutResponse;
import com.chompfooddeliveryapp.payload.response.ProductSummary;
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
    private final MenuItemRepository menuItemRepository;

    public CheckoutServiceImpl(UserRepository userRepository, ShippingAddressRepository shippingAddressRepository,
                               CartRepository cartRepository, OrderRepository orderRepository,
                               OrderDetailsRepository orderDetailsRepository, CartItemRepository cartItemRepository,
                               MenuItemRepository menuItemRepository) {
        this.userRepository = userRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.cartItemRepository = cartItemRepository;
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public List<String> saveShippingAddress(long userId, ShippingAddressDTO shippingAddressDTO) {
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

            ShippingAddress s = shippingAddressRepository.save(shippingAddress);
            return List.of(s.getFullName(), s.getEmail(), String.format("%s, %s, %s", s.getStreet(), s.getCity(), s.getState()));

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with id " + userId + " was not found");
        }
    }

    public CheckoutResponse createOrderFromCartItem(long userId, long cartId) {

        User user = getUserCart(userId, cartId);

        List<ProductSummary> list = getAllCartItems(userId, cartId);

        ShippingAddressDTO userShippingAddress = getDefaultShippingAddress(user);

        List<CartItem> listOfCartItems = cartItemRepository.findAllByCart_Id(cartId);

        if (listOfCartItems.size() < 1) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        var amount = listOfCartItems.stream()
                .map(cartItem -> cartItem.getMenuId().getPrice() * cartItem.getQuantity())
                .mapToDouble(x -> (double) x).reduce(0L, Double::sum);

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

        return  new CheckoutResponse(list, userShippingAddress);
    }


    private User getUserCart(long userId, long cartId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        User user = optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                                    "User with Id " + userId + " does not exist"));

        Optional<Cart> optionalUserCart = cartRepository.findByIdAndUser_Id(cartId, userId);

        Cart cart = optionalUserCart.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "cart with id: " + cartId
        + " cannot be found for userId: " + userId));
        return user;
    }


    public List<ProductSummary> getAllCartItems(long userId, long cartId) {

        User user = getUserCart(userId, cartId);

        List<CartItem> listOfCartItems = cartItemRepository.findAllByCart_Id(cartId);

        return listOfCartItems.stream().map(cartItem -> {
            var optionalMenuItem = menuItemRepository.findById(cartItem.getMenuId().getId());
            ProductSummary orderSummaryDTO = new ProductSummary();

            if (optionalMenuItem.isPresent()) {
                MenuItem menuItem = optionalMenuItem.get();
                orderSummaryDTO.setProductName(menuItem.getName());
                orderSummaryDTO.setProductPrice(menuItem.getPrice());
                orderSummaryDTO.setProductQuantity(cartItem.getQuantity());
                orderSummaryDTO.setProductImage(menuItem.getImage());
                orderSummaryDTO.setProductOwner(user.getFirstName() + " " + user.getLastName());
                return orderSummaryDTO;
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "cartItem not found for the cartId of this user");
            }
        }).collect(Collectors.toList());
    }


    public ShippingAddressDTO getDefaultShippingAddress(User user) {
        Optional<ShippingAddress> defaultAddress = shippingAddressRepository.findByUserAndDefaultAddress(user, true);

        return OrderServiceImplementation.shippinAddresResponse(defaultAddress);
    }

    public List<ShippingAddressDTO> getAllAddress(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        User user =  optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT,
                                  "User with userid " + userId + " does not exist"));

        List<ShippingAddress> usersAddressList = shippingAddressRepository.findAllByUser_Id(userId);

        return usersAddressList.stream().map(shippingAddress -> {
            ShippingAddressDTO shippingAddressDTO = new ShippingAddressDTO();
            OrderServiceImplementation.mapShippingAddress(shippingAddressDTO, shippingAddress);
            return shippingAddressDTO;
        }).collect(Collectors.toList());
    }
}

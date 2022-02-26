package com.chompfooddeliveryapp.model.carts;

import com.chompfooddeliveryapp.dto.CartDTO;
import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.enums.MenuCategory;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.users.Role;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.CartResponse;
import com.chompfooddeliveryapp.payload.ViewCartResponse;
import com.chompfooddeliveryapp.repository.CartItemRepository;
import com.chompfooddeliveryapp.repository.CartRepository;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.service.serviceImpl.CartServiceImpl;
import com.chompfooddeliveryapp.service.serviceInterfaces.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CartServiceImplTest.class})
class CartServiceImplTest {

    @Mock
    CartRepository cartRepository;

    @Mock
    MenuItemRepository menuItemRepository;

    @Mock
    CartItemRepository cartItemRepository;
    @Mock
    CartService cartServices;

    @InjectMocks
    CartServiceImpl cartService;

    Cart cart;
    CartItem cartItem;
    CartDTO cartDTO;
    CartResponse cartResponse;
    ViewCartResponse viewCartResponse;
    User user;
    MenuItem product;
    Role role;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setUser(user);
        cart.setId(1L);

        cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setId(1L);
        cartItem.setQuantity(5);
        cartItem.setMenuId(product);

        role = new Role();
        role.setId(1L);
        role.setName(UserRole.USER);




        user = new User();
        user.setEmail("emeka@gmail.com");
        user.setId(2L);
        user.setFirstName("Emeka");
        user.setLastName("Augustine");
        user.setEnabled(true);
        user.setRole(role);

        cartDTO = new CartDTO();
        cartDTO.setQty(5);

        cartResponse = new CartResponse();
        cartResponse.setTotalItem(cartDTO.getQty()+cartResponse.getTotalItem());


        product = new MenuItem();
        product.setId(2L);
        product.setName("malt");
        product.setCategory(MenuCategory.DRINKS);
        product.setPrice(300.0);
        product.setDescription("Nice drink");
        product.setImage("malt.jpeg");



        viewCartResponse = new ViewCartResponse();
        viewCartResponse.setCartItemId(cartItem.getId());
        viewCartResponse.setProductId(product.getId());
        viewCartResponse.setProductImage(product.getImage());
        viewCartResponse.setProductPrice(product.getPrice());
    }

    @Test
    void createCartForUser() {
        when(cartRepository.save(cart)).thenReturn(cart);
       doNothing().when(cartServices).createCartForUser(user);
       cart.setUser(user);
       assertNotNull(cart.getUser());

    }

    @Test
    void addToCart() {

      when(menuItemRepository.findMenuItemById(product.getId())).thenReturn(Optional.of(product));
      when(cartRepository.getByUser_Id(user.getId())).thenReturn(Optional.of(cart));
      when(cartItemRepository.findAllByCart(cart)).thenReturn(List.of(cartItem));
      when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
      cart.setUser(user);
        ResponseEntity<CartResponse> result =  cartService.addToCart(cartDTO,cart.getUser().getId(),product.getId());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertNotNull(cartResponse);
        assertEquals(cartResponse.getTotalItem(),cartDTO.getQty());
      verify(menuItemRepository, times(1)).findMenuItemById(product.getId());
      verify(cartRepository, times(1)).getByUser_Id(user.getId());
      verify(cartItemRepository, times(1)).findAllByCart(cart);
    }


    @Test
    void deleteCartItem() {
        when(menuItemRepository.findMenuItemById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.getByUser_Id(user.getId())).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCart(cart)).thenReturn(List.of(cartItem));
        when(cartItemRepository.findAllByCart_Id(cart.getId())).thenReturn(List.of(cartItem));

        doThrow(BadRequestException.class).when(cartServices).deleteCartItem(user.getId(),product.getId());
    }

    @Test
    void findAllProductsByUser() {
        when(cartRepository.getByUser_Id(user.getId())).thenReturn(Optional.of(cart));
        when(cartRepository.existsById(cart.getId())).thenReturn(true);
        doThrow(BadRequestException.class).when(cartServices).findAllProductsByUser(user.getId());
        cart.setId(1L);
        cartService.getAllProductsByUser(user.getId());
        assertNotNull(cartService.getAllProductsByUser(user.getId()));

    }
}
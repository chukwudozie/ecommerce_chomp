package com.chompfooddeliveryapp.model.carts;

import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public void createCartForUser(User user) {
        if (user.getRole().getName().equals(UserRole.USER)) {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
    }


    public ResponseEntity<?> addToCart(Long userId, Long menuId, int qty){
        //todo get the user cart with the user id
        //todo check if the product is in existence
        //todo check if the qty the user wants to add to cart is available(Optional)
        //todo check if the chosen product has been added before
        //todo if true, get the previous qty and add the new qty without replacing the product in the cart
        //todo if false, add the product to the cartitem with the qty and all
        //todo user the cartitem repository to save the cart item
        return new ResponseEntity<>("Cart Item added for user", HttpStatus.OK);
    }
}

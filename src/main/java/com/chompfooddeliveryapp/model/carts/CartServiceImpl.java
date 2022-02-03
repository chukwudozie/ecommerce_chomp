package com.chompfooddeliveryapp.model.carts;

import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final MenuItemRepository menuItemRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, MenuItemRepository menuItemRepository,
                           CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.menuItemRepository = menuItemRepository;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    public void createCartForUser(User user) {
        if (user.getRole().getName().equals(UserRole.USER)) {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
    }


    @Override
    public ResponseEntity<?> addToCart(CartDTO cartDTO,Long userId, Long menuId){

        MenuItem product = menuItemRepository.findMenuItemById(menuId)
                .orElseThrow((() -> new BadRequestException("Product not available")));
        Cart cart = cartRepository.getByUser_Id(userId);
//        if(cartRepository.getByUser_Id(userId) != null && menuItemRepository.existsById(menuId)){
            if(cart != null){
            if(cartItemRepository.findByMenuId(product).isPresent()){
                CartItem cartItem = cartItemRepository.findByMenuId(product).get();
                cartItem.setQuantity(cartDTO.getQty() + cartItem.getQuantity());
                cartItem.setCartId(cart.getId());
                cartItem.setMenuId(product);
                cartItemRepository.save(cartItem);
                cartRepository.save(cart);
            }else {
                CartItem cartItem = new CartItem();
                cartItem.setCartId(cart.getId());
                cartItem.setQuantity(cartDTO.getQty());
                cartItem.setMenuId(product);
                cartItemRepository.save(cartItem);
                cartRepository.save(cart);

            }

        } else {
                throw new BadRequestException("No cart for this user");
            }
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

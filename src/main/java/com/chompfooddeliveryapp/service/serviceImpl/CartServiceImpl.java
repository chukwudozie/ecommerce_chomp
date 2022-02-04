package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.carts.Cart;
import com.chompfooddeliveryapp.model.carts.CartDTO;
import com.chompfooddeliveryapp.model.carts.CartItem;
import com.chompfooddeliveryapp.model.carts.ViewCartResponse;
import com.chompfooddeliveryapp.repository.CartItemRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.CartService;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.CartRepository;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

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
    public ResponseEntity<CartResponse> addToCart(CartDTO cartDTO, Long userId, Long menuId){
        MenuItem product = menuItemRepository.findMenuItemById(menuId)
                .orElseThrow((() -> new BadRequestException("Product not available")));
        Cart cart = cartRepository.getByUser_Id(userId)
                .orElseThrow(() -> new BadRequestException("Only users can add to cart"));
        List<CartItem> userCartItems = cartItemRepository.findAllByCartId(cart);

        if(userCartItems.isEmpty()){
            addNewCartItem(cart,product, cartDTO.getQty());
            int total = getTotalProduct(cart);
            return  ResponseEntity.ok(new CartResponse(total,product.getName()+" added!"));
        }

        Optional<CartItem> cartItem = cartItemRepository.findByMenuIdAndCartId(product, cart);
        if(cartItem.isPresent()){
//             increase qty
            cartItem.get().setQuantity(cartDTO.getQty() + cartItem.get().getQuantity());
            cartItemRepository.save(cartItem.get());
            int total = getTotalProduct(cart);
            return  ResponseEntity.ok(new CartResponse(total,product.getName()+" increased"));
        }

        //saving a new cartItem
        addNewCartItem(cart,product,cartDTO.getQty());
        int total = getTotalProduct(cart);
        return  ResponseEntity.ok(new CartResponse(total,product.getName()+" added!"));

    }

    @Override
    public ResponseEntity<CartResponse> reduceCartItemQty(Long userId, Long menuId){
        MenuItem product = menuItemRepository.findMenuItemById(menuId)
                .orElseThrow((() -> new BadRequestException("Product not available, "+menuId+" invalid")));
        Cart cart = cartRepository.getByUser_Id(userId)
                .orElseThrow(() -> new BadRequestException("Only users can add to cart"));
        List<CartItem> userCartItems = cartItemRepository.findAllByCartId(cart);
        if(userCartItems.isEmpty()){
            int total = getTotalProduct(cart);
            return  ResponseEntity.ok(new CartResponse(total,"No item In cart"));
        }

        Optional<CartItem> cartItem = cartItemRepository.findByMenuIdAndCartId(product, cart);
        if(cartItem.isPresent()){
            if(cartItem.get().getQuantity() == 1){
                cartItemRepository.delete(cartItem.get());
                int total = getTotalProduct(cart);
                return  ResponseEntity.ok(new CartResponse(total,product.getName()+" deleted!"));
            }
            cartItem.get().setQuantity(cartItem.get().getQuantity() - 1);
            cartItemRepository.save(cartItem.get());
        } else{
            throw new BadRequestException("Product not in cart");
        }
        int total = getTotalProduct(cart);
        return  ResponseEntity.ok(new CartResponse(total,product.getName()+" reduced to!"
                +cartItem.get().getQuantity()));
    }

    @Override
    public ResponseEntity<CartResponse> deleteCartItem(Long userId, Long menuId){
        MenuItem product = menuItemRepository.findMenuItemById(menuId)
                .orElseThrow((() -> new BadRequestException("Product not available, "+menuId+" invalid")));
        Cart cart = cartRepository.getByUser_Id(userId)
                .orElseThrow(() -> new BadRequestException("Only users can add to cart"));
        List<CartItem> userCartItems = cartItemRepository.findAllByCartId(cart);
        List<CartItem> menuCartItems = cartItemRepository.findAllByMenuId_Id(menuId);

        if(userCartItems.isEmpty() || menuCartItems.isEmpty()){
          throw  new BadRequestException(product.getName()+" not in the Users cart");
        }
        Optional<CartItem> cartItem = cartItemRepository.findByMenuIdAndCartId(product, cart);
        cartItemRepository.delete(cartItem.get());
        int total = getTotalProduct(cart);
        return  ResponseEntity.ok(new CartResponse(total,cartItem.get().getMenuId().getName()+" deleted"));

    }

    private void addNewCartItem( Cart cart, MenuItem product, Integer quantity){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setMenuId(product);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public ResponseEntity<List<ViewCartResponse>> findAllProductsByUser(Long cartId){

        List<ViewCartResponse> usersCartItem= new ArrayList<>();
        List<CartItem> cartItems = cartItemRepository.findAllByCartId_Id(cartId);
        if(cartRepository.existsById(cartId)) {
            for (CartItem item : cartItems) {
                ViewCartResponse cartResponse = new ViewCartResponse();
                cartResponse.setCartItemId(item.getId());
                cartResponse.setProductId(item.getMenuId().getId());
                cartResponse.setProductImage(item.getMenuId().getImage());
                cartResponse.setProductName(item.getMenuId().getName());
                cartResponse.setUserLastName(item.getCart().getUser().getLastName());
                cartResponse.setProductPrice(item.getMenuId().getPrice());
                cartResponse.setProductQuantity(item.getQuantity());
                usersCartItem.add(cartResponse);

            }
            return ResponseEntity.ok(usersCartItem);
        }
        throw new BadRequestException("Cart with id "+cartId+" doesnt't exist");
    }

    private int getTotalProduct(Cart cart){
        int totalQuantity = 0;
       List<CartItem> cartItems =  cartItemRepository.findAllByCartId_Id(cart.getId());
       for(CartItem cartItem: cartItems){
           totalQuantity += cartItem.getQuantity();
       }
        return totalQuantity;
    }

}

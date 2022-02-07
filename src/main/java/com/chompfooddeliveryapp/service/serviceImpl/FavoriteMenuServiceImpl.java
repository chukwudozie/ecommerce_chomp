package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.exception.FavoriteNotFoundException;
import com.chompfooddeliveryapp.exception.MenuNotFoundException;
import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.FavoriteMealRepository;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.FavoriteMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteMenuServiceImpl implements FavoriteMealService {

    private final FavoriteMealRepository favoriteMealRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public FavoriteMenuServiceImpl(FavoriteMealRepository favoriteMealRepository, UserRepository userRepository, MenuItemRepository menuItemRepository) {
        this.favoriteMealRepository = favoriteMealRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public FavoriteMeal createFavoriteMeal(Long userId, Long menuId) {
        Optional<User> user = userRepository.findUserById(userId);
        Optional<MenuItem> menuItem = menuItemRepository.findMenuItemById(menuId);
        if (user.isEmpty() || menuItem.isEmpty()) {
            throw new MenuNotFoundException("User or MenuItem not found");
        }

        Optional<FavoriteMeal> favoriteMeal = favoriteMealRepository.findFavoriteMealByUseridAndMenuid(user.get(), menuItem.get());
        if (favoriteMeal.isPresent()) {
            throw new MenuNotFoundException("Item is already a favorite");
        };
        FavoriteMeal newfavoriteMeal = new FavoriteMeal(user.get(), menuItem.get());
        return favoriteMealRepository.save(newfavoriteMeal);
    }

    @Override
    public String removeFromFavoriteMeal(Long userId, Long menuId) {
        Optional<User> user = userRepository.findUserById(userId);
        Optional<MenuItem> menuItem = menuItemRepository.findMenuItemById(menuId);
        if (user.isEmpty() || menuItem.isEmpty()) throw new MenuNotFoundException("User or MenuItem not found");
        FavoriteMeal favoriteMeal = favoriteMealRepository.findFavoriteMealByUseridAndMenuid(user.get(), menuItem.get())
                .orElseThrow(()->new FavoriteNotFoundException("favorite meal with id: " + menuId + " does not exist"));
        favoriteMealRepository.delete(favoriteMeal);
        return "favorite meal with " + menuId + " has been removed from favorite.";
    }

    public List<FavoriteMeal> getAllFavoriteMealsByAUser(Long userId) {
        Optional<User> user = userRepository.findUserById(userId);
        if (user.isEmpty()) throw new MenuNotFoundException("User or MenuItem not found");
        return favoriteMealRepository.findByUserid(user.get());
    }

    public FavoriteMeal getAParticularFavoriteMeal(Long userId, Long menuId){
        Optional<User> user = userRepository.findUserById(userId);
        Optional<MenuItem> menuItem = menuItemRepository.findMenuItemById(menuId);
        if (user.isEmpty() || menuItem.isEmpty()) throw new MenuNotFoundException("User or MenuItem not found");
       return favoriteMealRepository.findFavoriteMealByUseridAndMenuid(user.get(), menuItem.get())
                .orElseThrow( ()-> new FavoriteNotFoundException("Favorite meal does not exist"));
    }

}

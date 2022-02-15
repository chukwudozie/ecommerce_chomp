package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.enums.OrderStatus;
import com.chompfooddeliveryapp.model.enums.UserGender;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.AdminInfoResponse;
import com.chompfooddeliveryapp.payload.AppStatisticsResponse;
import com.chompfooddeliveryapp.repository.FavoriteMealRepository;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.repository.OrderRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.AdminService;
import com.chompfooddeliveryapp.exception.UserNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final FavoriteMealRepository favoriteMealRepository;
    private final MenuItemRepository menuItemRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            OrderRepository orderRepository,
                            FavoriteMealRepository favoriteMealRepository,
                            MenuItemRepository menuItemRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.favoriteMealRepository = favoriteMealRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public ResponseEntity<AdminInfoResponse> getAdminInfo(String email) {
        AdminInfoResponse res = new AdminInfoResponse();
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Does Not Exist"));

            res.setFirstName(user.getFirstName());
            res.setLastName(user.getLastName());
            res.setEmail(user.getEmail());
            res.setGender(user.getUserGender().name());
            res.setDateOfBirth(user.getDob());
            return ResponseEntity.ok(res);
    }

    private Long allUsers(){
        return userRepository.count();
    }

    @Override
    public Long allUsersByGender(String gender){
        return userRepository.countUsersByUserGenderIs(UserGender.valueOf(gender.toUpperCase()).name());
    }

    private Long allSubscribedUsers(){
        return userRepository.countUsersBySubscribedTrue();
    }

    private Long totalOrdersSum(){
        return orderRepository.count();
    }

    private Double totalOrdersAmount(){
        return orderRepository.sumTotalOrderAmount();
    }

    private Long allTodayOrders(){
        return orderRepository.countOrdersByOrderDateEquals();
    }

    @Override
    public Long ordersByStatus(String status){
        return orderRepository.countOrderByOrderStatusEquals(OrderStatus.valueOf(status).name());
    }

    private List<String> topFavoriteMeals(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        List<Long> mealsId = favoriteMealRepository.findTopByMenuidOrderByMenuidDesc(paging);
        return mealsId.stream().map(id->{
            return menuItemRepository.findNameById(id)
                    .orElseThrow(()->new BadRequestException("Not Found"));
        }).collect(Collectors.toList());
    }

    @Override
    public AppStatisticsResponse getStatistics(Integer pageNo, Integer pageSize) {
        HashMap<String, Long> map = new HashMap();
        map.put("Total users", allUsers());
        map.put("Subscribed users", allSubscribedUsers());
        map.put("All orders received", totalOrdersSum());
        map.put("Total amount from orders", totalOrdersAmount().longValue());
        map.put("All orders received today", allTodayOrders());
        List<String> list = topFavoriteMeals(pageNo, pageSize);
        return new AppStatisticsResponse(map, list);
    }
}

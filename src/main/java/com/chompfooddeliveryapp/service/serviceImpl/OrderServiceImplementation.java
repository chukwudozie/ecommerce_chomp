package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.ViewOrderDTO;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.orders.OrderDetail;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.repository.OrderDetailsRepository;
import com.chompfooddeliveryapp.repository.OrderRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public OrderServiceImplementation(UserRepository userRepository, OrderRepository orderRepository,
                                      OrderDetailsRepository orderDetailsRepository, MenuItemRepository menuItemRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<ViewOrderDTO > getOrderDetails(Long userId, Long orderId) {
        Optional<Order> order = orderRepository.findOrderByIdAndAndUserId(userId, orderId);
        if (order.isPresent()) {
            var orderDetail = orderDetailsRepository
                    .findAllByOrderId(order.get().getId());
            var listOfMenuIds = orderDetail.stream()
                    .map(OrderDetail::getMenuId)
                    .collect(Collectors.toList());
            var listOfMenuItems = menuItemRepository.findAllById(listOfMenuIds);

            var listViewOrderDTO = listOfMenuItems.stream()
                    .map(menuItem -> {
                        ViewOrderDTO viewOrderDetails = new ViewOrderDTO();
                        viewOrderDetails.setImage(menuItem.getImage());
                        viewOrderDetails.setName(menuItem.getName());
                        viewOrderDetails.setDescription(menuItem.getDescription());
                        viewOrderDetails.setPrice(menuItem.getPrice());
                        viewOrderDetails.setOrderDate(order.get().getOrder_date());
                        viewOrderDetails.setDeliveredDate(order.get().getDelivered_date());
                        viewOrderDetails.setStatus(order.get().getStatus());
                        viewOrderDetails.setQuantity(orderDetail.stream().mapToLong(OrderDetail::getQuantity).count());
                        return viewOrderDetails;
                    })
                    .collect(Collectors.toList());


            return listViewOrderDTO;


        } else {
            return null;   //"Order by: " + userId + "with orderID: " + orderId + "not found";
        }
    }
}

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

import java.util.Optional;

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
    public ViewOrderDTO getOrderDetails(Long userId, Long orderId) {
        Optional<Order> order = orderRepository.findOrderByIdAndAndUserId(userId, orderId);
        if (order.isPresent()) {
            Optional<OrderDetail> orderDetail = orderDetailsRepository
                    .findOrderDetailById(order.get().getId());
            Optional<MenuItem> menuItem = menuItemRepository
                    .findById(orderDetail.get().getMenuId());

            ViewOrderDTO viewOrderDetails = new ViewOrderDTO();
            viewOrderDetails.setImage(menuItem.get().getImage());
            viewOrderDetails.setName(menuItem.get().getName());
            viewOrderDetails.setDescription(menuItem.get().getDescription());
            viewOrderDetails.setPrice(menuItem.get().getPrice());
            viewOrderDetails.setQuantity(orderDetail.get().getQuantity());
            viewOrderDetails.setOrderDate(order.get().getOrder_date());
            viewOrderDetails.setDeliveredDate(order.get().getDelivered_date());
            viewOrderDetails.setStatus(order.get().getStatus());

            return viewOrderDetails;


        } else {
            return null;   //"Order by: " + userId + "with orderID: " + orderId + "not found";
        }
    }
}

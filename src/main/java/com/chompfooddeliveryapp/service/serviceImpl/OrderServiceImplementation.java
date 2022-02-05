package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.ViewOrderDTO;
import com.chompfooddeliveryapp.exception.BadRequestException;
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
import java.util.Objects;
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
    public List<ViewOrderDTO> getOrderDetails(Long userId, Long orderId) {
        var userOp = userRepository.findUserById(userId);

        var user = userOp.orElseThrow(() -> new BadRequestException("No Such User"));
        System.out.println(user.toString());

        List<Order> orderList = orderRepository.findByUserId(userId);
        var order1 = orderRepository.getOrderByIdIsAndUserIdIs(orderId, userId);
        var ord = orderList.stream().filter(o -> Objects.equals(o.getId(), orderId)).findFirst();

        var orders = order1.orElseThrow(() -> new BadRequestException("No order with Order id" + orderId + " found"));

        var orderDetail = orderDetailsRepository
                .findAllByOrderId(orders.getId());
        var listOfMenuIds = orderDetail.stream()
                .map(x -> x.getMenu().getId())
                .collect(Collectors.toList());
        var listOfMenuItems = menuItemRepository.findAllById(listOfMenuIds);

        var listViewOrderDTO = listOfMenuItems.stream()
                .map(menuItem -> {
                    ViewOrderDTO viewOrderDetails = new ViewOrderDTO();
                    viewOrderDetails.setImage(menuItem.getImage());
                    viewOrderDetails.setName(menuItem.getName());
                    viewOrderDetails.setDescription(menuItem.getDescription());
                    viewOrderDetails.setPrice(menuItem.getPrice());
                    viewOrderDetails.setOrderDate(orders.getOrder_date());
                    viewOrderDetails.setDeliveredDate(orders.getDelivered_date());
                    viewOrderDetails.setStatus(orders.getStatus());
                    viewOrderDetails.setQuantity(0L);
                    return viewOrderDetails;
                })
                .collect(Collectors.toList());

        return listViewOrderDTO;

//        } else {
//            return var //new     List<BadRequestException> badRequest ("Order by userId: " + userId + "with orderID: " + orderId + "not found");
//
//            //"Order by: " + userId + "with orderID: " + orderId + "not found";
//        }
    }
}
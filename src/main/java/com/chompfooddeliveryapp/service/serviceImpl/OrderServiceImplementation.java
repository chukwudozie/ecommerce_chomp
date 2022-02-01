package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.ViewOrderDTO;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.orders.OrderDetail;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.repository.OrderDetailsRepository;
import com.chompfooddeliveryapp.repository.OrderRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImplementation implements OrderService {
    private final UserRepository user;
    private final OrderRepository order;
    private final OrderDetailsRepository orderDetails;
    private final MenuItemRepository menuItem;
    @Autowired
    public OrderServiceImplementation(UserRepository user, OrderRepository order,
                                      OrderDetailsRepository orderDetails, MenuItemRepository menuItem) {
        this.user = user;
        this.order = order;
        this.orderDetails = orderDetails;
        this.menuItem = menuItem;
    }

    @Override
    public ViewOrderDTO getOrderDetails(Long OrderId) {

        return null;
    }
}
// private Long orderId;
//    private Long quantity;
//    private String status;
//    private String name;
//    private String image;
//    private Long price;
//    private Timestamp dateCreated;
//    private Timestamp order_date;
//    private Timestamp delivered_date;
//    private String transaction;
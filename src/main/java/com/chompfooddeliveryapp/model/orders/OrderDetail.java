package com.chompfooddeliveryapp.model.orders;

import com.chompfooddeliveryapp.model.meals.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Order.class)
    @JoinColumn(name = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MenuItem.class)
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuItem menu;

}
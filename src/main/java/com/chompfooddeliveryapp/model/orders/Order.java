package com.chompfooddeliveryapp.model.orders;

import com.chompfooddeliveryapp.model.enums.OrderStatus;
import com.chompfooddeliveryapp.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private Timestamp order_date;

    private Timestamp delivered_date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
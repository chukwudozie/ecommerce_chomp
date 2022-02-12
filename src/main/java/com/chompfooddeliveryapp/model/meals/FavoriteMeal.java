package com.chompfooddeliveryapp.model.meals;

import com.chompfooddeliveryapp.model.users.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Getter
@Table(name = "favorite_meals")
public class FavoriteMeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userid;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MenuItem.class)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MenuItem menuid;

    public FavoriteMeal(User userid, MenuItem menuid) {
        this.userid = userid;
        this.menuid = menuid;
    }
}



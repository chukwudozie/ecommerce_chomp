package com.chompfooddeliveryapp.model.meals;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageName;

    @Column
    private String imageURL;

    @Column(nullable = false)
    private String product;

    public Images(String imageName, String imageURL, String product) {
        this.imageName = imageName;
        this.imageURL = imageURL;
        this.product = product;
    }
}

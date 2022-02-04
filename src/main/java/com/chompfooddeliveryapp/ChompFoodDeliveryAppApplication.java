package com.chompfooddeliveryapp;

import com.chompfooddeliveryapp.dto.token.ConfirmationTokenRepository;
import com.chompfooddeliveryapp.repository.CartRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, ConfirmationTokenRepository.class, CartRepository.class})
public class ChompFoodDeliveryAppApplication {



    public static void main(String[] args) {
        SpringApplication.run(ChompFoodDeliveryAppApplication.class, args);

    }

}

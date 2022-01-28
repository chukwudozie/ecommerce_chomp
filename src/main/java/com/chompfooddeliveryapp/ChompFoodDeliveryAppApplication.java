package com.chompfooddeliveryapp;

import com.chompfooddeliveryapp.dto.token.ConfirmationTokenRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, ConfirmationTokenRepository.class})
public class ChompFoodDeliveryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChompFoodDeliveryAppApplication.class, args);
    }

}

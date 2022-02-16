package com.chompfooddeliveryapp;

import com.chompfooddeliveryapp.repository.ConfirmationTokenRepository;
import com.chompfooddeliveryapp.repository.CartRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, ConfirmationTokenRepository.class, CartRepository.class})
@RequiredArgsConstructor
public class ChompFoodDeliveryAppApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(ChompFoodDeliveryAppApplication.class, args);

    }

    private final StartUpService startUpService;

    @Override
    public void run(String... args) throws Exception {
        startUpService.initiateStartup();
        startUpService.createAdmin();
    }
}

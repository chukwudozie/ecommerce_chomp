package com.chompfooddeliveryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class ChompFoodDeliveryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChompFoodDeliveryAppApplication.class, args);
    }

//    @Bean
//    public Docket SwagggerConfiguration() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.chompfooddeliveryapp"))
//                .build()
//                .apiInfo(apiDetails());
//    }
//
//    private ApiInfo apiDetails(){
//        return new ApiInfo(
//                "Chomp Food Delivery App",
//                "Food delivery app, Group A",
//                "1.0",
//                "Free to use",
//                new springfox.documentation.service.Contact("Group A", " ", "chompfood@gmail.com"),
//                "API Licence",
//                "http://chompfood.com",
//                Collections.emptyList()
//        );
//    }

}

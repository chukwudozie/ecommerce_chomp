package com.chompfooddeliveryapp.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket swagggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chompfooddeliveryapp"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "Chomp Food Delivery App",
                "Food delivery app, Group A",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Group A", " ", "chompfood@gmail.com"),
                "API Licence",
                "http://chompfood.com",
                Collections.emptyList()
        );
    }
}

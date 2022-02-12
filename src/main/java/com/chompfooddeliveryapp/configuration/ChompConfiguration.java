package com.chompfooddeliveryapp.configuration;

import com.chompfooddeliveryapp.ChompFoodDeliveryAppApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(basePackageClasses = ChompFoodDeliveryAppApplication.class)
@PropertySource("classpath:/application.properties")
public class ChompConfiguration {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }


}

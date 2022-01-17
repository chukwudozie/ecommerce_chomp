package com.chompfooddeliveryapp.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Bean
    public Docket swagggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chompfooddeliveryapp"))
                .build()
                .apiInfo(apiDetails());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui/")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//
//    private SecurityReference bearerAuthReference() {
//        return new SecurityReference("Bearer", new AuthorizationScope[0]);
//    }
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
//    }

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

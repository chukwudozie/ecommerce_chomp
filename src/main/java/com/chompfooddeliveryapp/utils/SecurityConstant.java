package com.chompfooddeliveryapp.utils;

public class SecurityConstant {
    public static final String [] PUBLIC_URI = {
            //swagger
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/login/",
            "/swagger-ui/api/login/",
            "/swagger-ui/#/**",
            //swagger ends
           "/api/auth/**",
            "/auth/**",
            "/**",

    };
}

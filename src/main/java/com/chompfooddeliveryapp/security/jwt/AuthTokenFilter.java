package com.chompfooddeliveryapp.security.jwt;

import com.chompfooddeliveryapp.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils utils;

    private static  final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

    public AuthTokenFilter(UserDetailsServiceImpl userDetailsService, JwtUtils utils) {
        this.userDetailsService = userDetailsService;
        this.utils = utils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
         HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}

package com.chompfooddeliveryapp.security.service;

import antlr.collections.impl.LList;
import com.chompfooddeliveryapp.model.enums.UserGender;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String firstName;
    private String lastName;
    private UserGender gender;

    @JsonIgnore
    private String password;
    private Boolean subscribed;

    private String email;

    private UserRole authorities;

    public UserDetailsImpl(Long id, String firstName, String lastName,
                           UserGender  gender, String password, Boolean subscribed,
                           String email,UserRole authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.password = password;
        this.subscribed = subscribed;
        this.email = email;
        this.authorities = authorities;
    }


    public static UserDetails build(User user) {
        UserRole authorities = user.getRole();
        return new UserDetailsImpl(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                user.getPassword(),
                user.getSubscribed(),
                user.getEmail(),
                authorities
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

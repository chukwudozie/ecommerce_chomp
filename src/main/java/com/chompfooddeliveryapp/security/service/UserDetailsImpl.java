package com.chompfooddeliveryapp.security.service;


import com.chompfooddeliveryapp.model.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;


@EqualsAndHashCode
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String firstName;
    private String lastName;

    @JsonIgnore
    private String password;


    private String email;

    private Boolean locked = false;

    private Boolean enabled = false;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String firstName, String lastName, String password,
                           String email, Boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
    }




    public static UserDetails build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.getEnabled()
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return new ArrayList<>();
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

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
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

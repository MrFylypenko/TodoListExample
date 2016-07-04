package com.springtest.settings;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by vano on 04.07.16.
 */
public class UserAuthentication implements Authentication {

    private UserDetails userDetails;
    private Object details;
    boolean authenticated = true;


    public UserAuthentication(UserDetails userDetails, Object details) {
        this.userDetails = userDetails;
        this.details = details;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            this.authenticated =  isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }


}

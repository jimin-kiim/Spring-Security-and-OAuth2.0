package com.springsecurity.demo.config.auth;

// when the url "/login" is requested, security catches it and process the login
// when it's completed, it creates its own session and stores the session information in the key value called Security ContextHolder
// the object that can be stored in the session is limited (Authentication typed Object)
// the User information is in the Authentication
// the User Object type -> UserDetails type

import com.springsecurity.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Security Session > Authentication type Object > UserDetails type Object(PrincipalDetails) > User Object
public class PrincipalDetails implements UserDetails {

    private User user; // composition

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // returns the authority of the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        // if we want to transit an account to dormant status after one year of the last login,
        // lastLogin field should be added to User Model
        return true;
    }
}

package com.springsecurity.demo.config.auth;

// when the url "/login" is requested, security catches it and process the login
// when it's completed, it creates its own session and stores the session information in the key value called Security ContextHolder
// the object that can be stored in the session is limited (Authentication typed Object)
// the User information is in the Authentication
// the User Object type -> UserDetails type

import com.springsecurity.demo.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Security Session > Authentication type Object > UserDetails type Object(PrincipalDetails) > User Object
/*
Session
   - Security Session
     - Authentication
        - UserDetails (standard) // UserDetails + User => PrincipalDetails
        - OAuth2User (OAuth login)

    when using the user data or loading the user to the Authentication, we should get the object as an argument
    but it can be either UserDetails or OAuth2User
    so the class we would use should implement both UserDetails and OAuth2User
    then the problem is solved
-> make PrincipalDetails implements UserDetails and OAuth2User
-> PrincipalDetails = UserDetails + OAuth2User + User + attributes
 */

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; // composition
    private Map<String, Object> attributes;

    // Standard
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}

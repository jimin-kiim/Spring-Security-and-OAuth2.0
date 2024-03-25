package com.springsecurity.demo.config.auth;

import com.springsecurity.demo.model.User;
import com.springsecurity.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// since the 'loginProcessingUrl("/login") is placed in the SecurityConfig,
// when the /login request received, the function loadUserByUsername, which is injected via IoC
// and belongs to the UserDetailsService type, is automatically called
@Service // PrincipalDetailsService is injected vis IoC
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Security Session > Authentication > UserDetails(PrincipalDetails) > User
    @Override
    // in the loginForm, the name of the input field should be "username"
    // if it isn't "username", in securityConfig, .usernameParameter("{modified parameter name}") should be added
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}

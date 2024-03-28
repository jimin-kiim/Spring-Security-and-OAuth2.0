package com.springsecurity.demo.config.oauth;

import com.springsecurity.demo.config.auth.PrincipalDetails;
import com.springsecurity.demo.model.User;
import com.springsecurity.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    // executed after google oauth login is completed
    // handling userRequest from google oauth
    // after exiting this function, @AuthenticationPrincipal annotation can be used
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        /*
            System.out.println("userRequest: " + userRequest); // with registrationId inside, we can figure out how login processed with which host
            System.out.println("getAccessToken: " + userRequest.getAccessToken());
            System.out.println("getTokenValue: " + userRequest.getAccessToken().getTokenValue());
            System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
            System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());
         */

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes: " + oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("password");
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}

package com.springsecurity.demo.config.oauth;

import com.springsecurity.demo.config.auth.PrincipalDetails;
import com.springsecurity.demo.config.oauth.provider.FacebookUserInfo;
import com.springsecurity.demo.config.oauth.provider.GoogleUserInfo;
import com.springsecurity.demo.config.oauth.provider.NaverUserInfo;
import com.springsecurity.demo.config.oauth.provider.OAuth2UserInfo;
import com.springsecurity.demo.model.User;
import com.springsecurity.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

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
//        System.out.println("getAttributes: " + oAuth2User.getAttributes());
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("We only support OAuth login for Google, Facebook and Naver");
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("password");
        String email = oAuth2UserInfo.getEmail();
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

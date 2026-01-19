package org.example.oauthsession.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.oauthsession.dto.CustomOAuth2User;
import org.example.oauthsession.dto.GoogleResponse;
import org.example.oauthsession.dto.NaverResponse;
import org.example.oauthsession.dto.OAuth2Response;
import org.example.oauthsession.entity.UserEntity;
import org.example.oauthsession.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User info : {}", oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String username = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        String email = oAuth2Response.getEmail();

        UserEntity savedUser = userRepository.findByUsername(username)
                .orElseGet(() -> {
                    UserEntity user = new UserEntity(username, email, "ROLE_USER");

                    return userRepository.save(user);
                });

        return new CustomOAuth2User(oAuth2Response, savedUser.getRole());
    }
}

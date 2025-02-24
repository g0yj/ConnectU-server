package com.lms.api.common.config;

import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        log.debug("✅ OAuth2 로그인 요청: provider={}, attributes={}", registrationId, attributes);

        // OAuth2 사용자 정보 처리
        return processOAuth2User(registrationId, oAuth2User);
    }

    private OAuth2User processOAuth2User(String registrationId, OAuth2User user) {
        Map<String, Object> attributes = user.getAttributes();
        String id = null;
        String email = null;
        String name = null;

        switch (registrationId) {
            case "kakao":
                id = String.valueOf(attributes.get("id"));
                log.debug("🔹 카카오 ID: {}", id);

                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                if (kakaoAccount != null) {
                    email = (String) kakaoAccount.get("email");

                    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                    if (profile != null) {
                        name = (String) profile.get("nickname");
                    }
                }
                break;

            case "naver":
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                if (response != null) {
                    id = String.valueOf(response.get("id"));
                    email = (String) response.get("email");
                    name = (String) response.get("name");

                    attributes = response; // 네이버는 response 안에 정보가 있음
                }
                log.debug("🔹 네이버 ID: {}", id);
                break;

            default:
                throw new OAuth2AuthenticationException("❌ 지원되지 않는 OAuth2 제공자: " + registrationId);
        }

        // 유효성 검사: email이 없으면 예외 발생
        if (email == null) {
            throw new OAuth2AuthenticationException("❌ OAuth2 로그인 실패: 이메일 정보가 없습니다.");
        }

        log.info("✅ [OAuth2 User] provider={}, id={}, email={}, name={}", registrationId, id, email, name);

        return getOrCreateOAuth2User(email, name, attributes);
    }

    private OAuth2User getOrCreateOAuth2User(String email, String name, Map<String, Object> attributes) {
        UserEntity userEntity = userRepository.findByLoginId(email)
                .orElseGet(() -> saveNewOAuth2User(email, name));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userEntity.getType().toRole()));
        return new DefaultOAuth2User(authorities, attributes, "email"); // 변경: 기본 키를 email로 설정
    }

    private UserEntity saveNewOAuth2User(String email, String name) {
        UserEntity newUser = UserEntity.builder()
                .loginId(email)
                .email(email)
                .name(name)
                .type(UserType.S) // 기본 USER 타입 부여
                .active(true)
                .build();
        return userRepository.save(newUser);
    }
}

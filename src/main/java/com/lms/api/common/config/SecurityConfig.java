package com.lms.api.common.config;

import com.lms.api.admin.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.RequestEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    /**
     * Spring Security 필터 체인을 설정하는 Bean
     * - 인증 및 인가(권한) 정책을 정의
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) //  CSRF 보호 비활성화 (API 호출 시 필요)
                .authorizeHttpRequests(auth -> auth
                        // 🔹 공개 접근 허용
                        .requestMatchers(
                                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/oauth2/**",  // ✅
                                "/login/oauth2/**"
                        ).permitAll()
                        // 🔹 로그인, 회원가입은 인증 없이 허용
                        .requestMatchers("/login", "/admin/v1/security/login",
                                "/admin/v1/signup", "/admin/v1/login", "/admin/v1/**",
                                "/oauth2/**"
                        ).permitAll()
                        // 🔹 관리자만 접근
                        .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                        // 🔹 회원만 접근
                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER")
                        // 🔹 나머지 모든 요청은 인증된 사용자만 접근 가능
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login") // 로그인 페이지 경로 설정
                        .successHandler(customLoginSuccessHandler) // 로그인 성공 후 핸들러 설정
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> {
                            endpoint.baseUri("/oauth2/authorization");
                        })
                        .redirectionEndpoint(redirection -> {
                            redirection.baseUri("/login/oauth2/code/*");
                        })
                        .userInfoEndpoint(userInfo -> {
                            userInfo.userService(new DefaultOAuth2UserService());
                        })
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService) // ✅ CustomOAuth2UserService 사용
                        )
                        .successHandler((request, response, authentication) -> {
                            log.info("✅ OAuth2 로그인 성공: {}", authentication.getName());
                            customLoginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                            request.getSession().invalidate(); // ✅ 기존 세션 무효화
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/v1/logout") //  로그아웃 URL을 "/logout"으로 설정
                        .logoutSuccessUrl("/login") // 로그아웃 성공 후 "/login"으로 이동
                        .invalidateHttpSession(true) //  세션 무효화
                        .deleteCookies("JSESSIONID", "SESSION") // JSESSIONID 쿠키 삭제
                )
                .headers(headers -> headers
                        .cacheControl(cache -> cache.disable())); // HTTP 응답 캐시 비활성화

        return http.build(); // `http.build()`를 반환하여 SecurityFilterChain을 생성
    }


/**
     * 비밀번호 암호화를 위한 PasswordEncoder Bean 등록
     * - BCryptPasswordEncoder: 안전한 해싱 알고리즘을 사용하여 비밀번호를 암호화
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


/**
     * AuthenticationManager Bean 등록
     * - Spring Security에서 인증(Authentication)을 처리하는 주요 컴포넌트
     * - AuthenticationConfiguration을 사용하여 기본 AuthenticationManager를 반환
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 로그인 성공 후 권한에 따라 다른 페이지로 이동하는 핸들러 설정
     */
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // ✅ 인증 정보를 포함한 요청 허용
        configuration.addAllowedOriginPattern("*"); // ✅ 모든 도메인 허용 (React 등 프론트엔드 허용)
        configuration.addAllowedMethod("*"); // ✅ 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
        configuration.addAllowedHeader("*"); // ✅ 모든 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // ✅ 모든 경로에 대해 CORS 적용
        return source;
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> customOAuth2AccessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient defaultClient = new DefaultAuthorizationCodeTokenResponseClient();
        defaultClient.setRequestEntityConverter(new OAuth2AuthorizationCodeGrantRequestEntityConverter() {
            @Override
            public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest request) {
                RequestEntity<?> entity = super.convert(request);
                return new RequestEntity<>(entity.getBody(), entity.getHeaders(), entity.getMethod(), entity.getUrl());
            }
        });
        return defaultClient;
    }


}

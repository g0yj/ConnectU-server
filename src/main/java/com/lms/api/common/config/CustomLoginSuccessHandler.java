package com.lms.api.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
@Component
@Slf4j
/**
 * 로그인 성공 후 권한별로 리다이렉트하는 핸들러 (스프링시큐리티 사용 시 )
 */
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("✅ [SUCCESS] OAuth2 로그인 성공: 사용자={} 권한={}", authentication.getName(), authentication.getAuthorities());
        // 로그인한 사용자의 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 권한에 따라 리다이렉트할 페이지 결정
        String targetUrl = "";
        for (GrantedAuthority authority : authorities){
            String role = authority.getAuthority();
            if(role.equals("ROLE_ADMIN")){
                targetUrl = "http://localhost:5173/admin/members/member";
                break;
            } else if (role.equals("ROLE_USER")) {
                targetUrl = "/user/home";
                break;
            }
        }
        // 해당 페이지로 리다이렉트
        response.sendRedirect(targetUrl);
    }
}

package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.user.CreateUserRequest;
import com.lms.api.admin.service.LoginService;
import com.lms.api.admin.service.dto.Login;
import com.lms.api.common.code.LoginType;
import com.lms.api.common.controller.dto.LoginRequest;
import com.lms.api.common.controller.dto.LoginResponse;
import com.lms.api.common.service.dto.LoginInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;
    private final LoginControllerMapper adminControllerMapper;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "회원 타입으로 권한을 주는 방식의 로그인", description = "타입이 관리자냐 회원이냐에 따라 접근 권한을 다르게 합니다. 토큰을 사용하는 로그인 방식으로 별도로 login상태 테이블을 사용하고 있습니다")
    @PostMapping("/admin/v1/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest){
        Login login = loginService.login(adminControllerMapper.toLogin(loginRequest), LoginType.ADMIN);

        log.debug("login: id={}, token={}", loginRequest.getId(), login.getToken());

        return adminControllerMapper.toLoginResponse(login);
    }
    @Operation(summary = "회원 타입으로 권한을 주는 방식의 로그아웃", description = "로그인 시 로그인 테이블을 통해 상태를 유지하고 있습니다. 저장된 토큰을 삭제 하는 방식입니다")
    @PostMapping("/admin/v1/logout")
    public void logout(LoginInfo loginInfo) {
        log.debug("logout: token={}", loginInfo.getToken());
        loginService.logoutAdmin(loginInfo.getToken());
    }

    @Operation(summary = "스프링시큐리티를 사용한 로그인", description = "ROLE로 권한을 부여하고 있어 type과 같은 필드가 필요하지 않습니다")
    @PostMapping("/security/login")
    public ResponseEntity<String> securityLogin(@RequestBody Map<String, String> loginRequest) {
        log.debug("호출됨 : loginRequest={}", loginRequest);
        // loginId와 password 추출
        String loginId = loginRequest.get("loginId");
        String password = loginRequest.get("password");

        log.debug("로그인 요청: loginId={}", loginId);

        // Spring Security 인증 수행 (여기서 UserDetailsService.loadUserByUsername 실행됨)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, password)
        );

        // SecurityContext에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 로그인 성공 후 사용자 정보 확인 (UserDetailsService 호출 확인용)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.debug("로그인 성공: username={}, authorities={}", userDetails.getUsername(), userDetails.getAuthorities());

        // 로그인 성공 시 메시지 반환
        return ResponseEntity.ok("로그인 성공");
    }

    @Operation(summary = "회원 가입 API", description = "비밀번호는 암호화 되어 저장")
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid CreateUserRequest request){
        log.debug("회원가입 요청: {}" , request);
        loginService.signUp(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary = "타임리프 사용 시 로그인 화면으로 이동", description = "기본 URL(`http://localhost:8080`)에서 로그인 페이지로 리다이렉트")
    @GetMapping("/")
    public String root(){
        return "redirect:/login";
    }

    @Operation(summary = "타임리프 사용시, 로그인 페이지 출력" , description = "login.html 뷰를 반환합니다")
    @GetMapping("/login")
    public ModelAndView loginPage(){
        return new ModelAndView("login");
    }
}







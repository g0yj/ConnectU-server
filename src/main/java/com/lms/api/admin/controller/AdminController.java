package com.lms.api.admin.controller;

import com.lms.api.admin.service.LoginService;
import com.lms.api.admin.service.dto.Login;
import com.lms.api.common.code.LoginType;
import com.lms.api.common.controller.dto.LoginRequest;
import com.lms.api.common.controller.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final LoginService loginService;
    private final AdminControllerMapper adminControllerMapper;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest){
        Login login = loginService.login(adminControllerMapper.toLogin(loginRequest), LoginType.ADMIN);

        log.debug("login: id={}, token={}", loginRequest.getId(), login.getToken());

        return adminControllerMapper.toLoginResponse(login);
    }

}

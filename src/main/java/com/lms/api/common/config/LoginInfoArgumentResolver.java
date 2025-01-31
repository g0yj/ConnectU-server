package com.lms.api.common.config;

import com.lms.api.admin.service.LoginService;
import com.lms.api.common.code.UserType;
import com.lms.api.common.exception.AppErrorCode;
import com.lms.api.common.exception.AppException;
import com.lms.api.common.service.dto.LoginInfo;
import com.lms.api.common.util.Consts;
import com.lms.api.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class LoginInfoArgumentResolver implements HandlerMethodArgumentResolver {

  private final LoginService loginService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(LoginInfo.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    // 특정 헤더가 있으면 테스트 계정 고정
    UserType userType = (UserType) request.getAttribute("userType");

    if (userType != null) {
      switch (userType)
        {
          case A:
            return LoginInfo.builder().id(Consts.TEST_ADMIN_ID).name("테스트_관리자").type(UserType.A).build();
          case T:
            return LoginInfo.builder().id(Consts.TEST_TEACHER_ID).name("테스트_멘토").type(UserType.T).build();
          case S:
            return LoginInfo.builder().id(Consts.TEST_STUDENT_ID).name("테스트_회원").type(UserType.S).build();
          default:
        }
    }

    String token = request.getHeader(AUTHORIZATION);

    if (StringUtils.hasNotText(token)) {
      throw new AppException(AppErrorCode.LOGIN_REQUIRED);
    }

    return loginService.getLoginInfo(token);
  }
}

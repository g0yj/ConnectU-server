package com.lms.api.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum UserType {
  A("관리자"),
  S("회원"),
  T("직원");

  String label;

  // Spring Security에서 사용할 GrantedAuthority 형식으로 변환
  public String toRole() {
    return switch (this) {
      case A -> "ROLE_ADMIN";
      case S -> "ROLE_USER";
      case T -> "ROLE_TEACHER";
      default -> throw new IllegalArgumentException("Unknown UserType: " + this);
    };
  }
}

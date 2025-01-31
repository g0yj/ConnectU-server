package com.lms.api.common.service.dto;

import com.lms.api.common.code.UserType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginInfo {

  String id;
  String name;
  String token;
  UserType type;
}

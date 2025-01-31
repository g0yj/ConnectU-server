package com.lms.api.common.controller.dto;

import com.lms.api.common.code.UserType;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

  UserType type;

  @NotEmpty
  String id;

  @NotEmpty
  String password;
}

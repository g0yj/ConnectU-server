package com.lms.api.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum DepartmentType {
  A("회계"),
  M("마케팅"),
  T("멘토"),
  P("인사"),
  ;

  String label;
}

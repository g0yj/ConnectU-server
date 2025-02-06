package com.lms.api.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PaymentType {
  A("계좌"),
  C("카드"),
  K("카카오페이"),
  N("네이버페이")
  ;

  String label;
}

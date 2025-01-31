package com.lms.api.common.code;

public enum YN {
  Y, N;

  public static YN of(Boolean bool) {
    return bool == null ? null : bool ? Y : N;
  }

  public static Boolean to(YN yn) {
    return yn == null ? null : yn == Y;
  }
}

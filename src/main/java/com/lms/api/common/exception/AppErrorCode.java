package com.lms.api.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum AppErrorCode implements AppError {
  // common error
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "9999", "서버에 문제가 발생했습니다."),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "9900", "잘못된 요청입니다. {}"),
  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "9901", "잘못된 파라미터입니다. ({})"),
  PARAMETER_REQUIRED(HttpStatus.BAD_REQUEST, "9901", "{} 파라미터는 필수입니다."),

  //file error
  FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "1500", "파일 크기가 %s를 초과합니다: %d"),
  FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "1501", "%s"),


  // auth error
  LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "0100", "로그인이 필요합니다."),
  PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "0101", "비밀번호가 다릅니다."),
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "0102", "사용자를 찾을 수 없습니다."),
  USER_INACTIVE(HttpStatus.BAD_REQUEST, "0104", "비활성화 된 사용자입니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "0105", "접근 권한이 없습니다."),
  ID_NOT_EXIST(HttpStatus.BAD_REQUEST, "0106", "없는 아이디입니다."),

  // loginId error
  LOGIN_SERVER_ERROR(HttpStatus.CONFLICT, "1400", "동일한 ID가 존재합니다"),


  //중복 error
  CELLPHONE_NOT_MATCH(HttpStatus.BAD_REQUEST, "1001", "중복되는 번호가 있습니다"),

  ;
  HttpStatusCode httpStatusCode;
  String code;
  String message;

  public String getCode() {
    return "api-" + code;
  }
}

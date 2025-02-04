package com.lms.api.admin.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.UserType;
import com.lms.api.common.controller.dto.PageResponseData;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUsersExcelResponse {

    String 아이디;
    String 이름;
    Gender 성별;
    String 연락처;
    String 이메일;
    String 주소1;
    String 주소2;
    Boolean 직장여부;
    boolean 활동여부;
    String 특이사항;
}

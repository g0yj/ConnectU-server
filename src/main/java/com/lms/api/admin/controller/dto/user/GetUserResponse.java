package com.lms.api.admin.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.CoursePurpose;
import com.lms.api.common.code.Gender;
import com.lms.api.common.controller.dto.PageResponseData;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserResponse {

    String id;
    String loginId;
    String name;

    String email;
    Boolean isReceiveEmail;
    Gender gender;

    String cellPhone;
    Boolean isReceiveSms;
    Boolean isOfficeWorker;
    String note;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDateTime;

    boolean active;

    String address;
    String detailedAddress;

    List<CoursePurpose> coursePurposes;
}

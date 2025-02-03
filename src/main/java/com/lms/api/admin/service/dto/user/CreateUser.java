package com.lms.api.admin.service.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lms.api.common.code.CoursePurpose;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.YN;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUser {
    String name;
    String loginId;
    String password;

    String email;
    @Builder.Default
    Boolean isReceiveEmail = false;

    Gender gender;

    String cellPhone;
    @Builder.Default
    Boolean isReceiveSms = false;

    Boolean isOfficeWorker;
    String note;
    boolean active;
    String address;
    String detailedAddress;
    UserType type;
    String coursePurpose;
    String createdBy;



}

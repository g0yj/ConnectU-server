package com.lms.api.admin.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lms.api.common.code.CoursePurpose;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.YN;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotEmpty
    String name;

    @NotEmpty
    String loginId;

    @NotEmpty
    String email;

    Boolean isReceiveEmail;

    @NotEmpty
    String password;

    @NotNull
    Gender gender;

    @NotEmpty
    String cellPhone;

    Boolean isReceiveSms;

    Boolean isOfficeWorker;
    String note;

    @JsonProperty("isActive")
    boolean active;
    String address;
    String detailedAddress;

    @NotNull
    UserType type;

    List<CoursePurpose> coursePurposes;


    public String getCoursePurpose() {
        if (coursePurposes == null) {
            return null;
        }
        return coursePurposes.stream().map(CoursePurpose::name).collect(Collectors.joining(","));
    }
}

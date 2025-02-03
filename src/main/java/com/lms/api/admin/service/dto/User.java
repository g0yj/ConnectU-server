package com.lms.api.admin.service.dto;

import com.lms.api.common.code.CoursePurpose;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.YN;
import com.lms.api.common.util.StringUtils;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    String id;
    String loginId;
    String name;
    String password;
    UserType type;
    Gender gender;
    String cellPhone;
    Boolean isReceiveSms;
    String email;
    Boolean isReceiveEmail;
    String address;
    String detailedAddress;
    Boolean isOfficeWorker;
    boolean active;
    String note;
    String coursePurpose;

    String createdBy;
    LocalDateTime createdOn;
    String modifiedBy;
    LocalDateTime modifiedOn;


    public List<CoursePurpose> getCoursePurposes() {
        if (coursePurpose == null) {
            return List.of();
        }

        return Arrays.stream(coursePurpose.split(","))
                .filter(StringUtils::hasText)
                .map(String::trim)
                .map(CoursePurpose::of)
                .collect(Collectors.toList());
    }

}

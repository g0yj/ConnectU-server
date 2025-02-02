package com.lms.api.admin.service.dto;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.YN;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
    YN isReceiveSms;
    String email;
    YN isReceiveEmail;
    String address;
    String detailedAddress;
    YN isOfficeWorker;
    boolean active;
    String note;
    String coursePurpose;

}

package com.lms.api.admin.service.dto.user;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.UserType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUser {
    String id;
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
    String modifiedBy;

}

package com.lms.api.admin.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.controller.dto.PageResponseData;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUsersResponse extends PageResponseData {

    String id;
    String name;
    String cellPhone;
    String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate expirationDate;
    Float remainingCount; // 잔여
}

package com.lms.api.admin.service.dto.user;

import com.lms.api.admin.service.dto.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserList {
    User user;

    LocalDate endDate; // 수업 종료일
    Float remainingCount; // 잔여
}

package com.lms.api.admin.service.dto.user;

import com.lms.api.admin.code.SearchCode;
import com.lms.api.common.code.UserType;
import com.lms.api.common.service.dto.Search;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUsers extends Search {

    LocalDate createDateFrom;
    LocalDate createDateTo;
    SearchCode.ExpireType expireType;
    SearchCode.RemainingType remainingType;
    UserType type;

}

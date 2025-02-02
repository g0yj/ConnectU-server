package com.lms.api.admin.controller.dto.user;

import com.lms.api.admin.code.SearchCode;
import com.lms.api.common.code.UserType;
import com.lms.api.common.controller.dto.PageRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUsersRequest extends PageRequest {
    LocalDate createDateFrom;
    LocalDate createDateTo;

    SearchCode.ExpireType expireType;
    SearchCode.RemainingType remainingType;

    UserType type;

    public ListUsersRequest(Integer page, Integer limit, Integer pageSize, String order, String direction, String search, String keyword, LocalDate createDateFrom, LocalDate createDateTo, SearchCode.ExpireType expireType, SearchCode.RemainingType remainingType, UserType type) {
        super(page, limit, pageSize, order, direction, search, keyword);
        this.createDateFrom = createDateFrom;
        this.createDateTo = createDateTo;
        this.expireType = expireType;
        this.remainingType = remainingType;
        this.type = type;
    }
}

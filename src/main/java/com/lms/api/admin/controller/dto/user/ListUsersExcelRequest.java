package com.lms.api.admin.controller.dto.user;

import com.lms.api.admin.code.SearchCode;
import com.lms.api.common.code.UserType;
import com.lms.api.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUsersExcelRequest  {
    LocalDate createDateFrom;
    LocalDate createDateTo;
    SearchCode.ExpireType expireType;
    SearchCode.RemainingType remainingType;
    String keyword;
    String search;
    UserType type;

    public ListUsersExcelRequest(LocalDate createDateFrom, LocalDate createDateTo, SearchCode.ExpireType expireType, SearchCode.RemainingType remainingType, String keyword, String search, UserType type) {
        this.createDateFrom = createDateFrom;
        this.createDateTo = createDateTo;
        this.expireType = expireType;
        this.remainingType = remainingType;
        this.keyword = keyword;
        this.search = search;
        this.type = type;
    }

    public boolean hasSearch() {
        return StringUtils.hasAllText(search, keyword);
    }

}

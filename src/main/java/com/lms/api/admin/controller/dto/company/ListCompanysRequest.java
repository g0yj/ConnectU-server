package com.lms.api.admin.controller.dto.company;

import com.lms.api.admin.code.SearchCode;
import com.lms.api.common.code.UserType;
import com.lms.api.common.controller.dto.PageRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCompanysRequest extends PageRequest {
    LocalDate startDate;
    LocalDate endDate;
    Boolean active;

    public ListCompanysRequest(Integer page, Integer limit, Integer pageSize, String order, String direction, String search, String keyword, LocalDate startDate, LocalDate endDate, Boolean active) {
        super(page, limit, pageSize, order, direction, search, keyword);
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }
}

package com.lms.api.admin.service.dto.company;

import com.lms.api.common.service.dto.Search;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCompany extends Search {

    LocalDate startDate;
    LocalDate endDate;
    Boolean active;

}

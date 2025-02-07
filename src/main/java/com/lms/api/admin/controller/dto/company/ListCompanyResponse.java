package com.lms.api.admin.controller.dto.company;

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
public class ListCompanyResponse extends PageResponseData {

    String id;
    String name;
    String title;
    String phone;
    String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate;
}

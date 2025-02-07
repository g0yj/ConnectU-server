package com.lms.api.admin.service.dto.company;

import com.lms.api.admin.service.dto.Company;
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
public class CompanyList {
    Company company;

    String title; //최근 거래 내용
    LocalDate endDate; // 계약 종료일

}

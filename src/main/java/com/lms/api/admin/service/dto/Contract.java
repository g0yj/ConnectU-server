package com.lms.api.admin.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contract {

    Long id;

    String title; // 계약내용
    String content; // 상세내용
    String name; // 담당자
    Long contractAmount; // 계약금액
    LocalDate startDate; // 계약시작일
    LocalDate endDate; // 계약 종료일

    String createdBy;
    LocalDateTime createdOn;
    String modifiedBy;
    LocalDateTime modifiedOn;


}

package com.lms.api.admin.service.dto;

import com.lms.api.common.code.CoursePurpose;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.UserType;
import com.lms.api.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.yaml.snakeyaml.constructor.Construct;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {

    Long id;
    String name; // 기업명
    String representative; // 대표자명
    String companyNumber; // 사업자등록번호
    String phone; // 연락처
    String email;
    String bank;
    String account;
    Boolean active; // 거래처 상태

    List<Contract> contructs;

    String createdBy;
    LocalDateTime createdOn;
    String modifiedBy;
    LocalDateTime modifiedOn;

}

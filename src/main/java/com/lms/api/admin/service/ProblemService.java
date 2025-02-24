package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Company;
import com.lms.api.admin.service.dto.Contract;
import com.lms.api.admin.service.dto.company.CompanyList;
import com.lms.api.admin.service.dto.company.SearchCompany;
import com.lms.api.common.entity.CompanyEntity;
import com.lms.api.common.entity.ContractEntity;
import com.lms.api.common.entity.QCompanyEntity;
import com.lms.api.common.entity.QContractEntity;
import com.lms.api.common.exception.AppErrorCode;
import com.lms.api.common.exception.AppException;
import com.lms.api.common.repository.CompanyRepository;
import com.lms.api.common.repository.ContractRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemService {

}
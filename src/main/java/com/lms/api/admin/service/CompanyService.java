package com.lms.api.admin.service;

import com.lms.api.admin.code.SearchCode;
import com.lms.api.admin.controller.dto.user.ListUsersExcelRequest;
import com.lms.api.admin.service.dto.Company;
import com.lms.api.admin.service.dto.Contract;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.company.CompanyList;
import com.lms.api.admin.service.dto.company.SearchCompany;
import com.lms.api.admin.service.dto.user.CreateUser;
import com.lms.api.admin.service.dto.user.SearchUsers;
import com.lms.api.admin.service.dto.user.UpdateUser;
import com.lms.api.admin.service.dto.user.UserList;
import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.*;
import com.lms.api.common.exception.AppErrorCode;
import com.lms.api.common.exception.AppException;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.repository.CompanyRepository;
import com.lms.api.common.repository.ContractRepository;
import com.lms.api.common.repository.CourseRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.util.AppUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
public class CompanyService {

  private final CompanyServiceMapper companyServiceMapper;
  private final ContractServiceMapper contractServiceMapper;
  private final CompanyRepository companyRepository;
  private final ContractRepository contractRepository;

  public Page<CompanyList> listCompany(SearchCompany searchCompany) {
    QCompanyEntity qCompanyEntity = QCompanyEntity.companyEntity;
    QContractEntity qContractEntity = QContractEntity.contractEntity;

    LocalDate now = LocalDate.now();

    BooleanExpression where = Expressions.TRUE;

    // 계약일
    if (searchCompany.getStartDate() != null && searchCompany.getEndDate() != null) {
      where = where.and(
              qCompanyEntity.contractEntities.any().endDate.between(
                      searchCompany.getStartDate(),
                      searchCompany.getEndDate()
              )
      );
    }

    if (searchCompany.getActive() != null) {
      if (searchCompany.getActive()) {
        // 계약이 아직 진행 중인 회사 (endDate가 현재 날짜 이후인 회사)
        where = where.and(qCompanyEntity.contractEntities.any().endDate.goe(now));  // 계약이 아직 진행 중인 경우
      } else {
        // 계약이 종료된 회사 (endDate가 현재 날짜 이전인 회사)
        where = where.and(qCompanyEntity.contractEntities.any().endDate.lt(now));  // 계약이 종료된 경우
      }
    }

    // 검색어 필터링
    if (searchCompany.hasSearch()) {
      switch (searchCompany.getSearch()) {
        case "ALL":
          where = where.and(
                  qCompanyEntity.name.contains(searchCompany.getKeyword())
                          .or(qCompanyEntity.representative.contains(searchCompany.getKeyword()))
                          .or(qCompanyEntity.phone.contains(searchCompany.getKeyword()))
                          .or(qCompanyEntity.email.contains(searchCompany.getKeyword()))
          );
          break;
        case "name":
          where = where.and(qCompanyEntity.name.contains(searchCompany.getKeyword()));
          break;
        case "representative":
          where = where.and(qCompanyEntity.representative.contains(searchCompany.getKeyword()));
          break;
        case "phone":
          where = where.and(qCompanyEntity.phone.contains(searchCompany.getKeyword()));
          break;
        case "email":
          where = where.and(qCompanyEntity.email.contains(searchCompany.getKeyword()));
          break;
        default:
          break;
      }
    }

  Page<CompanyEntity> companyEntities = companyRepository.findAll(where, searchCompany.toPageRequest());

    Page<CompanyList> companyListPage = companyEntities.map(companyEntity -> {
      Company company = companyServiceMapper.toCompany(companyEntity);

      List<ContractEntity> contractEntities = contractRepository.findByCompanyEntity_Id(companyEntity.getId());
      // 가장 최신의 계약
      Optional<ContractEntity> latestContract = contractEntities.stream()
              .max(Comparator.comparing(ContractEntity::getEndDate, Comparator.nullsLast(Comparator.naturalOrder())));

      return CompanyList.builder()
              .company(company)
              .endDate(latestContract.map(ContractEntity::getEndDate).orElse(null))
              .title(latestContract.map(ContractEntity::getContent).orElse(null))
              .build();
    });

    return companyListPage;
  }

  public Company getCompany(Long id){
    Company company = companyRepository.findById(id)
            .map(companyServiceMapper::toCompany)
            .orElseThrow(() -> new AppException(AppErrorCode.COMPANY_NOT_FOUND));

    List<Contract> contracts = contractRepository.findByCompanyEntity_Id(id)
            .stream()
            .map(contractServiceMapper::toContract)
            .sorted(Comparator.comparing(Contract::getEndDate))
            .collect(Collectors.toList());
    company.setContracts(contracts);

    return company;
  }
}
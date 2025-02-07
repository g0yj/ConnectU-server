package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.company.ListCompanyResponse;
import com.lms.api.admin.controller.dto.company.ListCompanysRequest;
import com.lms.api.admin.controller.dto.user.*;
import com.lms.api.admin.service.CompanyService;
import com.lms.api.admin.service.UserAdminService;
import com.lms.api.admin.service.dto.Company;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.company.CompanyList;
import com.lms.api.admin.service.dto.company.SearchCompany;
import com.lms.api.admin.service.dto.user.CreateUser;
import com.lms.api.admin.service.dto.user.SearchUsers;
import com.lms.api.admin.service.dto.user.UpdateUser;
import com.lms.api.admin.service.dto.user.UserList;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.service.dto.LoginInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/admin/v1/company")
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyControllerMapper companyControllerMapper;
  private final CompanyService companyService;

  /**
   * 12. 업체 목록 조회
   */
  @GetMapping
  public PageResponse<ListCompanyResponse> listCompany(ListCompanysRequest request){
    SearchCompany searchCompany = companyControllerMapper.toSearchCompany(request);
    Page<CompanyList> companyListPage = companyService.listCompany(searchCompany);
    return companyControllerMapper.toListCompanyResponse(companyListPage, searchCompany);
  }

  /**
   * 13. 업체 상세 조회
   */
  @GetMapping("/{id}")
  public Company getCompany(@PathVariable Long id){
    return companyService.getCompany(id);
  }
}

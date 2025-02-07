package com.lms.api.admin.controller;
import com.lms.api.admin.controller.dto.company.ListCompanyResponse;
import com.lms.api.admin.controller.dto.company.ListCompanysRequest;
import com.lms.api.admin.controller.dto.user.*;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.company.CompanyList;
import com.lms.api.admin.service.dto.company.SearchCompany;
import com.lms.api.admin.service.dto.user.CreateUser;
import com.lms.api.admin.service.dto.user.SearchUsers;
import com.lms.api.admin.service.dto.user.UpdateUser;
import com.lms.api.admin.service.dto.user.UserList;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.common.service.dto.Search;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface CompanyControllerMapper {

  SearchCompany toSearchCompany(ListCompanysRequest request);

  @Mapping(target = "list", source = "companyPage.content")
  @Mapping(target = "totalCount", source = "companyPage.totalElements")
  PageResponse<ListCompanyResponse> toListCompanyResponse (Page<CompanyList> companyPage, Search search);

  @Mapping(target = "id" , source ="company.id" )
  @Mapping(target = "name" , source ="company.name" )
  @Mapping(target = "phone" , source ="company.phone" )
  @Mapping(target = "email" , source ="company.email" )
  ListCompanyResponse toListCompanyResponse(CompanyList company);
}



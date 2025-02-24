package com.lms.api.admin.controller;
import com.lms.api.admin.controller.dto.company.ListCompanyResponse;
import com.lms.api.admin.controller.dto.company.ListCompanysRequest;
import com.lms.api.admin.service.dto.company.CompanyList;
import com.lms.api.admin.service.dto.company.SearchCompany;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.common.service.dto.Search;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface ProblemControllerMapper {


}



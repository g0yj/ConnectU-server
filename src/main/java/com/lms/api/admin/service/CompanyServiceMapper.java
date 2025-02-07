package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Company;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.user.CreateUser;
import com.lms.api.admin.service.dto.user.UpdateUser;
import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.CompanyEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface CompanyServiceMapper {

  Company toCompany(CompanyEntity companyEntity);

}

package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Company;
import com.lms.api.admin.service.dto.Contract;
import com.lms.api.common.entity.CompanyEntity;
import com.lms.api.common.entity.ContractEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface ContractServiceMapper {

  Contract toContract (ContractEntity entity);


}

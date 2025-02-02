package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.*;
import com.lms.api.common.entity.*;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface UserAdminServiceMapper {

  User toUser(UserEntity userEntity);

}

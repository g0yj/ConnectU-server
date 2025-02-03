package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.*;
import com.lms.api.admin.service.dto.user.CreateUser;
import com.lms.api.admin.service.dto.user.UpdateUser;
import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.*;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface UserAdminServiceMapper {

  User toUser(UserEntity userEntity);

  @Mapping(target = "password", source = "password")
  UserEntity toUserEntity(CreateUser createUser, String id, String password, UserType type);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", source = "password")
  void mapUserEntity(UpdateUser updateUser, @MappingTarget UserEntity userEntity);
}

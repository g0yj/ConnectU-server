package com.lms.api.admin.controller;
import com.lms.api.admin.controller.dto.user.*;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.user.CreateUser;
import com.lms.api.admin.service.dto.user.SearchUsers;
import com.lms.api.admin.service.dto.user.UpdateUser;
import com.lms.api.admin.service.dto.user.UserList;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.common.service.dto.LoginInfo;
import com.lms.api.common.service.dto.Search;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface UserAdminControllerMapper {

  @Mapping(target = "type" , source = "listUsersRequest.type")
  SearchUsers toSearchUsers(ListUsersRequest listUsersRequest);

  @Mapping(target = "list", source = "userPage.content")
  @Mapping(target = "totalCount", source = "userPage.totalElements")
  PageResponse<ListUsersResponse> toListUsersResponse(Page<UserList> userPage, Search search);


  @Mapping(target = "id", source = "user.id")
  @Mapping(target = "name", source = "user.name")
  @Mapping(target = "cellPhone", source = "user.cellPhone")
  @Mapping(target = "email", source = "user.email")
  @Mapping(target = "remainingCount", source = "remainingCount")
  @Mapping(target = "expirationDate", source = "endDate")
  ListUsersResponse toListUsersResponse(UserList user);

  @Mapping(target = "type", source = "request.type")
  CreateUser toCreateUser(String createdBy, CreateUserRequest request);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "modifiedBy", source = "modifiedBy")
  UpdateUser toUpdateUser(String id, String modifiedBy, UpdateUserRequest request);

  @Mapping(target = "createDateTime", source = "createdOn")
  GetUserResponse toGetUserResponse(User user);
}



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

import java.util.List;


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

  List<ListUsersExcelResponse> toListUsersExcelResponse (List<User> users);
  @Mapping(target = "아이디", source = "loginId")
  @Mapping(target = "이름", source = "name")
  @Mapping(target = "성별", source = "gender")
  @Mapping(target = "연락처", source = "cellPhone")
  @Mapping(target = "이메일", source = "email")
  @Mapping(target = "주소1", source = "address")
  @Mapping(target = "주소2", source = "detailedAddress")
  @Mapping(target = "직장여부", source = "isOfficeWorker")
  @Mapping(target = "활동여부", source = "active")
  @Mapping(target = "특이사항", source = "note")
  ListUsersExcelResponse toListUsersExcelResponse(User user);

}



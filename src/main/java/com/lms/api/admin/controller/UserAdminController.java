package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.user.*;
import com.lms.api.admin.service.UserAdminService;
import com.lms.api.admin.service.dto.User;
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
@RequestMapping("/admin/v1/users")
@RequiredArgsConstructor
public class UserAdminController {

  private final UserAdminService userAdminService;
  private final UserAdminControllerMapper userAdminControllerMapper;

  /**
   * 03.회원목록
   */
  @GetMapping
  public PageResponse<ListUsersResponse> listUsers(ListUsersRequest request){
    SearchUsers searchUsers = userAdminControllerMapper.toSearchUsers(request);
    Page<UserList> userPage = userAdminService.listUsers(searchUsers);
    return userAdminControllerMapper.toListUsersResponse(userPage, searchUsers);
  }

  /**
   * 04. 회원등록
   */
  @PostMapping
  public void createUser(LoginInfo loginInfo, @RequestBody @Valid CreateUserRequest request){
    CreateUser createUser = userAdminControllerMapper.toCreateUser(loginInfo.getId(), request);
    userAdminService.createUser(createUser);
  }

  /**
   * 05. 회원상세조회
   */
  @GetMapping("/{id}")
  public GetUserResponse getUser(@PathVariable String id){
    User user = userAdminService.getUser(id);
    return userAdminControllerMapper.toGetUserResponse(user);
  }

  /**
   * 06. 회원수정
   */
  @PutMapping("/{id}")
  public void updateUser(
          @PathVariable String id,
          LoginInfo loginInfo,
          @RequestBody @Valid UpdateUserRequest request){
    UpdateUser updateUser = userAdminControllerMapper.toUpdateUser(id, loginInfo.getId(), request);
    userAdminService.updateUser(updateUser);
  }

  /**
   * 07. 회원삭제
   */
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable String id){
    userAdminService.deleteUser(id);
  }

  /**
   * 08. 회원 엑셀 다운로드
   */
  @GetMapping("/excel")
  public List<ListUsersExcelResponse> listUsersExcel(ListUsersExcelRequest request){
    List<User> users = userAdminService.listUsersExcel(request);
    return userAdminControllerMapper.toListUsersExcelResponse(users);
  }
}

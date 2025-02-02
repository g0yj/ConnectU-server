package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.user.ListUsersRequest;
import com.lms.api.admin.controller.dto.user.ListUsersResponse;
import com.lms.api.admin.service.UserAdminService;
import com.lms.api.admin.service.dto.user.SearchUsers;
import com.lms.api.admin.service.dto.user.UserList;
import com.lms.api.common.controller.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


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
}

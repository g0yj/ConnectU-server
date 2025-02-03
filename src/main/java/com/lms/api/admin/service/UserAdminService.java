package com.lms.api.admin.service;

import com.lms.api.admin.code.SearchCode;
import com.lms.api.admin.service.dto.*;
import com.lms.api.admin.service.dto.user.CreateUser;
import com.lms.api.admin.service.dto.user.SearchUsers;
import com.lms.api.admin.service.dto.user.UserList;
import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.*;
import com.lms.api.common.exception.AppError;
import com.lms.api.common.exception.AppErrorCode;
import com.lms.api.common.exception.AppException;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.repository.*;
import com.lms.api.common.util.AppUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAdminService {

  private final UserRepository userRepository;
  private final UserAdminServiceMapper userAdminServiceMapper;
  private final CourseRepository courseRepository;
  private final ServiceMapper serviceMapper;

  public Page<UserList> listUsers(SearchUsers searchUsers) {
    QUserEntity qUserEntity = QUserEntity.userEntity;
    QCourseEntity qCourseEntity = QCourseEntity.courseEntity;
    LocalDate now = LocalDate.now();

    BooleanExpression where = Expressions.TRUE;

    // 사용자 유형 필터링
    if (searchUsers.getType() != null) {
      where = where.and(qUserEntity.type.eq(searchUsers.getType()));
    }

    // 가입일자 필터링
    if (searchUsers.getCreateDateFrom() != null && searchUsers.getCreateDateTo() != null) {
      where = where.and(qUserEntity.createdOn.between(
              searchUsers.getCreateDateFrom().atStartOfDay(),
              searchUsers.getCreateDateTo().atTime(23, 59, 59)
      ));
    }

// 과정 만료 여부 필터링 (서브쿼리 내부에서 endDate 처리)
    BooleanExpression expiredCondition = JPAExpressions
            .selectOne()
            .from(qCourseEntity)
            .where(qCourseEntity.userEntity.id.eq(qUserEntity.id)
                    .and(qCourseEntity.endDate.isNotNull())
                    .and(qCourseEntity.endDate.loe(now))) // EXPIRED: endDate가 현재 날짜보다 작거나 같을 때
            .exists();

    BooleanExpression notExpiredCondition = JPAExpressions
            .selectOne()
            .from(qCourseEntity)
            .where(qCourseEntity.userEntity.id.eq(qUserEntity.id)
                    .and(qCourseEntity.endDate.isNotNull())
                    .and(qCourseEntity.endDate.goe(now))) // NOT_EXPIRED: endDate가 현재 날짜보다 클 때
            .exists();

    if (searchUsers.getExpireType() == SearchCode.ExpireType.EXPIRED) {
      where = where.and(expiredCondition); // 만료된 과정만 필터링
    } else if (searchUsers.getExpireType() == SearchCode.ExpireType.NOT_EXPIRED) {
      where = where.and(notExpiredCondition); // 만료되지 않은 과정만 필터링
    }

// 잔여 수업 횟수 필터링 (서브쿼리 내부에서 계산)
    BooleanExpression remainingCondition = JPAExpressions
            .selectOne()
            .from(qCourseEntity)
            .where(qCourseEntity.userEntity.id.eq(qUserEntity.id)
                    .and(qCourseEntity.lessonCount.coalesce(0.0f)
                            .subtract(qCourseEntity.assignmentCount.coalesce(0.0f))
                            .loe(0.0f)))  // NOT_REMAINING 조건: 잔여 수업 횟수가 0 이하
            .exists();

    BooleanExpression notRemainingCondition = JPAExpressions
            .selectOne()
            .from(qCourseEntity)
            .where(qCourseEntity.userEntity.id.eq(qUserEntity.id)
                    .and(qCourseEntity.lessonCount.coalesce(0.0f)
                            .subtract(qCourseEntity.assignmentCount.coalesce(0.0f))
                            .gt(0.0f)))  // REMAINING 조건: 잔여 수업 횟수가 0 초과
            .exists();

    if (searchUsers.getRemainingType() == SearchCode.RemainingType.REMAINING) {
      where = where.and(notRemainingCondition);  // 0 초과인 경우만 필터링
    } else if (searchUsers.getRemainingType() == SearchCode.RemainingType.NOT_REMAINING) {
      where = where.and(remainingCondition);  // 0 이하인 경우만 필터링
    }

    // 검색어 필터링
    if (searchUsers.hasSearch()) {
      switch (searchUsers.getSearch()) {
        case "ALL":
          where = where.and(
                  qUserEntity.name.contains(searchUsers.getKeyword())
                          .or(qUserEntity.loginId.contains(searchUsers.getKeyword()))
                          .or(qUserEntity.email.contains(searchUsers.getKeyword()))
                          .or(qUserEntity.cellPhone.contains(searchUsers.getKeyword()))
          );
          break;
        case "name":
          where = where.and(qUserEntity.name.contains(searchUsers.getKeyword()));
          break;
        case "loginId":
          where = where.and(qUserEntity.loginId.contains(searchUsers.getKeyword()));
          break;
        case "email":
          where = where.and(qUserEntity.email.contains(searchUsers.getKeyword()));
          break;
        case "cellPhone":
          where = where.and(qUserEntity.cellPhone.contains(searchUsers.getKeyword()));
          break;
        default:
          break;
      }
    }

    Page<UserEntity> userEntities = userRepository.findAll(where, searchUsers.toPageRequest());

    Page<UserList> userListPage = userEntities.map(
            userEntity -> {
              User user = userAdminServiceMapper.toUser(userEntity);
              List<CourseEntity> courseEntities = courseRepository.findByUserEntity_Id(userEntity.getId());
              Optional<CourseEntity> latestCourse = courseEntities.stream()
                      .max(Comparator.comparing(CourseEntity::getEndDate, Comparator.nullsLast(Comparator.naturalOrder())));

              return UserList.builder()
                      .user(user)
                      .endDate(latestCourse.map(CourseEntity::getEndDate).orElse(null))
                      .remainingCount(latestCourse.map(course -> course.getLessonCount() - course.getAssignmentCount()).orElse(0f))
                      .build();
            });

    return userListPage;
  }

  @Transactional
  public UserEntity createUser(CreateUser user) {
    if(userRepository.findByLoginId(user.getLoginId()).isPresent()){
      throw new AppException(AppErrorCode.LOGIN_SERVER_ERROR);
    }
    if(userRepository.findByCellPhone(user.getCellPhone()).isPresent()){
      throw new AppException(AppErrorCode.CELLPHONE_NOT_MATCH);
    }

    UserEntity userEntity = null;
    switch (user.getType().toString()){
      case "S":
        userEntity =
                userAdminServiceMapper.toUserEntity(
                        user,
                        AppUtils.createUserId(),
                        AppUtils.encryptPassword(user.getPassword()),
                        UserType.S);
        break;
      case "A":
        userEntity =
                userAdminServiceMapper.toUserEntity(
                        user,
                        AppUtils.createUserId(),
                        AppUtils.encryptPassword(user.getPassword()),
                        UserType.A);
        break;
      default:
        userEntity =
                userAdminServiceMapper.toUserEntity(
                        user,
                        AppUtils.createUserId(),
                        AppUtils.encryptPassword(user.getPassword()),
                        UserType.T);
    }
    return userRepository.save(userEntity);
  }

}

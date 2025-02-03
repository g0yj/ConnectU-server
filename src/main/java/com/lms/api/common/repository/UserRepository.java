package com.lms.api.common.repository;

import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String>,
    QuerydslPredicateExecutor<UserEntity> {
  Optional<UserEntity> findByLoginId(String loginId);
  Optional<UserEntity> findByCellPhone(String cellPhone);


}

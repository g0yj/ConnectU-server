package com.lms.api.common.repository;

import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLoginEntity, String>,
    QuerydslPredicateExecutor<UserLoginEntity> {

  Optional<UserLoginEntity> findByToken(String token);

  void deleteAllByUserEntity(UserEntity userEntity);
}


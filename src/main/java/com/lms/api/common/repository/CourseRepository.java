package com.lms.api.common.repository;

import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long>,
    QuerydslPredicateExecutor<CourseEntity> {
    List<CourseEntity> findByUserEntity_Id(String userId);


}

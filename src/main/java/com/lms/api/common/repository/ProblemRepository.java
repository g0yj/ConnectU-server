package com.lms.api.common.repository;

import com.lms.api.common.entity.CompanyEntity;
import com.lms.api.common.entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface ProblemRepository extends JpaRepository<ProblemEntity, Long>,
    QuerydslPredicateExecutor<CompanyEntity> {
    @Query("SELECT p FROM ProblemEntity p WHERE p.modifiedOn >= :lastMonth")
    List<ProblemEntity> findUpdatedProblems(LocalDateTime lastMonth);
}

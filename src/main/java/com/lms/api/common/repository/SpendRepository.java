package com.lms.api.common.repository;

import com.lms.api.common.entity.PaymentEntity;
import com.lms.api.common.entity.SpendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SpendRepository extends JpaRepository<SpendEntity, Long>,
    QuerydslPredicateExecutor<SpendEntity> {

}


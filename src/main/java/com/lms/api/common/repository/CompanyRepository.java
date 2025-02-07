package com.lms.api.common.repository;

import com.lms.api.common.entity.CompanyEntity;
import com.lms.api.common.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>,
    QuerydslPredicateExecutor<CompanyEntity> {

}

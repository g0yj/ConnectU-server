package com.lms.api.common.repository;

import com.lms.api.common.entity.CompanyEntity;
import com.lms.api.common.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ContractRepository extends JpaRepository<ContractEntity, Long>,
    QuerydslPredicateExecutor<ContractEntity> {

    List<ContractEntity>  findByCompanyEntity_Id(Long id);

}

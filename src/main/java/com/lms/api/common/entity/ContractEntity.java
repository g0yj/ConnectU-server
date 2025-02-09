package com.lms.api.common.entity;

import com.lms.api.common.code.ContractType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contract")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String title; // 계약내용
  String content; // 상세내용

  @Enumerated(EnumType.STRING)
  ContractType type;
  String name; // 담당자
  Long contractAmount; // 계약금액
  Long unpaid; // 미수금
  LocalDate payday; // 지불일
  LocalDate startDate; // 계약시작일
  LocalDate endDate; // 계약 종료일


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id", nullable = false)
  CompanyEntity companyEntity;

  @ToString.Exclude
  @OneToMany(mappedBy = "contractEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<ContractFileEntity> contractFileEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "contractEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<SpendEntity> spendEntities = new ArrayList<>();

}

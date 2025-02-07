package com.lms.api.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "company")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name; // 기업명
  String representative; // 대표자명
  String companyNumber; // 사업자등록번호
  String phone; // 연락처
  String email;
  String bank;
  String account;
  Boolean active; // 거래처 상태
  String note;



  @ToString.Exclude
  @OneToMany(mappedBy = "companyEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<ContractEntity> contractEntities = new ArrayList<>();

}
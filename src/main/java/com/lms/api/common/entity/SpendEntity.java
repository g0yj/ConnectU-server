package com.lms.api.common.entity;

import com.lms.api.common.code.BankCompany;
import com.lms.api.common.code.CardCompany;
import com.lms.api.common.code.PaymentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "spend")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpendEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Enumerated(EnumType.STRING)
  PaymentType type;

  Long price;

  Boolean isRefund; //환불 여부 (환불 시 true)
  String refundReason; // 환불 사유

  @Enumerated(EnumType.STRING)
  CardCompany cardCompany;
  String cardNumber;
  @Enumerated(EnumType.STRING)
  BankCompany bankCompany;
  String account;


  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "contract_id", nullable = false)
  ContractEntity contractEntity;


}
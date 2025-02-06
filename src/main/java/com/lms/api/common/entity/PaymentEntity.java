package com.lms.api.common.entity;

import com.lms.api.common.code.PaymentType;
import com.lms.api.common.code.ProductType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment")
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  @Enumerated(EnumType.STRING)
  PaymentType type;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false)
  UserEntity userEntity;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false) // 외래 키로 order_id
  OrderEntity orderEntity;


}

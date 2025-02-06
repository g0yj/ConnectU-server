package com.lms.api.common.entity;

import com.lms.api.common.code.PaymentType;
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
@Table(name = "order_")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  Integer totalPrice; // 총 주문금액
  Integer discount; // 할인 금액

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false)
  UserEntity userEntity;

  @OneToOne(mappedBy = "orderEntity", fetch = FetchType.LAZY)
  PaymentEntity paymentEntity;

  @ToString.Exclude
  @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<OrderProductEntity> orderProductEntities = new ArrayList<>();

}
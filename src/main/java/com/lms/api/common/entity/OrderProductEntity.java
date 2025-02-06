package com.lms.api.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "order_product")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderProductEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  OrderEntity orderEntity;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  ProductEntity productEntity;
}

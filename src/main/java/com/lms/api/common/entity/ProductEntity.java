package com.lms.api.common.entity;

import com.lms.api.common.code.DepartmentType;
import com.lms.api.common.code.ProductType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.commons.math3.stat.descriptive.summary.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  @Enumerated(EnumType.STRING)
  ProductType type;
  String name;
  Integer price;
  String description;


  @ToString.Exclude
  @OneToMany(mappedBy = "productEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<ProductFileEntity> productFileEntities = new ArrayList<>();

}

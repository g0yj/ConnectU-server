package com.lms.api.common.entity;

import com.lms.api.common.code.DepartmentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  @Enumerated(EnumType.STRING)
  DepartmentType type;


  @ToString.Exclude
  @OneToMany(mappedBy = "employeeEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<EmployeeFileEntity> employeeFileEntities = new ArrayList<>();

}

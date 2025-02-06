package com.lms.api.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "employee_file")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeFileEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String file;
  String originalFile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id", nullable = false)
  EmployeeEntity employeeEntity;
}

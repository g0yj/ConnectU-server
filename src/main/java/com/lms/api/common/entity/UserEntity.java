package com.lms.api.common.entity;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.YN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_")
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  String loginId;
  String name;
  String password;

  @Enumerated(EnumType.STRING)
  UserType type;

  @Enumerated(EnumType.STRING)
  Gender gender;

  String cellPhone;

  @Enumerated(EnumType.STRING)
  YN isReceiveSms;

  String email;

  @Enumerated(EnumType.STRING)
  YN isReceiveEmail;

  String address;
  String detailedAddress;


  @Enumerated(EnumType.STRING)
  YN isOfficeWorker;

  @Column(name = "is_active")
  boolean active;

  String note;

  String coursePurpose;

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<CourseEntity> courseEntities = new ArrayList<>();


}

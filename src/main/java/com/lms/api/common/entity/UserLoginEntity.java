package com.lms.api.common.entity;

import com.lms.api.common.code.LoginType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_login")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String token;

  @Enumerated(EnumType.STRING)
  LoginType loginType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  UserEntity userEntity;
}

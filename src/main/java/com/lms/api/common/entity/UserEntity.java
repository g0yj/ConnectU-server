package com.lms.api.common.entity;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.YN;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ✅ JPA를 위한 기본 생성자 추가
@AllArgsConstructor // ✅ 모든 필드를 포함한 생성자 추가 (필요할 경우)
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity implements UserDetails {

  @Id
  @Column(updatable = false)
  String id;

  String loginId;
  String name;
  String password;

/*
  시큐리티 사용 하기 위해서는 role이 있어야함. 우리는 이미 사용 중인 type이 있으니 이걸 사용!!
  @Enumerated(EnumType.STRING)
  Role role;
*/

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

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<EmailEntity> emailEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<PaymentEntity> paymentEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  List<OrderEntity> orderEntities = new ArrayList<>();

  // 스프링시큐리티 사용을 위해 implements UserDetails 했음.
  /**
   * 사용자의 권한을 Spring Security에서 사용할 수 있도록 변환
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    String role = type.toRole(); // ✅ Null 방지
    return List.of(new SimpleGrantedAuthority(type.toRole())); // UserType을 ROLE_ 형식으로 변환 (UserType 클래스 안에 변환 함수 있음)
  }

  /**
   * 사용자의 로그인 ID를 반환 (Spring Security에서 username으로 사용됨)
   */
  @Override
  public String getUsername() {
    return loginId; // 로그인 ID를 username으로 사용
  }

  /**
   * 계정의 만료 여부를 반환
   * - `true`: 계정이 만료되지 않음 (계속 사용 가능)
   * - `false`: 계정이 만료됨 (로그인 불가)
   */
  @Override
  public boolean isAccountNonExpired() {
    return true; // 기본적으로 계정 만료 기능을 사용하지 않으므로 true
  }

  /**
   * 계정의 잠금 여부를 반환
   * - `true`: 계정이 잠겨있지 않음 (로그인 가능)
   * - `false`: 계정이 잠겨있음 (로그인 불가)
   */
  @Override
  public boolean isAccountNonLocked() {
    return true; // 계정 잠금 기능을 사용하지 않으므로 true
  }

  /**
   * 비밀번호의 유효성 여부를 반환
   * - `true`: 비밀번호가 유효함
   * - `false`: 비밀번호가 만료됨 (로그인 불가, 비밀번호 변경 필요)
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true; // 기본적으로 비밀번호 만료 기능을 사용하지 않으므로 true
  }

  /**
   * 계정 활성화 여부를 반환
   * - `true`: 계정이 활성화됨 (로그인 가능)
   * - `false`: 계정이 비활성화됨 (로그인 불가)
   */
  @Override
  public boolean isEnabled() {
    return true; // 계정 활성화 상태 (비활성화 로직이 필요하면 false로 설정 가능)
  }
}

package com.lms.api.common.entity;

import com.lms.api.common.code.YN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Entity
@Table(name = "course")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "lesson_count", nullable = true)
  Float lessonCount; // 총 회차

  @Column(name = "assignment_count", nullable = true)
  Float assignmentCount; // 예약

  @Column(name = "attendance_count", nullable = true)
  Float attendanceCount; // 출석

  @Column(name = "start_date", nullable = true)
  LocalDate startDate;

  @Column(name = "end_date", nullable = true)
  LocalDate endDate;


  @ManyToOne(fetch = FetchType.EAGER)  // LAZY -> EAGER (QueryDSL에서 참조 가능하도록)
  @JoinColumn(name = "user_id", nullable = false)
  UserEntity userEntity;

  public float getRemainCount() {
    return (lessonCount != null ? lessonCount : 0f) - (assignmentCount != null ? assignmentCount : 0f);
  }

}
package com.lms.api.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Table(name = "reservation")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  LocalDate date;
  LocalTime startTime;
  LocalTime endTime;

  @ManyToOne(fetch = FetchType.EAGER)  // LAZY -> EAGER (QueryDSL에서 참조 가능하도록)
  @JoinColumn(name = "course_id", nullable = false)
  CourseEntity courseEntity;

}
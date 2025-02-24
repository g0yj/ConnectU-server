package com.lms.api.common.entity;

import com.lms.api.common.code.BankCompany;
import com.lms.api.common.code.CardCompany;
import com.lms.api.common.code.PaymentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "problem")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProblemEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Difficulty difficulty;

  @Column
  private String category;

  public enum Difficulty {
    EASY, MEDIUM, HARD
  }
}
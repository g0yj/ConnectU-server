package com.lms.api.admin.service.dto;

import com.lms.api.common.code.BankCompany;
import com.lms.api.common.code.CardCompany;
import com.lms.api.common.code.PaymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Spend {

    Long id;
    Long contractId;
    PaymentType type;
    Long price;
    Boolean isRefund;
    String refundReason;

    CardCompany cardCompany;
    String cardNumber;
    BankCompany bankCompany;
    String account;

    String createdBy;
    LocalDateTime createdOn;
    String modifiedBy;
    LocalDateTime modifiedOn;

}

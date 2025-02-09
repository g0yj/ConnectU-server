package com.lms.api.common.service;

import com.lms.api.common.controller.dto.SmsRequest;
import com.lms.api.common.entity.ContractEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.repository.ContractRepository;
import com.lms.api.common.repository.PaymentRepository;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.util.DayUtils;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentServiceConfig paymentServiceConfig;
    private final IamportClient api;
    private final ContractRepository contractRepository;
    @Value("${app.portone.api.code}")
    private String apiCode;


    @Transactional
    public void postPrepare(PrepareData prepareData) throws IamportResponseException, IOException {
        // Optional을 처리하기 위해 isPresent()로 확인
        Optional<ContractEntity> contractOptional = contractRepository.findById(Long.valueOf(prepareData.getMerchant_uid()));
        if (contractOptional.isPresent()) {
            ContractEntity contractEntity = contractOptional.get();
            // PG사 API 호출
            try {
                api.postPrepare(prepareData);
            } catch (IamportResponseException e) {
                log.error("PG사 응답 오류 발생: {}", e.getMessage());
                throw e;  // 예외를 다시 던져서 호출한 곳에서 처리할 수 있게 할 수 있습니다.
            } catch (IOException e) {
                log.error("PG사와의 통신 오류 발생: {}", e.getMessage());
                throw e;
            }
            // DB 업데이트
            Long amount = prepareData.getAmount().longValueExact();
            contractEntity.setUnpaid(contractEntity.getUnpaid()- amount);
        } else {
            log.error("해당 계약을 찾을 수 없습니다.");
        }
    }

}

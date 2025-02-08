package com.lms.api.common.controller;

import com.lms.api.common.controller.dto.PreparePaymentRequest;
import com.lms.api.common.controller.dto.SmsRequest;
import com.lms.api.common.service.PaymentService;
import com.lms.api.common.service.SmsService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.math.BigDecimal;


@Controller
@RequestMapping("/admin/v1/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    /**14. 결제 사전 검증
     *  얼만큼 결제가 이뤄어져야하는지 확인하기 위함 ->주문 결제금액이 다를 경우 pg사 결제창 호출이 중단
     * */
    @PostMapping("/prepare")
    public ResponseEntity<String> postPrepare(@RequestBody PreparePaymentRequest request) throws IamportResponseException, IOException{
        PrepareData data = new PrepareData(request.getMerchant_uid(), BigDecimal.valueOf(request.getAmount()) );
        paymentService.postPrepare(data);
        return ResponseEntity.ok("ok");
    }

}

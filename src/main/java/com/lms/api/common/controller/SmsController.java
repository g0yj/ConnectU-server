package com.lms.api.common.controller;

import com.lms.api.common.controller.dto.SmsRequest;
import com.lms.api.common.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/admin/v1/sms")
@RequiredArgsConstructor
@Slf4j
public class SmsController {

    private final SmsService smsService;
    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest request) {
        try {
            smsService.sendSms(request);
            return ResponseEntity.ok("SMS가 성공적으로 전송되었습니다.");
        } catch (Exception e) {
            log.error("SMS 전송 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SMS 전송에 실패하였습니다.");
        }
    }

}

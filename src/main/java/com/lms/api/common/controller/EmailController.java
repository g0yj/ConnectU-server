package com.lms.api.common.controller;

import com.lms.api.common.controller.dto.EmailRequest;
import com.lms.api.common.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) throws MessagingException, IOException {
        emailService.send(request);
        return ResponseEntity.ok("이메일이 성공적으로 전송되었습니다.");
    }

}

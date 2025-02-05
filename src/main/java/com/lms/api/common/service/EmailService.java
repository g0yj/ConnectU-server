package com.lms.api.common.service;

import com.lms.api.common.controller.dto.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private String fromEmail;

    public void send(EmailRequest request) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            // 이메일 제목 설정
            helper.setSubject(request.getTitle());
            // 이메일 내용 설정 (HTML 형식)
            helper.setText(request.getContent(), true);

            // 수신자 목록 설정
            if (request.getRecipients() != null && !request.getRecipients().isEmpty()) {
                String[] recipientEmails = request.getRecipients().stream().map(EmailRequest.Recipient::getEmail).toArray(String[]::new);
                helper.setTo(recipientEmails);  // 모든 수신자에게 이메일 발송
            }

            // 이메일 발송
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MessagingException("이메일 전송에 실패했습니다.", e);
        }
    }
}

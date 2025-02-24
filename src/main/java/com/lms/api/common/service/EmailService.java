package com.lms.api.common.service;

import com.lms.api.common.controller.dto.EmailRequest;
import com.lms.api.common.entity.ProblemEntity;
import com.lms.api.common.repository.ProblemRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final PdfService pdfService;
    private final ProblemRepository problemRepository;

    @Value("${spring.mail.username}")
    private String recipientEmail;

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

    /**
     * 📅 한 달 동안 변경된 문제를 조회하여 PDF로 변환 후 이메일 전송 (매달 실행)
     */
    @Scheduled(cron = "0 08 20 * * ?") // 매일 오후 6시 30분 실행
    public void sendMonthlyReport() {
        log.debug("📢 [스케줄링 실행] sendMonthlyReport() - 이메일 전송 시작");

        sendUpdatedProblemsReport();

        log.debug("✅ [스케줄링 완료] 이메일 전송 완료");
    }

    /**
     * 📧 이메일 전송 (PDF 첨부)
     */
    public void sendUpdatedProblemsReport() {
        log.debug("📩 [메일 발송] 메서드 진입");

        // 1. 최근 한 달 동안 변경된 문제 조회
        List<ProblemEntity> updatedProblems = problemRepository.findUpdatedProblems(LocalDateTime.now().minusMonths(1));
        log.debug("🔎 [변경된 문제 조회] {}개 문제 발견", updatedProblems.size());

        // 2. 변경된 문제가 없으면 이메일 전송 건너뜀
        if (updatedProblems.isEmpty()) {
            log.debug("⚠ 변경된 문제가 없어 이메일 전송을 건너뜁니다.");
            return;
        }

        try {
            // 3. PDF 생성
            byte[] pdfBytes = pdfService.generateProblemPDF(updatedProblems);
            log.debug("📄 [PDF 생성 완료] PDF 크기: {} bytes", pdfBytes.length);

            // 4. 이메일 생성 및 전송
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject("📌 최근 한 달 동안 변경/추가된 문제");
            helper.setText("첨부된 PDF 파일을 확인하세요.");

            // 5. PDF 파일 첨부
            helper.addAttachment("Updated_Problems.pdf", () -> new ByteArrayInputStream(pdfBytes));

            javaMailSender.send(message);
            log.debug("✅ [이메일 전송 성공] 수신자: {}", recipientEmail);

        } catch (Exception e) {
            log.error("❌ [이메일 전송 실패] 오류: {}", e.getMessage(), e);
        }
    }

}

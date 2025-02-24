package com.lms.api.common.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.lms.api.common.controller.dto.EmailRequest;
import com.lms.api.common.entity.ProblemEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfService {


    public byte[] generateProblemPDF(List<ProblemEntity> problemEntities) {
        log.debug("[PDF 생성]  problemEntities : {} " , problemEntities);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("최근 변경/추가된 문제 목록"));
        document.add(new Paragraph("------------------------"));

        for(ProblemEntity problemEntity : problemEntities) {
            document.add(new Paragraph("제목 : " + problemEntity.getTitle()));
            document.add(new Paragraph("내용: " + problemEntity.getContent()));
            document.add(new Paragraph("카테고리: " + problemEntity.getCategory()));
            document.add(new Paragraph("난이도: " + problemEntity.getDifficulty()));
            document.add(new Paragraph("------------------------"));
        }

        log.debug("PDF파일 document: {} " , document);
        document.close();
        return outputStream.toByteArray();
    }

}

package com.lms.api.common.service;

import com.lms.api.common.controller.dto.EmailRequest;
import com.lms.api.common.controller.dto.SmsRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {
    @Value("${app.coolsms.api.number}")
    private String number;

    private final DefaultMessageService messageService;
    public void sendSms(SmsRequest request) {
        List<Message> messageList = new ArrayList<>();

        for (int i = 0; i < request.getRecipients().size(); i++) {
            Message message = new Message();
            String newNumber = formatPhoneNumber(request.getRecipients().get(i).getCellPhone());
            message.setFrom(number);
            message.setTo(newNumber);
            message.setText(request.getContent());
            messageList.add(message);
            log.debug("만들어진 메세지 객체 {}", messageList);
        }
        try {
            MultipleDetailMessageSentResponse response = this.messageService.send(messageList, false, true);
            return;
        } catch (NurigoMessageNotReceivedException e) {
            log.debug("에러발생!!!! {}" , e.getFailedMessageList());
        } catch (Exception e){
            log.debug("에러발생!! {} ", e.getMessage() );
        }
        return;

    }

    private String formatPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("-", "");
    }
}

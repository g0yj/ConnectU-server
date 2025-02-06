package com.lms.api.common.service;

import com.lms.api.common.controller.dto.SmsRequest;
import com.lms.api.common.entity.QCourseEntity;
import com.lms.api.common.entity.QReservationEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.util.DayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.poi.ss.formula.functions.Single;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final ReservationRepository reservationRepository;

    @Value("${app.coolsms.api.number}")
    private String number;

    private final DefaultMessageService messageService;

    /** 단건 발송*/
    public void sendOneSms(String cellPhone, String text){
        Message message = new Message();
        String newNumber = formatPhoneNumber(cellPhone);
        message.setFrom(number);
        message.setTo(newNumber);
        message.setText(text);

        log.debug("CellPhone {}, text {} ", cellPhone,text);
        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
        log.debug("예약 문자 정보 {}", response);
    }
    /** 다건 발송*/
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
            MultipleDetailMessageSentResponse response = messageService.send(messageList, false, true);
            return;
        } catch (NurigoMessageNotReceivedException e) {
            log.debug("에러발생!!!! {}" , e.getFailedMessageList());
        } catch (Exception e){
            log.debug("에러발생!! {} ", e.getMessage() );
        }
        return;
    }

    @Scheduled(cron = "00 16 * * * ?")
    public void sendReservationNotification(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        List<ReservationEntity> reservationEntities = reservationRepository.findByDate(tomorrow);

        for(ReservationEntity reservationEntity : reservationEntities) {
            String cellPhone = reservationEntity.getCourseEntity().getUserEntity().getCellPhone();
            String text = "";
            if (cellPhone != null && !cellPhone.isEmpty()) {
                text = "안녕하세요. 내일 "
                        + DayUtils.convertToAmPmFormat(String.valueOf(reservationEntity.getStartTime()))
                        + " 예약이 있습니다";
                sendOneSms(cellPhone,text);
            }

        }

    }


    private String formatPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("-", "");
    }

}

package com.lms.api.common.service;

import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsServiceConfig {

    @Value("${app.coolsms.api.key}")
    private String apiKey;

    @Value("${app.coolsms.api.secret}")
    private String apiSecret;

    @Value("${app.coolsms.api.number}")
    private String apiNumber;

    // API URL 설정 (Coolsms URL을 지정)
    private String url = "https://api.coolsms.co.kr/sms/1/send/"; // CoolSMS API URL

    @Bean
    public DefaultMessageService messageService() {
        return new DefaultMessageService(apiKey, apiSecret, url);
    }
}

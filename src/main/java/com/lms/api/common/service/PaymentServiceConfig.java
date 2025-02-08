package com.lms.api.common.service;

import com.siot.IamportRestClient.IamportClient;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceConfig {

    @Value("${app.portone.api.key}")
    private String apiKey;

    @Value("${app.portone.api.secret}")
    private String apiSecret;


    @Bean
    public IamportClient importClient() {
        return new IamportClient(apiKey, apiSecret);
    }
}

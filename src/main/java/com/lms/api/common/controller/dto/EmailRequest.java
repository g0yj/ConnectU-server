package com.lms.api.common.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private String title;
    private List<Recipient> recipients; // 수신자
    private String content; // 이메일 내용

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Recipient {
        private String id;
        private String name;  // 수신자 이름
        private String email; // 수신자 이메일
    }
}

package com.lms.api.common.service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsDto {
    String title;
    String date;
    String link;


}

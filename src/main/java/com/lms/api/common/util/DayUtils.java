package com.lms.api.common.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DayUtils extends org.springframework.util.StringUtils {
  /** 시간 포맷 */
  public static String convertToAmPmFormat(String time) {
    LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
    int hour = localTime.getHour();
    String period = hour < 12 ? "오전" : "오후"; //
    int formattedHour = hour % 12 == 0 ? 12 : hour % 12;
    String minute = String.format("%02d", localTime.getMinute());
    return String.format("%s %d시 %s분", period, formattedHour, minute);
  }

}

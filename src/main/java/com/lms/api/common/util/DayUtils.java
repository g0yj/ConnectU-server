package com.lms.api.common.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DayUtils extends org.springframework.util.StringUtils {
  /** 시간 포맷 1 */
  public static String convertToAmPmFormat(String time) {
    LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
    int hour = localTime.getHour();
    String period = hour < 12 ? "오전" : "오후"; //
    int formattedHour = hour % 12 == 0 ? 12 : hour % 12;
    String minute = String.format("%02d", localTime.getMinute());
    return String.format("%s %d시 %s분", period, formattedHour, minute);
  }

  /**
   * 시간포맷2
   */
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

  public static String convertRelativeDate(String relativeDateText) {
    if (relativeDateText == null || relativeDateText.isEmpty()) {
      return null; // 입력값이 없으면 null 반환
    }

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime articleDate = now;

    // "5시간 전", "24분 전" 같은 불필요한 단어를 제거하고 숫자와 단위만 추출
    relativeDateText = relativeDateText.trim().replace(" 전", "");

    // 정규식을 사용하여 숫자와 단위를 추출
    Pattern pattern = Pattern.compile("(\\d+)(분|시간|일)");
    Matcher matcher = pattern.matcher(relativeDateText);

    if (matcher.find()) {
      int amount = Integer.parseInt(matcher.group(1)); // 숫자 부분
      String unit = matcher.group(2); // 시간 단위 (분, 시간, 일)

      switch (unit) {
        case "분":
          articleDate = now.minusMinutes(amount);
          break;
        case "시간":
          articleDate = now.minusHours(amount);
          break;
        case "일":
          articleDate = now.minusDays(amount);
          break;
        default:
          return null; // 예상치 못한 형식이면 null 반환
      }
    } else {
      return null; // 변환할 수 없는 형식이면 null 반환
    }

    // 날짜를 'yyyy.MM.dd.' 형식으로 출력
    return articleDate.toLocalDate().format(DATE_FORMAT);
  }
}

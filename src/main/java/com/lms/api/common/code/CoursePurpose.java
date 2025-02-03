package com.lms.api.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CoursePurpose {
    STUDY_ABROAD("10"), // 유학
    TEST("20"), // 시험
    EMPLOYMENT("30"), // 취업
    WORK("40"), //업무
    DEVELOPMENT("50"), // 자기개발
    ;

    String code;

    public static CoursePurpose of(String codeOrName) {
        return Arrays.stream(values())
                .filter(value -> value.getCode().equals(codeOrName) || value.name().equalsIgnoreCase(codeOrName))
                .findFirst()
                .orElse(null);
    }
}

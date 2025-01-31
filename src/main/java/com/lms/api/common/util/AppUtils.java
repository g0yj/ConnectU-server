package com.lms.api.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface AppUtils {

    static boolean checkPassword(String password, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (hashedPassword.startsWith("$2a$")) {
            // 암호화된 비밀번호 비교
            return encoder.matches(password, hashedPassword);
        } else {
            // 평문 비밀번호 비교 (테스트 용도)
            return password.equals(hashedPassword);
        }
    }
}

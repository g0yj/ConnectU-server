package com.lms.api.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface AppUtils {
    String ID_PREFIX_USER = "M";

    static String createUserId() {
        return createId(ID_PREFIX_USER);
    }


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

    static String createId(String prefix) {
        return prefix + System.currentTimeMillis() + StringUtils.leftPad(
                Integer.toString((int) (Math.random() * 1000)), 3, "0");
    }
    static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}

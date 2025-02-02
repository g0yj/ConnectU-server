package com.lms.api.admin.code;

public interface SearchCode {
    enum ExpireType {
        ALL, // 전체
        EXPIRED, // 만료됨
        NOT_EXPIRED // 만료안됨
    }

    enum RemainingType {
        ALL, // 전체
        REMAINING, // 잔여있음
        NOT_REMAINING // 잔여없음
    }

}

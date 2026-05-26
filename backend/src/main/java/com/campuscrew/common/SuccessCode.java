package com.campuscrew.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // 인증 API
    SUCCESS_AUTH_001(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    SUCCESS_AUTH_002(HttpStatus.OK, "로그인에 성공했습니다."),
    SUCCESS_AUTH_003(HttpStatus.OK, null);

    private final HttpStatus httpStatus;
    private final String message;
}

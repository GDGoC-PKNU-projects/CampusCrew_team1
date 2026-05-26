package com.campuscrew.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 인증 에러 (401)
    AUTH_001(HttpStatus.UNAUTHORIZED, "AUTH_001", "로그인 정보가 올바르지 않습니다."),
    AUTH_002(HttpStatus.UNAUTHORIZED, "AUTH_002", "인증 토큰이 없습니다."),
    AUTH_003(HttpStatus.UNAUTHORIZED, "AUTH_003", "인증 토큰이 만료되었습니다."),
    AUTH_004(HttpStatus.UNAUTHORIZED, "AUTH_004", "유효하지 않은 인증 토큰입니다."),

    // 검증 에러 - 공통 (400)
    VALID_001(HttpStatus.BAD_REQUEST, "VALID_001", "필수 입력값이 누락되었습니다."),
    VALID_002(HttpStatus.BAD_REQUEST, "VALID_002", "입력값 형식이 올바르지 않습니다."),

    // 검증 에러 - 회원가입 (400)
    VALID_EMAIL_001(HttpStatus.BAD_REQUEST, "VALID_EMAIL_001", "이메일 형식이 올바르지 않습니다."),
    VALID_EMAIL_002(HttpStatus.BAD_REQUEST, "VALID_EMAIL_002", "이메일은 최대 100자까지 입력할 수 있습니다."),
    VALID_PW_001(HttpStatus.BAD_REQUEST, "VALID_PW_001", "비밀번호는 8자 이상 20자 이하로 입력해야 합니다."),
    VALID_NAME_001(HttpStatus.BAD_REQUEST, "VALID_NAME_001", "이름은 2자 이상 20자 이하로 입력해야 합니다."),
    VALID_STUID_001(HttpStatus.BAD_REQUEST, "VALID_STUID_001", "학번은 숫자만 입력해야 합니다."),
    VALID_STUID_002(HttpStatus.BAD_REQUEST, "VALID_STUID_002", "학번은 8자 이상 10자 이하로 입력해야 합니다."),

    // 충돌 에러 - 회원가입 (409)
    CONFLICT_001(HttpStatus.CONFLICT, "CONFLICT_001", "이미 사용 중인 이메일입니다."),
    CONFLICT_002(HttpStatus.CONFLICT, "CONFLICT_002", "이미 사용 중인 학번입니다."),

    // 서버 에러 (500)
    SERVER_001(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_001", "서버 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

package com.campuscrew.exception;

import com.campuscrew.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.error(errorCode.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> resolveErrorCode(error.getField(), error.getCode()))
                .orElse(ErrorCode.VALID_001);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(errorCode.getMessage()));
    }

    private ErrorCode resolveErrorCode(String field, String code) {
        if ("NotBlank".equals(code)) return ErrorCode.VALID_001;
        return switch (field) {
            case "name" -> ErrorCode.VALID_NAME_001;
            case "studentId" -> "Pattern".equals(code) ? ErrorCode.VALID_STUID_001 : ErrorCode.VALID_STUID_002;
            case "email" -> "Email".equals(code) ? ErrorCode.VALID_EMAIL_001 : ErrorCode.VALID_EMAIL_002;
            case "password" -> ErrorCode.VALID_PW_001;
            default -> ErrorCode.VALID_001;
        };
    }
}
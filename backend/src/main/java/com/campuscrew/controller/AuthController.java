package com.campuscrew.controller;

import com.campuscrew.common.ApiResponse;
import com.campuscrew.dto.LoginRequestDTO;
import com.campuscrew.dto.LoginResponseDTO;
import com.campuscrew.dto.MeResponseDTO;
import com.campuscrew.dto.SignUpRequestDTO;
import com.campuscrew.dto.SignUpResponseDTO;
import com.campuscrew.entity.UserEntity;
import com.campuscrew.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponseDTO>> signUp(@RequestBody SignUpRequestDTO signUpRequest) {
        SignUpResponseDTO data = authService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(data, "회원가입이 완료되었습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO data = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(data, "로그인에 성공했습니다."));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MeResponseDTO>> me(@AuthenticationPrincipal UserEntity user) {
        MeResponseDTO data = MeResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .studentId(user.getStudentId())
                .build();
        return ResponseEntity.ok(ApiResponse.success(data, null));
    }
}

package com.campuscrew;

import com.campuscrew.dto.SignUpRequestDTO;
import com.campuscrew.dto.SignUpResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponseDTO>> signUp(@RequestBody SignUpRequestDTO signUpRequest) {
        SignUpResponseDTO data = authService.signUp(signUpRequest);
        return ResponseEntity.ok(ApiResponse.success(data, "회원가입이 완료되었습니다."));
    }
}

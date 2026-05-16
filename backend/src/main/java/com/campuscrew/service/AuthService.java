package com.campuscrew.service;

import com.campuscrew.dto.LoginRequestDTO;
import com.campuscrew.dto.LoginResponseDTO;
import com.campuscrew.dto.SignUpRequestDTO;
import com.campuscrew.dto.SignUpResponseDTO;
import com.campuscrew.entity.UserEntity;
import com.campuscrew.exception.CustomException;
import com.campuscrew.exception.ErrorCode;
import com.campuscrew.repository.AuthRepository;
import com.campuscrew.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthRepository authRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequest) {
        if (authRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException(ErrorCode.CONFLICT_001);
        }

        if (authRepository.existsByStudentId(signUpRequest.getStudentId())) {
            throw new CustomException(ErrorCode.CONFLICT_002);
        }

        UserEntity userEntity = new UserEntity(
                signUpRequest.getName(),
                signUpRequest.getStudentId(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword())
        );
        UserEntity savedUser = authRepository.save(userEntity);

        return SignUpResponseDTO.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        UserEntity user = authRepository.findByEmail(loginRequest.getEmail());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.AUTH_001);
        }

        String token = jwtUtil.generateToken(user.getId());

        return LoginResponseDTO.builder()
                .accessToken(token)
                .user(LoginResponseDTO.UserInfo.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .build();
    }

}

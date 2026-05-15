package com.campuscrew.service;

import com.campuscrew.dto.SignUpRequestDTO;
import com.campuscrew.dto.SignUpResponseDTO;
import com.campuscrew.entity.UserEntity;
import com.campuscrew.exception.CustomException;
import com.campuscrew.exception.ErrorCode;
import com.campuscrew.repository.AuthRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(AuthRepository authRepository, BCryptPasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequest) {
        if (authRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException(ErrorCode.CONFLICT_001);
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

}

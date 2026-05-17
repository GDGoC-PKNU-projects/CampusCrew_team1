package com.campuscrew.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponseDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}

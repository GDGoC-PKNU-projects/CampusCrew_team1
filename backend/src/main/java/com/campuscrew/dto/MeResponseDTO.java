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
public class MeResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String studentId;
    private LocalDateTime createdAt;
}

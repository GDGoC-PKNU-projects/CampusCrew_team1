package com.campuscrew.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDTO {
    private String name;
    private String studentId;
    private String email;
    private String password;
}

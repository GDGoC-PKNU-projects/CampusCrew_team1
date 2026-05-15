package com.campuscrew.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDTO {

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    @Size(min = 8, max = 10)
    private String studentId;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
}

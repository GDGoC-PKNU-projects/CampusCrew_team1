package com.campuscrew.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

    @NotBlank(message = "VALID_001")
    @Size(min = 2, max = 20, message = "VALID_NAME_001")
    private String name;

    @NotBlank(message = "VALID_001")
    @Pattern(regexp = "^[0-9]+$", message = "VALID_STUID_001")
    @Size(min = 8, max = 10, message = "VALID_STUID_002")
    private String studentId;

    @NotBlank(message = "VALID_001")
    @Email(message = "VALID_EMAIL_001")
    @Size(max = 100, message = "VALID_EMAIL_002")
    private String email;

    @NotBlank(message = "VALID_001")
    @Size(min = 8, max = 20, message = "VALID_PW_001")
    private String password;
}

package com.campuscrew.dto.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamJoinRequestDTO {

    @NotBlank(message = "VALID_001")
    @Pattern(regexp = "^[A-Z0-9]{6}$", message = "VALID_TEAM_004")
    private String joinCode;
}

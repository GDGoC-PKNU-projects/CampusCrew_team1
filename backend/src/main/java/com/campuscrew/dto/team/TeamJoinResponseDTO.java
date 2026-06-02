package com.campuscrew.dto.team;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamJoinResponseDTO {
    private Long teamId;
    private String teamName;
}

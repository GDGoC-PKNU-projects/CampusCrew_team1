package com.campuscrew.dto.team;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamDetailResponseDTO {
    private Long id;
    private String name;
    private String courseName;
    private String description;
    private Long memberCount;
    private String joinCode;
    private Long ownerId;
}

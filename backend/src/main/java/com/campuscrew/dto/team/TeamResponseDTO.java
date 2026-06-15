package com.campuscrew.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TeamResponseDTO {
    private Long id;
    private String name;
    private String courseName;
    private String description;
    private Long memberCount;
}

package com.campuscrew.controller;

import com.campuscrew.common.ApiResponse;
import com.campuscrew.dto.team.CreateTeamRequestDTO;
import com.campuscrew.dto.team.CreateTeamResponseDTO;
import com.campuscrew.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/teams")
    public ResponseEntity<ApiResponse<CreateTeamResponseDTO>> createTeam(@Valid @RequestBody CreateTeamRequestDTO createTeamRequest) {
        CreateTeamResponseDTO data = teamService.createTeam(createTeamRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(data, ))
    }
}

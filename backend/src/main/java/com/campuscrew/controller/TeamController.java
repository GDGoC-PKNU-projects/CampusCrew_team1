package com.campuscrew.controller;

import com.campuscrew.common.ApiResponse;
import com.campuscrew.common.SuccessCode;
import com.campuscrew.dto.team.CreateTeamRequestDTO;
import com.campuscrew.dto.team.CreateTeamResponseDTO;
import com.campuscrew.dto.team.TeamResponseDTO;
import com.campuscrew.entity.UserEntity;
import com.campuscrew.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;


    @GetMapping("/teams")
    public ResponseEntity<ApiResponse<List<TeamResponseDTO>>> teams(@AuthenticationPrincipal UserEntity user){
        List<TeamResponseDTO> data = teamService.getMyTeams(user);
        return ResponseEntity.ok(ApiResponse.success(data, SuccessCode.SUCCESS_TEAM_001));
    }

    @PostMapping("/teams")
    public ResponseEntity<ApiResponse<CreateTeamResponseDTO>> createTeam(@Valid @RequestBody CreateTeamRequestDTO createTeamRequest,
                                                                         @AuthenticationPrincipal UserEntity user) {
        CreateTeamResponseDTO data = teamService.createTeam(createTeamRequest, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(data, SuccessCode.SUCCESS_TEAM_002));
    }

}

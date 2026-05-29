package com.campuscrew.service;

import com.campuscrew.dto.team.CreateTeamRequestDTO;
import com.campuscrew.dto.team.CreateTeamResponseDTO;
import com.campuscrew.entity.MemberEntity;
import com.campuscrew.entity.TeamEntity;
import com.campuscrew.entity.UserEntity;
import com.campuscrew.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;


    //joinCode생성용 문자
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH = 6;
    private final SecureRandom rand = new SecureRandom();
    private String generateJoinCode(){
        StringBuilder joinCode = new StringBuilder(LENGTH);
        for (int i=0; i< LENGTH; i++) {
            joinCode.append(CHARACTERS.charAt(rand.nextInt(CHARACTERS.length())));
        }

        return joinCode.toString();
    }


    public CreateTeamResponseDTO createTeam(CreateTeamRequestDTO createTeamRequest, UserEntity owner){
        String joinCode;
        do {joinCode = generateJoinCode();}
        while (teamRepository.existsByJoinCode(joinCode));


        TeamEntity teamEntity = new TeamEntity(
                createTeamRequest.getName(),
                createTeamRequest.getCourseName(),
                createTeamRequest.getDescription(),
                joinCode,
                owner
        );

        TeamEntity newTeam = teamRepository.save(teamEntity);

        return CreateTeamResponseDTO.builder()
                .id(newTeam.getId())
                .name(newTeam.getName())
                .courseName(newTeam.getCourseName())
                .description(newTeam.getDescription())
                .joinCode(newTeam.getJoinCode())
                .build();
    }
}

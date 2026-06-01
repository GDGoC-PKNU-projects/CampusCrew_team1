package com.campuscrew.service;

import com.campuscrew.dto.team.CreateTeamRequestDTO;
import com.campuscrew.dto.team.CreateTeamResponseDTO;
import com.campuscrew.dto.team.TeamResponseDTO;
import com.campuscrew.entity.MemberEntity;
import com.campuscrew.entity.TeamEntity;
import com.campuscrew.entity.UserEntity;
import com.campuscrew.repository.MemberRepository;
import com.campuscrew.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;


    public

    public List<TeamResponseDTO> getMyTeams(UserEntity user){
        List<TeamResponseDTO> result = new ArrayList<>();
        List<MemberEntity> members = memberRepository.findByUser_Id(user.getId());
        for (MemberEntity member: members){
            TeamEntity team = member.getTeam();
            Long teamId = team.getId();
            result.add(TeamResponseDTO.builder()
                    .id(teamId)
                            .name(team.getName())
                            .courseName(team.getCourseName())
                            .description(team.getDescription())
                            .memberCount(memberRepository.countByTeam_Id(teamId))
                    .build());
        }
        return result;

    }

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


    @Transactional
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

        TeamEntity savedTeam = teamRepository.save(teamEntity);

        MemberEntity ownerMember = new MemberEntity(savedTeam, owner, "OWNER");
        memberRepository.save(ownerMember);
        return CreateTeamResponseDTO.builder()
                .id(savedTeam.getId())
                .name(savedTeam.getName())
                .courseName(savedTeam.getCourseName())
                .description(savedTeam.getDescription())
                .joinCode(savedTeam.getJoinCode())
                .build();
    }
}

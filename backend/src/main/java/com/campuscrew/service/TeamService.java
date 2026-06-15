package com.campuscrew.service;

import com.campuscrew.dto.team.*;
import com.campuscrew.entity.MemberEntity;
import com.campuscrew.entity.TeamEntity;
import com.campuscrew.entity.UserEntity;
import com.campuscrew.exception.CustomException;
import com.campuscrew.exception.ErrorCode;
import com.campuscrew.repository.MemberRepository;
import com.campuscrew.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;


    public void validateMembership(Long teamId, Long userId) {
        if (!memberRepository.existsByTeam_IdAndUser_Id(teamId, userId)){
           throw  new CustomException(ErrorCode.FORBIDDEN_001);
        }
    }

    @Transactional(readOnly = true)
    public List<TeamResponseDTO> getMyTeams(UserEntity user){
        return memberRepository.findMyTeamsWithMemberCount(user.getId());
    }

    @Transactional(readOnly = true)
    public TeamDetailResponseDTO getTeamDetail(Long teamId, UserEntity user){
        validateMembership(teamId, user.getId());
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_002));
        return TeamDetailResponseDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .courseName(team.getCourseName())
                .description(team.getDescription())
                .joinCode(team.getJoinCode())
                .ownerId(team.getOwner().getId())
                .memberCount(memberRepository.countByTeam_Id(teamId))
                .build();
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



    @Transactional
    public TeamJoinResponseDTO joinTeam(TeamJoinRequestDTO joinData, UserEntity user){
        String joinCode = joinData.getJoinCode();
        Long userId = user.getId();
        TeamEntity team = teamRepository.findByJoinCode(joinCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_002));

        if (memberRepository.existsByTeam_IdAndUser_Id(team.getId(), userId)){
            throw new CustomException(ErrorCode.CONFLICT_003);
        }

        MemberEntity commonMember = new MemberEntity(team, user, "MEMBER");
        memberRepository.save(commonMember);
        return TeamJoinResponseDTO.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .build();
    }
}

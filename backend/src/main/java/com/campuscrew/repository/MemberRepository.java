package com.campuscrew.repository;

import com.campuscrew.dto.team.TeamResponseDTO;
import com.campuscrew.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    List<MemberEntity> findByUser_Id(Long userId);

    boolean existsByTeam_IdAndUser_Id(Long teamId, Long userId);

    Long countByTeam_Id(Long teamId);

    @Query("""
            SELECT new com.campuscrew.dto.team.TeamResponseDTO(
                t.id, t.name, t.courseName, t.description, COUNT(m2.id))
            FROM MemberEntity m
            JOIN m.team t
            JOIN MemberEntity m2 ON m2.team = t
            WHERE m.user.id = :userId
            GROUP BY t.id, t.name, t.courseName, t.description
            """)
    List<TeamResponseDTO> findMyTeamsWithMemberCount(@Param("userId") Long userId);

}

package com.campuscrew.repository;

import com.campuscrew.entity.MemberEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    List<MemberEntity> findByUser_Id(Long userId);

    boolean existsByTeam_IdAndUser_Id(Long teamId, Long userId);

    Long countByTeam_Id(Long teamId);
}

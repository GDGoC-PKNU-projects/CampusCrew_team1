package com.campuscrew.repository;

import com.campuscrew.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    boolean existsByJoinCode(String joinCode);
}

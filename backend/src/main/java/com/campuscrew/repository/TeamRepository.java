package com.campuscrew.repository;

import com.campuscrew.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    boolean existsByJoinCode(String joinCode);

    Optional<TeamEntity> findByJoinCode(String joinCode);

}

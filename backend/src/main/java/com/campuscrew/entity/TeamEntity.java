package com.campuscrew.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "teams")
@Getter
@NoArgsConstructor
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, name = "course_name", length = 30)
    private String courseName;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, unique = true, name = "join_code", length = 6)
    private String joinCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    public TeamEntity(String name, String courseName, String description, String joinCode, UserEntity owner) {
        this.name = name;
        this.courseName = courseName;
        this.description = description;
        this.joinCode = joinCode;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
    }
}

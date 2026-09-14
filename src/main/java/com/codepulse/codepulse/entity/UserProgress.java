package com.codepulse.codepulse.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "user_progress",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "problem_key"}
        )
)
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "problem_key", nullable = false)
    private String problemKey;

    @Column(name = "solved_at", nullable = false)
    private LocalDate solvedAt;

    public UserProgress() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProblemKey() {
        return problemKey;
    }

    public void setProblemKey(String problemKey) {
        this.problemKey = problemKey;
    }

    public LocalDate getSolvedAt() {
        return solvedAt;
    }

    public void setSolvedAt(LocalDate solvedAt) {
        this.solvedAt = solvedAt;
    }
}
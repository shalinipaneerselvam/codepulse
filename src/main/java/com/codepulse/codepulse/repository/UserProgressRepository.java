package com.codepulse.codepulse.repository;

import com.codepulse.codepulse.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProgressRepository
        extends JpaRepository<UserProgress, Long> {

    List<UserProgress> findByUserUsernameOrderBySolvedAtAsc(
            String username
    );

    boolean existsByUserUsernameAndProblemKey(
            String username,
            String problemKey
    );
}
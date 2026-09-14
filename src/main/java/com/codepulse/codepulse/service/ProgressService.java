package com.codepulse.codepulse.service;

import com.codepulse.codepulse.entity.User;
import com.codepulse.codepulse.entity.UserProgress;
import com.codepulse.codepulse.repository.UserProgressRepository;
import com.codepulse.codepulse.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private static final int TOTAL_PROBLEMS = 21;

    private final UserRepository userRepository;
    private final UserProgressRepository userProgressRepository;

    public ProgressService(
            UserRepository userRepository,
            UserProgressRepository userProgressRepository) {

        this.userRepository = userRepository;
        this.userProgressRepository = userProgressRepository;
    }

    // ================= MARK PROBLEM AS SOLVED =================

    @Transactional
    public void markSolved(String username, String problemKey) {

        boolean alreadySolved =
                userProgressRepository
                        .existsByUserUsernameAndProblemKey(
                                username,
                                problemKey
                        );

        if (alreadySolved) {
            return;
        }

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + username
                        )
                );

        UserProgress progress = new UserProgress();

        progress.setUser(user);
        progress.setProblemKey(problemKey);
        progress.setSolvedAt(LocalDate.now());

        userProgressRepository.save(progress);
    }

    // ================= ALL SOLVED PROBLEMS =================

    public Set<String> getSolvedProblems(String username) {

        return userProgressRepository
                .findByUserUsernameOrderBySolvedAtAsc(username)
                .stream()
                .map(UserProgress::getProblemKey)
                .collect(
                        Collectors.toCollection(
                                LinkedHashSet::new
                        )
                );
    }

    // ================= TOTAL SOLVED =================

    public int getProblemsSolved(String username) {

        return getSolvedProblems(username).size();
    }

    // ================= OVERALL PROGRESS =================

    public int getProgress(String username) {

        int solved = getProblemsSolved(username);

        return (solved * 100) / TOTAL_PROBLEMS;
    }

    // ================= BASIC PROBLEMS =================

    public int getBasicProblemsSolved(String username) {

        return (int) getSolvedProblems(username)
                .stream()
                .filter(key ->
                        !key.startsWith("array-")
                                && !key.startsWith("string-")
                )
                .count();
    }

    // ================= ARRAY PROBLEMS =================

    public int getArrayProblemsSolved(String username) {

        return (int) getSolvedProblems(username)
                .stream()
                .filter(key ->
                        key.startsWith("array-")
                )
                .count();
    }

    // ================= STRING PROBLEMS =================

    public int getStringProblemsSolved(String username) {

        return (int) getSolvedProblems(username)
                .stream()
                .filter(key ->
                        key.startsWith("string-")
                )
                .count();
    }

    // ================= CURRENT STREAK =================

    public int getCurrentStreak(String username) {

        List<LocalDate> dates =
                userProgressRepository
                        .findByUserUsernameOrderBySolvedAtAsc(username)
                        .stream()
                        .map(UserProgress::getSolvedAt)
                        .distinct()
                        .sorted(Comparator.reverseOrder())
                        .toList();

        if (dates.isEmpty()) {
            return 0;
        }

        int streak = 1;

        for (int i = 1; i < dates.size(); i++) {

            LocalDate previousDate = dates.get(i - 1);
            LocalDate currentDate = dates.get(i);

            if (currentDate.equals(
                    previousDate.minusDays(1))) {

                streak++;

            } else {

                break;
            }
        }

        return streak;
    }
}
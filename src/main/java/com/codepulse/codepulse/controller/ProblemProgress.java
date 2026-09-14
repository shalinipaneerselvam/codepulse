package com.codepulse.codepulse.controller;

public class ProblemProgress {

    private int problemsSolved;

    public ProblemProgress() {
        this.problemsSolved = 0;
    }

    public int getProblemsSolved() {
        return problemsSolved;
    }

    public void setProblemsSolved(int problemsSolved) {
        this.problemsSolved = problemsSolved;
    }

    public void incrementProblemsSolved() {
        this.problemsSolved++;
    }
}
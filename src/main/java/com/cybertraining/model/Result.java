package com.cybertraining.model;

public class Result {

    private User user;
    private Course course;
    private int score;
    private long timestamp;

    public Result(User user, Course course, int score) {
        this.user = user;
        this.course = course;
        this.score = score;
        this.timestamp = System.currentTimeMillis();
    }

    public User getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public int getScore() {
        return score;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isPassed() {
        return score >= 60;
    }
}

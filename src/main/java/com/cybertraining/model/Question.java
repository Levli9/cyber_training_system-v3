package com.cybertraining.model;

public class Question {

    private String question;
    private String[] answers;
    private int correctIndex;

    public Question(String question, String[] answers, int correctIndex) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public boolean isCorrect(int index) {
        return index == correctIndex;
    }
}
package com.cybertraining.service;

import com.cybertraining.model.Question;

import java.util.List;

public class QuizService {

    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;

    public QuizService(List<Question> questions) {
        this.questions = questions;
    }

    public Question getCurrentQuestion() {
        return questions.get(currentIndex);
    }

    public boolean hasNextQuestion() {
        return currentIndex < questions.size() - 1;
    }

    public void nextQuestion() {
        if (hasNextQuestion()) {
            currentIndex++;
        }
    }

    public void submitAnswer(int selectedIndex) {

        Question q = questions.get(currentIndex);

        if (q.isCorrect(selectedIndex)) {
            score += 100 / questions.size();
        }
    }

    public int getScore() {
        return score;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}
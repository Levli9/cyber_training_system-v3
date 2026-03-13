package com.cybertraining.ui;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.*;
import com.cybertraining.service.QuizService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizFrame extends JFrame {

    private QuizService quiz;

    private JLabel question;
    private JRadioButton[] options;
    private ButtonGroup group;

    public QuizFrame(DatabaseManager db, User user){

        List<Question> list = db.getQuestionsForCourse(1);
        quiz = new QuizService(list);

        setTitle("מבחן הסמכה");
        setSize(900,600);
        setLocationRelativeTo(null);

        question = new JLabel();
        options = new JRadioButton[4];
        group = new ButtonGroup();

        JPanel panel = new JPanel(new GridLayout(6,1));

        panel.add(question);

        for(int i=0;i<4;i++){
            options[i]=new JRadioButton();
            group.add(options[i]);
            panel.add(options[i]);
        }

        JButton next = new JButton("שאלה הבאה");

        next.addActionListener(e->next());

        panel.add(next);

        add(panel);

        loadQuestion();
    }

    private void loadQuestion(){

        Question q = quiz.getCurrentQuestion();

        question.setText(q.getQuestion());

        String[] ans = q.getAnswers();

        for(int i=0;i<4;i++){
            options[i].setText(ans[i]);
        }
    }

    private void next(){

        int selected=-1;

        for(int i=0;i<4;i++){
            if(options[i].isSelected()) selected=i;
        }

        if(selected!=-1)
            quiz.submitAnswer(selected);

        if(quiz.hasNextQuestion()){
            quiz.nextQuestion();
            loadQuestion();
            group.clearSelection();
        }else{
            new ResultFrame(quiz.getScore()).setVisible(true);
            dispose();
        }
    }
}
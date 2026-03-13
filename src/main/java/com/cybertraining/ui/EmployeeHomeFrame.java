package com.cybertraining.ui;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;

import javax.swing.*;
import java.awt.*;

public class EmployeeHomeFrame extends JFrame {

    public EmployeeHomeFrame(DatabaseManager db, User user){

        setTitle("דף עובד");
        setSize(900,600);
        setLocationRelativeTo(null);

        GradientPanel bg = new GradientPanel(AppTheme.BG,AppTheme.BG2);
        bg.setLayout(new GridBagLayout());

        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("שלום "+user.getName());
        welcome.setForeground(AppTheme.TEXT);
        welcome.setFont(AppTheme.TITLE);

        JButton learn = AppTheme.primaryButton("מסלול למידה");
        JButton exam = AppTheme.secondaryButton("מבחן הסמכה");

        learn.addActionListener(e->{
            new LearningFrame(db,user).setVisible(true);
        });

        exam.addActionListener(e->{
            new QuizFrame(db,user).setVisible(true);
        });

        card.add(welcome);
        card.add(Box.createVerticalStrut(30));
        card.add(learn);
        card.add(Box.createVerticalStrut(20));
        card.add(exam);

        bg.add(card);
        add(bg);
    }
}
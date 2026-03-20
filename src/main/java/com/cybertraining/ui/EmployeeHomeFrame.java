package com.cybertraining.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;

public class EmployeeHomeFrame extends JFrame {

    public EmployeeHomeFrame(DatabaseManager db, User user){

        setTitle("דף עובד");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel bg = new GradientPanel(AppTheme.BG, AppTheme.BG2);
        bg.setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        topBar.setOpaque(false);
        JButton backButton = AppTheme.backButton("← התנתק וחזור");
        backButton.addActionListener(e -> {
            new WelcomeFrame(db).setVisible(true);
            dispose();
        });
        topBar.add(backButton);
        bg.add(topBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(480, 280));

        JLabel welcome = new JLabel("<html><center>שלום, <span style='color: #00E6FF;'><b>" + user.getName() + "</b></span> 👋</center></html>");
        welcome.setForeground(AppTheme.TEXT);
        welcome.setFont(AppTheme.TITLE);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("בחר אחת מהאפשרויות הבאות:");
        subtitle.setForeground(AppTheme.MUTED);
        subtitle.setFont(AppTheme.SUBTITLE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton learn = AppTheme.primaryButton("📚 מסלול למידה");
        JButton exam = AppTheme.secondaryButton("🎯 מבחן הסמכה");
        
        learn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exam.setAlignmentX(Component.CENTER_ALIGNMENT);
        learn.setMaximumSize(new Dimension(260, 44));
        exam.setMaximumSize(new Dimension(260, 44));

        learn.addActionListener(e -> {
            new LearningFrame(db, user).setVisible(true);
            dispose();
        });

        exam.addActionListener(e -> {
            new QuizFrame(db, user).setVisible(true);
            dispose();
        });

        card.add(Box.createVerticalStrut(20));
        card.add(welcome);
        card.add(Box.createVerticalStrut(10));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));
        card.add(learn);
        card.add(Box.createVerticalStrut(15));
        card.add(exam);

        centerPanel.add(card);
        bg.add(centerPanel, BorderLayout.CENTER);

        add(bg);
        AppTheme.applyRTL(this);
    }
}
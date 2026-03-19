package com.cybertraining.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.Question;
import com.cybertraining.model.User;
import com.cybertraining.service.QuizService;

public class QuizFrame extends JFrame {

    private QuizService quiz;
    private DatabaseManager db;
    private User user;

    private JLabel title;
    private JLabel categoryLabel;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;

    public QuizFrame(DatabaseManager db, User user){

        this.db = db;
        this.user = user;
        List<Question> list = db.getQuestionsForCourse(1);
        quiz = new QuizService(list);

        setTitle("מבחן הסמכה - אבטחת מידע");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel bg = new GradientPanel(AppTheme.BG, AppTheme.BG2);
        bg.setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        header.setOpaque(false);
        JButton backButton = AppTheme.backButton("← יציאה מהמבחן");
        backButton.addActionListener(e -> {
            new EmployeeHomeFrame(db, user).setVisible(true);
            dispose();
        });
        header.add(backButton);
        bg.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(750, 480));

        title = new JLabel("מבחן הסמכה");
        title.setFont(AppTheme.TITLE);
        title.setForeground(AppTheme.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        categoryLabel = new JLabel();
        categoryLabel.setFont(new Font("Avenir Next", Font.BOLD, 14));
        categoryLabel.setForeground(AppTheme.ACCENT);
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.ACCENT, 1, true),
            BorderFactory.createEmptyBorder(4, 12, 4, 12)
        ));

        questionLabel = new JLabel();
        questionLabel.setFont(AppTheme.SUBTITLE);
        questionLabel.setForeground(AppTheme.TEXT);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        options = new JRadioButton[4];
        group = new ButtonGroup();

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for(int i = 0; i < 4; i++){
            options[i] = new JRadioButton();
            options[i].setFont(AppTheme.TEXT_FONT);
            options[i].setForeground(AppTheme.TEXT);
            options[i].setOpaque(false);
            options[i].setFocusPainted(false);
            options[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            group.add(options[i]);
            optionsPanel.add(options[i]);
            optionsPanel.add(Box.createVerticalStrut(10));
        }

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        actionsPanel.setOpaque(false);

        JButton skipButton = AppTheme.secondaryButton("דלג על השאלה");
        skipButton.setPreferredSize(new Dimension(160, 44));
        skipButton.addActionListener(e -> skip());

        JButton nextButton = AppTheme.primaryButton("שאלה הבאה");
        nextButton.setPreferredSize(new Dimension(160, 44));
        nextButton.addActionListener(e -> next());

        actionsPanel.add(skipButton);
        actionsPanel.add(nextButton);

        card.add(Box.createVerticalStrut(10));
        card.add(title);
        card.add(Box.createVerticalStrut(15));
        card.add(categoryLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(questionLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(optionsPanel);
        card.add(Box.createVerticalStrut(15));
        card.add(actionsPanel);

        centerPanel.add(card);
        bg.add(centerPanel, BorderLayout.CENTER);

        add(bg);

        loadQuestion();
    }

    private void loadQuestion(){
        Question q = quiz.getCurrentQuestion();
        title.setText("שאלה " + (quiz.getCurrentIndex() + 1) + " מתוך " + quiz.getTotalQuestions());
        
        String cat = q.getCategory() != null ? q.getCategory() : "כללי";
        categoryLabel.setText("קטגוריה: " + cat);
        
        Color catColor = AppTheme.ACCENT;
        if (cat.contains("פישינג")) catColor = new Color(0, 230, 255); // Neon Cyan
        else if (cat.contains("הנדסה")) catColor = new Color(255, 150, 0); // Neon Orange
        else if (cat.contains("סיסמאות")) catColor = new Color(0, 255, 128); // Neon Green
        else if (cat.contains("פיזית")) catColor = new Color(190, 40, 255); // Neon Purple
        else if (cat.contains("זדוניות")) catColor = new Color(255, 42, 122); // Neon Pink
        else if (cat.contains("גלישה")) catColor = new Color(0, 255, 200); // Neon Teal
        else if (cat.contains("ניהול")) catColor = new Color(255, 255, 0); // Neon Yellow

        categoryLabel.setForeground(catColor);
        categoryLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(catColor, 2, true),
            BorderFactory.createEmptyBorder(6, 16, 6, 16)
        ));
        
        // Convert rgb to hex for html rendering of the text if needed, or just let it remain centered
        String hexColor = String.format("#%02x%02x%02x", catColor.getRed(), catColor.getGreen(), catColor.getBlue());
        questionLabel.setText("<html><div style='text-align: center; color: " + hexColor + ";'>" + q.getQuestion() + "</div></html>");
        
        String[] ans = q.getAnswers();
        for(int i = 0; i < 4; i++){
            options[i].setText(ans[i]);
            options[i].setForeground(AppTheme.TEXT); // Reset text color on skip/next
        }
    }

    private void skip(){
        if(quiz.hasNextQuestion()){
            quiz.nextQuestion();
            loadQuestion();
            group.clearSelection();
        } else {
            new ResultFrame(db, user, quiz.getScore()).setVisible(true);
            dispose();
        }
    }

    private void next(){
        int selected = -1;
        for(int i = 0; i < 4; i++){
            if(options[i].isSelected()){
                selected = i;
                break;
            }
        }

        if(selected == -1){
            JOptionPane.showMessageDialog(this, "אנא בחר תשובה או לחץ על 'דלג'");
            return;
        }

        quiz.submitAnswer(selected);

        if(quiz.hasNextQuestion()){
            quiz.nextQuestion();
            loadQuestion();
            group.clearSelection();
        } else {
            new ResultFrame(db, user, quiz.getScore()).setVisible(true);
            dispose();
        }
    }
}
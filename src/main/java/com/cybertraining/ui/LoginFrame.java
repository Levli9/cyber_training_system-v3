package com.cybertraining.ui;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private DatabaseManager db;
    public LoginFrame(DatabaseManager db, boolean managerMode){

        this.db = db;
        setTitle("כניסה למערכת");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel background =
                new GradientPanel(AppTheme.BG, AppTheme.BG2);

        background.setLayout(new GridBagLayout());

        JPanel card = AppTheme.cardPanel();
        card.setLayout(new GridLayout(4,2,10,10));

        JLabel userLabel = new JLabel("שם משתמש:");
        JLabel passLabel = new JLabel("סיסמה:");

        userLabel.setForeground(AppTheme.TEXT);
        passLabel.setForeground(AppTheme.TEXT);

        usernameField = AppTheme.styledTextField();
        passwordField = AppTheme.styledPasswordField();

        JButton loginButton =
                AppTheme.primaryButton("התחברות");

        JButton backButton =
                AppTheme.secondaryButton("חזרה");

        loginButton.addActionListener(e -> login());

        backButton.addActionListener(e -> {

            new WelcomeFrame(db).setVisible(true);
            dispose();

        });

        card.add(userLabel);
        card.add(usernameField);

        card.add(passLabel);
        card.add(passwordField);

        card.add(backButton);
        card.add(loginButton);

        background.add(card);

        add(background);
    }

    private void login(){

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = db.authenticate(username,password);

        if(user == null){

            JOptionPane.showMessageDialog(
                    this,
                    "שם משתמש או סיסמה שגויים"
            );

            return;
        }

        if(user.isManager()){

            new ManagerDashboardFrame(db,user)
                    .setVisible(true);

        } else {

            new EmployeeHomeFrame(db,user)
                    .setVisible(true);
        }

        dispose();
    }
}
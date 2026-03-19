package com.cybertraining.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;

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
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(420, 320));

        JLabel title = new JLabel(managerMode ? "🔑 כניסת הנהלה" : "👨‍💻 כניסת עובדים");
        title.setFont(AppTheme.TITLE);
        title.setForeground(AppTheme.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userLabel = new JLabel("שם משתמש:");
        JLabel passLabel = new JLabel("סיסמה:");

        userLabel.setForeground(AppTheme.TEXT);
        passLabel.setForeground(AppTheme.TEXT);
        userLabel.setFont(AppTheme.TEXT_FONT);
        passLabel.setFont(AppTheme.TEXT_FONT);

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

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(6, 6, 6, 6);
        gc.anchor = GridBagConstraints.LINE_START;
        form.add(userLabel, gc);

        gc.gridx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        form.add(usernameField, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 0.0;
        gc.fill = GridBagConstraints.NONE;
        form.add(passLabel, gc);

        gc.gridx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        form.add(passwordField, gc);

        JPanel actions = new JPanel(new GridLayout(1, 2, 12, 0));
        actions.setOpaque(false);
        actions.add(backButton);
        actions.add(loginButton);

        card.add(title);
        card.add(Box.createVerticalStrut(18));
        card.add(form);
        card.add(Box.createVerticalStrut(18));
        card.add(actions);

        background.add(card);

        add(background);

        getRootPane().setDefaultButton(loginButton);
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
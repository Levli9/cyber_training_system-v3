package com.cybertraining.ui;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.service.AuthenticationService;
import com.cybertraining.model.User;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private DatabaseManager db;
    private AuthenticationService authService;
    public LoginFrame(DatabaseManager db, boolean managerMode){

        this.db = db;
        this.authService = new AuthenticationService(db);
        setTitle("כניסה למערכת");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel background = new BackgroundPanel(AppTheme.DEFAULT_BACKGROUND_RESOURCE);
        background.setLayout(new GridBagLayout());
        background.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(420, 320));

        JLabel title = new JLabel(managerMode ? "🔑 כניסת מנהלים" : "👨‍💻 כניסת עובדים");
        title.setFont(AppTheme.TITLE);
        title.setForeground(AppTheme.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel userLabel = new JLabel("שם משתמש:");
        JLabel passLabel = new JLabel("סיסמה:");

        userLabel.setForeground(AppTheme.TEXT);
        passLabel.setForeground(AppTheme.TEXT);
        userLabel.setFont(AppTheme.TEXT_FONT);
        passLabel.setFont(AppTheme.TEXT_FONT);

        usernameField = AppTheme.createTextField();
        passwordField = AppTheme.createPasswordField();

        // Right-align input text and set RTL for Hebrew
        usernameField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        usernameField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        passwordField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        passwordField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        // Show / hide password checkbox
        JCheckBox showPassword = new JCheckBox("הצג סיסמה");
        showPassword.setOpaque(false);
        showPassword.setForeground(AppTheme.MUTED);
        char defaultEcho = passwordField.getEchoChar();
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar(defaultEcho);
            }
        });

        JButton loginButton =
                AppTheme.primaryButton("התחברות");

        JButton backButton =
                AppTheme.secondaryButton("חזרה");

            JButton registerButton = AppTheme.primaryButton("הרשמה");
        registerButton.setMaximumSize(new Dimension(200, 44));
        registerButton.addActionListener(e -> {
            new RegistrationFrame(db).setVisible(true);
            dispose();
        });

        loginButton.addActionListener(e -> login());

        backButton.addActionListener(e -> {

            new WelcomeFrame(db).setVisible(true);
            dispose();

        });

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(6, 6, 6, 6);
        gc.anchor = GridBagConstraints.LINE_END;
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

        gc.gridx = 1; gc.gridy = 2; gc.fill = GridBagConstraints.NONE; gc.anchor = GridBagConstraints.LINE_END; form.add(showPassword, gc);

        JPanel actions = new JPanel(new GridLayout(1, 3, 12, 0));
        actions.setOpaque(false);
        actions.add(backButton);
        actions.add(registerButton);
        actions.add(loginButton);

        card.add(title);
        card.add(Box.createVerticalStrut(18));
        card.add(form);
        card.add(Box.createVerticalStrut(18));
        card.add(actions);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER; gbc.weightx = 1.0; gbc.weighty = 1.0;
        background.add(card, gbc);

        // add a drifting glow on the top-right to enhance visuals
        AnimatedGlow glow = new AnimatedGlow(AppTheme.ACCENT);
        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 1; g2.gridy = 0; g2.weightx = 0.0; g2.weighty = 0.0; g2.anchor = GridBagConstraints.NORTHEAST; g2.insets = new Insets(8,8,8,8);
        glow.setPreferredSize(new Dimension(160, 120));
        background.add(glow, g2);

        add(background);

        getRootPane().setDefaultButton(loginButton);
        // Ensure RTL orientation for Hebrew
        AppTheme.applyRTL(this);
    }

    public LoginFrame(DatabaseManager db) {
        // Placeholder implementation
        this.db = db;
    }

    private void login(){

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = authService.authenticate(username, password, null);

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
package com.cybertraining.ui;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.cybertraining.db.DatabaseManager;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame(DatabaseManager db) {

        setTitle("מערכת הדרכת סייבר");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel(AppTheme.DEFAULT_BACKGROUND_RESOURCE);
        bg.setLayout(new GridBagLayout());
        bg.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(520, 360));

        JLabel title = new JLabel("<html><center><span style='color: #00E6FF;'>⚡</span> מערכת הדרכה לאבטחת מידע <span style='color: #00E6FF;'>⚡</span></center></html>");
        title.setFont(AppTheme.TITLE);
        title.setForeground(AppTheme.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel sub = new JLabel("פלטפורמת למידה והסמכה חכמה לעובדים 🚀");
        sub.setFont(AppTheme.SUBTITLE);
        sub.setForeground(AppTheme.MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setHorizontalAlignment(SwingConstants.CENTER);

        // Buttons according to new flow: Register / Login
        JButton register = AppTheme.primaryButton("הרשמה");
        JButton login = AppTheme.primaryButton("התחברות");

        register.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setMaximumSize(new Dimension(260, 56));
        login.setMaximumSize(new Dimension(260, 56));

        register.addActionListener(e -> {
            new RegistrationFrame(db).setVisible(true);
            dispose();
        });

        login.addActionListener(e -> {
            new RoleSelectionScreen(db).setVisible(true);
            dispose();
        });

        card.add(title);
        card.add(Box.createVerticalStrut(12));
        card.add(sub);
        card.add(Box.createVerticalStrut(24));
        card.add(register);
        card.add(Box.createVerticalStrut(12));
        card.add(login);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER; gbc.weightx = 1.0; gbc.weighty = 1.0;
        bg.add(card, gbc);
        setContentPane(bg);
        // Apply RTL to all components to ensure Hebrew aligns correctly
        AppTheme.applyRTL(this);
    }
}
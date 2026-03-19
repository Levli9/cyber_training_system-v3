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

public class ManagerDashboardFrame extends JFrame {

    public ManagerDashboardFrame(DatabaseManager db, User manager){

        setTitle("דשבורד מנהל");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel bg = new GradientPanel(AppTheme.BG, AppTheme.BG2);
        bg.setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        header.setOpaque(false);
        JButton backButton = AppTheme.backButton("← התנתק וחזור");
        backButton.addActionListener(e -> {
            new WelcomeFrame(db).setVisible(true);
            dispose();
        });
        header.add(backButton);
        bg.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(500, 300));

        JLabel title = new JLabel("<html><center>דשבורד מנהל 📊 <br><span style='color: #00E6FF; font-size:16px;'>סטטיסטיקות ומעקב</span></center></html>");
        title.setForeground(AppTheme.TEXT);
        title.setFont(AppTheme.TITLE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("כאן יוצגו נתוני ההדרכות של העובדים... 🔒");
        subtitle.setForeground(AppTheme.MUTED);
        subtitle.setFont(AppTheme.SUBTITLE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(60));
        card.add(title);
        card.add(Box.createVerticalStrut(20));
        card.add(subtitle);

        centerPanel.add(card);
        bg.add(centerPanel, BorderLayout.CENTER);

        add(bg);
    }
}
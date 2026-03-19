package com.cybertraining.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

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

        GradientPanel bg = new GradientPanel(AppTheme.BG,AppTheme.BG2);
        bg.setLayout(new GridBagLayout());

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

        JButton employee = AppTheme.primaryButton("כניסת עובד");
        JButton manager = AppTheme.secondaryButton("כניסת מנהל");

        employee.setAlignmentX(Component.CENTER_ALIGNMENT);
        manager.setAlignmentX(Component.CENTER_ALIGNMENT);
        employee.setMaximumSize(new Dimension(260, 44));
        manager.setMaximumSize(new Dimension(260, 44));

        employee.addActionListener(e->{
            new LoginFrame(db,false).setVisible(true);
            dispose();
        });

        manager.addActionListener(e->{
            new LoginFrame(db,true).setVisible(true);
            dispose();
        });

        card.add(title);
        card.add(Box.createVerticalStrut(16));
        card.add(sub);
        card.add(Box.createVerticalStrut(32));
        card.add(employee);
        card.add(Box.createVerticalStrut(16));
        card.add(manager);

        bg.add(card);
        add(bg);
    }
}
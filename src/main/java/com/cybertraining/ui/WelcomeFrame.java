package com.cybertraining.ui;

import com.cybertraining.db.DatabaseManager;

import javax.swing.*;
import java.awt.*;

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

        JLabel title = new JLabel("מערכת הדרכה לאבטחת מידע בארגון");
        title.setFont(AppTheme.TITLE);
        title.setForeground(AppTheme.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("פלטפורמת למידה והסמכה לעובדים");
        sub.setFont(AppTheme.SUBTITLE);
        sub.setForeground(AppTheme.MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton employee = AppTheme.primaryButton("כניסת עובד");
        JButton manager = AppTheme.secondaryButton("כניסת מנהל");

        employee.setAlignmentX(Component.CENTER_ALIGNMENT);
        manager.setAlignmentX(Component.CENTER_ALIGNMENT);

        employee.addActionListener(e->{
            new LoginFrame(db,false).setVisible(true);
            dispose();
        });

        manager.addActionListener(e->{
            new LoginFrame(db,true).setVisible(true);
            dispose();
        });

        card.add(title);
        card.add(Box.createVerticalStrut(20));
        card.add(sub);
        card.add(Box.createVerticalStrut(40));
        card.add(employee);
        card.add(Box.createVerticalStrut(20));
        card.add(manager);

        bg.add(card);
        add(bg);
    }
}
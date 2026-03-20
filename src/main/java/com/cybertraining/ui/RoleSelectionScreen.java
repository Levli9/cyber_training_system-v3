package com.cybertraining.ui;

import com.cybertraining.db.DatabaseManager;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionScreen extends JFrame {

    private final DatabaseManager db;

    public RoleSelectionScreen(DatabaseManager db) {
        super("בחירת כניסה");
        this.db = db;
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        BackgroundPanel background = new BackgroundPanel(AppTheme.DEFAULT_BACKGROUND_RESOURCE);
        background.setLayout(new GridBagLayout());
        background.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JPanel card = AppTheme.cardPanel();
        card.setPreferredSize(new Dimension(520, 260));
        card.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("ברוכים הבאים למערכת ההכשרה");
        title.setFont(AppTheme.TITLE);
        title.setForeground(AppTheme.TEXT);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        JButton emp = AppTheme.primaryButton("כניסת עובדים");
        emp.setPreferredSize(new Dimension(220, 56));
        emp.addActionListener(e -> openLogin(false));

        gbc.gridx = 1;
        JButton mgr = AppTheme.primaryButton("כניסת מנהלים");
        mgr.setPreferredSize(new Dimension(220, 56));
        mgr.addActionListener(e -> openLogin(true));

        gbc.gridx = 0; gbc.gridy = 2; card.add(emp, gbc);
        gbc.gridx = 1; card.add(mgr, gbc);

        background.add(card);
        add(background, BorderLayout.CENTER);
        // Apply RTL to ensure Hebrew layout
        AppTheme.applyRTL(this);
    }

    private void openLogin(boolean managerMode) {
        // Use existing LoginFrame in ui package which supports managerMode
        LoginFrame lf = new LoginFrame(db, managerMode);
        lf.setVisible(true);
        this.dispose();
    }
}

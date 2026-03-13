package com.cybertraining.ui;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;

import javax.swing.*;

public class ManagerDashboardFrame extends JFrame {

    public ManagerDashboardFrame(DatabaseManager db, User manager){

        setTitle("דשבורד מנהל");
        setSize(900,600);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("דשבורד מנהל - סטטיסטיקות עובדים");

        label.setHorizontalAlignment(SwingConstants.CENTER);

        add(label);
    }
}
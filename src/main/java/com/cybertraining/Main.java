package com.cybertraining;

import javax.swing.SwingUtilities;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.ui.LoginFrame;

public class Main {

    public static void main(String[] args) {

        DatabaseManager databaseManager = new DatabaseManager();

        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame = new LoginFrame(databaseManager, false);
            loginFrame.setVisible(true);

        });
    }
}
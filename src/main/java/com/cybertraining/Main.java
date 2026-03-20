package com.cybertraining;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.service.AuthenticationService;
import com.cybertraining.ui.WelcomeFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            DatabaseManager databaseManager = new DatabaseManager();
            AuthenticationService auth = new AuthenticationService(databaseManager);

            // ensure at least one manager exists for first-run convenience
            if (databaseManager.getUserCount() == 0) {
                auth.register("manager", "manager123", "מנהל מערכת", "מנהל", "הנהלה");
            }

            WelcomeFrame welcome = new WelcomeFrame(databaseManager);
            welcome.setVisible(true);

        });
    }
}
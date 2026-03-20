package com.cybertraining.ui;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {

    private final DatabaseManager db;
    private final com.cybertraining.service.AuthenticationService authService;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JComboBox<String> roleCombo;

    public RegistrationFrame(DatabaseManager db) {
        this.db = db;
        this.authService = new com.cybertraining.service.AuthenticationService(db);

        AppTheme.applyFrameSettings(this, "הרשמה למערכת", 820, 560);

        BackgroundPanel root = new BackgroundPanel(AppTheme.DEFAULT_BACKGROUND_RESOURCE);
        root.setLayout(new GridBagLayout());
        root.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JPanel card = AppTheme.createCardPanel();
        card.setPreferredSize(new Dimension(700, 420));
        card.setLayout(new BorderLayout(0, 18));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel title = AppTheme.createTitle("הרשמה למערכת");
        title.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = AppTheme.createSubtitle("צור חשבון חדש כדי לגשת לתוכן ההדרכה");
        sub.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 16));
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(sub);

        // Build vertical list of rows so label appears on the right and field on the left
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        form.setBorder(new javax.swing.border.EmptyBorder(6, 6, 6, 6));

        usernameField = AppTheme.createTextField();
        passwordField = AppTheme.createPasswordField();
        confirmPasswordField = AppTheme.createPasswordField();
        fullNameField = AppTheme.createTextField();

        // Ensure RTL typing, right-aligned text and consistent sizes for Hebrew
        Dimension inputSize = new Dimension(150, 32); // increased height to avoid clipped text
        java.awt.Font fieldFont = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14);

        JTextField[] fields = new JTextField[] { usernameField, fullNameField };
        JPasswordField[] pfields = new JPasswordField[] { passwordField, confirmPasswordField };

        javax.swing.border.Border roundedPadding = new javax.swing.border.CompoundBorder(
            new RoundedBorder(26, AppTheme.BORDER, 1),
            new javax.swing.border.EmptyBorder(6, 10, 6, 10)
        );

        for (JTextField f : fields) {
            f.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            f.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            f.setPreferredSize(inputSize);
            f.setMaximumSize(inputSize);
            f.setFont(fieldFont);
            f.setBorder(roundedPadding);
            f.setBackground(AppTheme.INPUT_BG);
        }
        for (JPasswordField f : pfields) {
            f.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            f.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            f.setPreferredSize(inputSize);
            f.setMaximumSize(inputSize);
            f.setFont(fieldFont);
            f.setBorder(roundedPadding);
            f.setBackground(AppTheme.INPUT_BG);
        }

        roleCombo = new JComboBox<>(new String[]{"עובד", "מנהל"});
        roleCombo.setFont(fieldFont);
        roleCombo.setBackground(AppTheme.INPUT_BG);
        roleCombo.setForeground(Color.BLACK);
        roleCombo.setBorder(new RoundedBorder(26, AppTheme.BORDER, 1));
        roleCombo.setPreferredSize(new Dimension(120, 28));
        roleCombo.setMaximumSize(new Dimension(120, 28));
        roleCombo.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // helper to create a compact row where label is on the right and field is immediately to its left
        java.util.function.BiFunction<JLabel, java.awt.Component, JPanel> makeRow = (lbl, field) -> {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
            row.setOpaque(false);
            row.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            lbl.setHorizontalAlignment(SwingConstants.RIGHT);
            lbl.setFont(fieldFont);
            lbl.setPreferredSize(new Dimension(140, 20));
            // add label first (right), then field (to its left)
            row.add(lbl);
            row.add(field);
            row.setMaximumSize(new Dimension(Short.MAX_VALUE, 36));
            row.setPreferredSize(new Dimension(560, 36));
            return row;
        };

        form.add(makeRow.apply(AppTheme.createFieldLabel("שם משתמש:"), usernameField));
        form.add(Box.createVerticalStrut(12));
        form.add(makeRow.apply(AppTheme.createFieldLabel("סיסמה:"), passwordField));
        form.add(Box.createVerticalStrut(12));
        form.add(makeRow.apply(AppTheme.createFieldLabel("אימות סיסמה:"), confirmPasswordField));
        form.add(Box.createVerticalStrut(12));
        form.add(makeRow.apply(AppTheme.createFieldLabel("שם מלא:"), fullNameField));
        form.add(Box.createVerticalStrut(12));
        form.add(makeRow.apply(AppTheme.createFieldLabel("סוג משתמש:"), roleCombo));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 6));
        buttons.setOpaque(false);

        JButton cancel = AppTheme.secondaryButton("חזור");
        JButton register = AppTheme.primaryButton("הרשמה");

        cancel.addActionListener(e -> {
            dispose();
            new WelcomeFrame(db).setVisible(true);
        });

        register.addActionListener(e -> doRegister());

        buttons.add(cancel);
        buttons.add(register);

        card.add(header, BorderLayout.NORTH);
        // wrap form in a centered container so rows appear centered in the card
        // center the form both horizontally and vertically inside the card
        JPanel formWrapper = new JPanel(new GridBagLayout());
        formWrapper.setOpaque(false);
        GridBagConstraints fw = new GridBagConstraints();
        fw.gridx = 0; fw.gridy = 0; fw.weightx = 1.0; fw.weighty = 1.0;
        fw.anchor = GridBagConstraints.CENTER; fw.fill = GridBagConstraints.NONE;
        formWrapper.add(form, fw);
        card.add(formWrapper, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        GridBagConstraints rootGbc = new GridBagConstraints();
        rootGbc.gridx = 0; rootGbc.gridy = 0; rootGbc.fill = GridBagConstraints.NONE;
        rootGbc.anchor = GridBagConstraints.CENTER; rootGbc.weightx = 1.0; rootGbc.weighty = 1.0;
        root.add(card, rootGbc);
        // floating glow for visual interest
        AnimatedGlow glow = new AnimatedGlow(AppTheme.ACCENT);
        GridBagConstraints gg = new GridBagConstraints();
        gg.gridx = 1; gg.gridy = 0; gg.anchor = GridBagConstraints.NORTHEAST; gg.insets = new java.awt.Insets(12,12,12,12);
        glow.setPreferredSize(new Dimension(140, 110));
        root.add(glow, gg);
        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        setResizable(true);

        // Apply RTL recursively
        AppTheme.applyRTL(this);
    }

    private void doRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmPasswordField.getPassword());
        String fullName = fullNameField.getText().trim();
        String role = (String) roleCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "אנא מלא שם משתמש וסיסמה ואימות סיסמה", "שגיאה", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "הסיסמאות אינן תואמות", "שגיאה", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // department removed from UI; pass empty string to backend
        LoadingDialog ld = new LoadingDialog(SwingUtilities.getWindowAncestor(this), "הרשמה...\nאנא המתן");
        SwingWorker<User, Void> worker = new SwingWorker<>() {
            @Override
            protected User doInBackground() throws Exception {
                return authService.register(username, password, fullName, role, "");
            }

            @Override
            protected void done() {
                ld.dispose();
                try {
                    User u = get();
                    if (u == null) {
                        // determine whether username already exists or another DB error occurred
                        com.cybertraining.model.User existing = db.getUserByUsername(username);
                        if (existing != null) {
                            JOptionPane.showMessageDialog(RegistrationFrame.this, "שם משתמש קיים. בחר שם אחר.", "שגיאה", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(RegistrationFrame.this, "אירעה שגיאה בהרשמה (בדוק את הלוג בקונסול)", "שגיאה", JOptionPane.ERROR_MESSAGE);
                        }
                        return;
                    }
                    JOptionPane.showMessageDialog(RegistrationFrame.this, "נרשמת בהצלחה! ניתן להתחבר כעת.", "הצלחה", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginFrame(db,false).setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RegistrationFrame.this, "אירעה שגיאה במהלך ההרשמה", "שגיאה", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
        ld.setVisible(true);
    }
}

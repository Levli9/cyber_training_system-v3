package com.cybertraining.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;

public class ManagerDashboardFrame extends JFrame {
    private DatabaseManager db;
    private javax.swing.Timer refreshTimer;
    private JLabel avgValueLabel, completionValueLabel, highRiskValueLabel, monthlyValueLabel;
    private DefaultTableModel tableModel;

    /**
     * @param db
     * @param manager
     */
    public ManagerDashboardFrame(DatabaseManager db, User manager){

        this.db = db;

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

        // animated spinner to give dashboard a 'cyber' visual touch
        AnimatedSpinner spinner = new AnimatedSpinner(48);
        header.add(spinner);
        // moving glow for extra motion
        AnimatedGlow glow = new AnimatedGlow(AppTheme.SECONDARY_ACCENT);
        glow.setPreferredSize(new Dimension(120, 120));
        header.add(glow);
        bg.add(header, BorderLayout.NORTH);

        // Main split: left filters, right dashboard content
        JPanel main = new JPanel();
        main.setOpaque(false);
        main.setLayout(new BorderLayout(20, 0));

        // Left filters column
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(220, 0));
        left.setBorder(new EmptyBorder(10,10,10,10));

        left.add(AppTheme.createFieldLabel("מחלקה"));
        javax.swing.JComboBox<String> deptCombo = new javax.swing.JComboBox<>(new String[]{"כל המחלקות", "מכירות", "פיתוח", "משאבי אנוש"});
        deptCombo.setMaximumSize(new Dimension(200,28));
        deptCombo.setBackground(AppTheme.INPUT_BG);
        left.add(deptCombo);
        left.add(Box.createVerticalStrut(12));
        left.add(AppTheme.createFieldLabel("טווח תאריכים"));
        left.add(AppTheme.createTextField());
        left.add(Box.createVerticalStrut(12));
        left.add(AppTheme.createFieldLabel("יחידת הדרכה"));
        left.add(AppTheme.createTextField());

        main.add(left, BorderLayout.WEST);

        // Right content area (cards + charts + table)
        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        // Top KPI cards row
        JPanel cardsRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 8));
        cardsRow.setOpaque(false);
        cardsRow.setAlignmentX(Component.RIGHT_ALIGNMENT);
        // load live metrics into cards (labels kept as fields for refresh)
        cardsRow.add(dashboardCard("ציון ארגוני ממוצע", "--", null));
        cardsRow.add(dashboardCard("השלמת הדרכה", "--", null));
        cardsRow.add(dashboardCard("עובדים בסיכון גבוה", "--", null));
        cardsRow.add(dashboardCard("התקדמות חודשית", "--", null));
        right.add(cardsRow);

        // Middle charts row (pie + bar + trend)
        JPanel chartsRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        chartsRow.setOpaque(false);
        chartsRow.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // prepare data from results
        java.util.List<com.cybertraining.model.Result> allResults = db.getResults();
        // score distribution buckets
        java.util.Map<String,Integer> distribution = new java.util.LinkedHashMap<>();
        distribution.put("0-49", 0);
        distribution.put("50-69", 0);
        distribution.put("70-84", 0);
        distribution.put("85-100", 0);
        // attempts per user map
        java.util.Map<Integer, Integer> attempts = new java.util.HashMap<>();
        java.util.Map<Integer, Integer> latestScore = new java.util.HashMap<>();
        java.util.Map<Integer, Long> latestTs = new java.util.HashMap<>();

        for (com.cybertraining.model.Result r : allResults) {
            if (r.getCourse() == null || r.getCourse().getId() != 1) continue;
            int s = r.getScore();
            if (s < 50) distribution.put("0-49", distribution.get("0-49") + 1);
            else if (s < 70) distribution.put("50-69", distribution.get("50-69") + 1);
            else if (s < 85) distribution.put("70-84", distribution.get("70-84") + 1);
            else distribution.put("85-100", distribution.get("85-100") + 1);

            com.cybertraining.model.User u = r.getUser();
            if (u == null) continue;
            int uid = u.getId();
            attempts.put(uid, attempts.getOrDefault(uid, 0) + 1);
            if (!latestTs.containsKey(uid) || r.getTimestamp() > latestTs.get(uid)) {
                latestTs.put(uid, r.getTimestamp());
                latestScore.put(uid, s);
            }
        }


        SimpleChartPanel pie = new SimpleChartPanel(SimpleChartPanel.Type.PIE, distribution);
        pie.setPreferredSize(new Dimension(420, 240));
        SimpleChartPanel bar = new SimpleChartPanel(SimpleChartPanel.Type.BAR, distribution);
        bar.setPreferredSize(new Dimension(420, 240));
        chartsRow.add(bar);
        chartsRow.add(pie);

        // small trend placeholder (reuse chartPlaceholder area)
        chartsRow.add(chartPlaceholder("מגמת ביצועים ארגונית", 240, 200));
        right.add(chartsRow);

        // Heatmap / summary row
        JPanel heatRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        heatRow.setOpaque(false);
        heatRow.setAlignmentX(Component.RIGHT_ALIGNMENT);
        heatRow.add(chartPlaceholder("חוזקות וחולשות לפי נושא", 960, 120));
        right.add(heatRow);

        // Bottom area: employees list with attempts and recent results
        right.add(Box.createVerticalStrut(12));
        JPanel tableCard = AppTheme.cardPanel();
        tableCard.setLayout(new BoxLayout(tableCard, BoxLayout.Y_AXIS));
        tableCard.setPreferredSize(new Dimension(960, 260));
        tableCard.add(AppTheme.createSubtitle("פעולות נדרשות: עובדים וסטטוס הדרכה"));

        String[] cols = new String[]{"שם", "מחלקה", "ניסיונות", "ציון אחרון", "סטטוס הדרכה", "המלצה", "זמן אחרון"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        // Build rows from attempts map
        java.util.List<Integer> uids = new java.util.ArrayList<>(attempts.keySet());
        // sort by attempts desc
        uids.sort((a,b) -> Integer.compare(attempts.get(b), attempts.get(a)));
        for (Integer uid : uids) {
            com.cybertraining.model.User u = db.getUserById(uid);
            String name = u != null ? u.getName() : "(משתמש)";
            String dept = u != null ? u.getDepartment() : "";
            int att = attempts.get(uid);
            int sc = latestScore.getOrDefault(uid, 0);
            long ts = latestTs.getOrDefault(uid, 0L);
            String status = sc >= 70 ? "עובר" : "זקוק לשיפור";
            String rec = sc >= 85 ? "אין צורך" : (sc >= 70 ? "חיזוק קצר" : "הדרכה מחודשת");
            String ttxt = ts == 0 ? "-" : new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date(ts*1000));
            tableModel.addRow(new Object[]{name, dept, att, sc, status, rec, ttxt});
        }

        JTable table = new JTable(tableModel);
        table.setFont(AppTheme.TEXT_FONT);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(AppTheme.INPUT_BG);
        table.getTableHeader().setFont(AppTheme.SMALL_FONT);

        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(java.awt.Color.WHITE);
        tableCard.add(sp);
        right.add(tableCard);

        // start auto-refresh timer (every 3 seconds)
        refreshTimer = new javax.swing.Timer(3000, e -> refreshMetrics());
        refreshTimer.start();

        main.add(right, BorderLayout.CENTER);

        bg.add(main, BorderLayout.CENTER);

        add(bg);
        AppTheme.applyRTL(this);
    }

    private void refreshMetrics() {
        // update KPI labels
        double avg = db.getAverageScoreForCourse(1);
        int completion = db.getCompletionRateForCourse(1);
        int highRisk = db.countHighRiskEmployees(1, 50);
        int monthly = db.getMonthlyProgressPercent(1);
        if (avgValueLabel != null) avgValueLabel.setText(String.format("%d%%", (int)Math.round(avg)));
        if (completionValueLabel != null) completionValueLabel.setText(String.format("%d%%", completion));
        if (highRiskValueLabel != null) highRiskValueLabel.setText(String.valueOf(highRisk));
        if (monthlyValueLabel != null) monthlyValueLabel.setText((monthly >= 0 ? "+" : "") + monthly + "%");

        // refresh table rows
        java.util.List<com.cybertraining.model.Result> allResults = db.getResults();
        java.util.Map<Integer, Integer> attempts = new java.util.HashMap<>();
        java.util.Map<Integer, Integer> latestScore = new java.util.HashMap<>();
        java.util.Map<Integer, Long> latestTs = new java.util.HashMap<>();
        for (com.cybertraining.model.Result r : allResults) {
            if (r.getCourse() == null || r.getCourse().getId() != 1) continue;
            com.cybertraining.model.User u = r.getUser();
            if (u == null) continue;
            int uid = u.getId();
            attempts.put(uid, attempts.getOrDefault(uid, 0) + 1);
            if (!latestTs.containsKey(uid) || r.getTimestamp() > latestTs.get(uid)) {
                latestTs.put(uid, r.getTimestamp());
                latestScore.put(uid, r.getScore());
            }
        }

        // rebuild table model
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        java.util.List<Integer> uids = new java.util.ArrayList<>(attempts.keySet());
        uids.sort((a,b) -> Integer.compare(attempts.get(b), attempts.get(a)));
        for (Integer uid : uids) {
            com.cybertraining.model.User u = db.getUserById(uid);
            String name = u != null ? u.getName() : "(משתמש)";
            String dept = u != null ? u.getDepartment() : "";
            int att = attempts.get(uid);
            int sc = latestScore.getOrDefault(uid, 0);
            long ts = latestTs.getOrDefault(uid, 0L);
            String ttxt = ts == 0 ? "-" : new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date(ts*1000));
            tableModel.addRow(new Object[]{name, dept, att, sc, ttxt});
        }
    }

    @Override
    public void dispose() {
        if (refreshTimer != null && refreshTimer.isRunning()) refreshTimer.stop();
        super.dispose();
    }

    private JPanel dashboardCard(String title, String mainValue, String small) {
        JPanel p = AppTheme.cardPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(200, 90));
        JLabel t = AppTheme.createFieldLabel(title);
        t.setForeground(AppTheme.MUTED);
        t.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JLabel v = new JLabel(mainValue);
        v.setForeground(AppTheme.TEXT);
        v.setFont(AppTheme.TITLE);
        v.setAlignmentX(Component.RIGHT_ALIGNMENT);
        // keep references for live updates
        switch (title) {
            case "ציון ארגוני ממוצע": avgValueLabel = v; break;
            case "השלמת הדרכה": completionValueLabel = v; break;
            case "עובדים בסיכון גבוה": highRiskValueLabel = v; break;
            case "התקדמות חודשית": monthlyValueLabel = v; break;
        }
        p.add(Box.createVerticalStrut(8));
        p.add(t);
        p.add(Box.createVerticalStrut(6));
        p.add(v);
        if (small != null) {
            JLabel s = AppTheme.createFieldLabel(small);
            s.setForeground(AppTheme.ACCENT);
            s.setAlignmentX(Component.RIGHT_ALIGNMENT);
            p.add(s);
        }
        return p;
    }

    private JPanel chartPlaceholder(String title, int w, int h) {
        JPanel p = AppTheme.cardPanel();
        p.setPreferredSize(new Dimension(w, h));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel t = AppTheme.createFieldLabel(title);
        t.setAlignmentX(Component.RIGHT_ALIGNMENT);
        p.add(t);
        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setPreferredSize(new Dimension(w - 20, h - 30));
        box.setMaximumSize(new Dimension(w - 20, h - 30));
        p.add(box);
        return p;
    }

    
}
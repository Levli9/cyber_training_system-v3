package com.cybertraining.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class AppTheme {

    public static final Font TITLE = new Font("Avenir Next", Font.BOLD, 30);
    public static final Font SUBTITLE = new Font("Avenir Next", Font.PLAIN, 18);
    public static final Font TEXT_FONT = new Font("Avenir Next", Font.PLAIN, 15);

    public static final Color BG = new Color(13, 10, 25); // Deep dark purple/black
    public static final Color BG2 = new Color(38, 20, 60); // Vibrant dark violet

    public static final Color CARD = new Color(25, 20, 45); // Semi-dark violet card

    public static final Color TEXT = new Color(245, 245, 255);
    public static final Color MUTED = new Color(170, 160, 200);

    public static final Color ACCENT = new Color(0, 230, 255); // Neon Cyan
    public static final Color ACCENT_DARK = new Color(0, 170, 200);
    
    public static final Color SECONDARY_ACCENT = new Color(255, 42, 122); // Neon Pink

    public static JPanel cardPanel(){

        JPanel panel = new JPanel();

        panel.setBackground(CARD);
        panel.setOpaque(true);

        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(72, 78, 96), 1, true),
                new EmptyBorder(26, 28, 26, 28)
        ));

        return panel;
    }

    public static JTextField styledTextField(){

        JTextField f = new JTextField();

        f.setBackground(new Color(24, 27, 38));
        f.setForeground(TEXT);
        f.setCaretColor(TEXT);
        f.setSelectionColor(new Color(72, 120, 180));
        f.setSelectedTextColor(Color.WHITE);
        f.setFont(TEXT_FONT);

        f.setBorder(new CompoundBorder(
            new LineBorder(new Color(84, 90, 110), 1, true),
            new EmptyBorder(9, 12, 9, 12)
        ));

        return f;
    }

    public static JPasswordField styledPasswordField(){

        JPasswordField f = new JPasswordField();

        f.setBackground(new Color(24, 27, 38));
        f.setForeground(TEXT);
        f.setCaretColor(TEXT);
        f.setSelectionColor(new Color(72, 120, 180));
        f.setSelectedTextColor(Color.WHITE);
        f.setFont(TEXT_FONT);

        f.setBorder(new CompoundBorder(
            new LineBorder(new Color(84, 90, 110), 1, true),
            new EmptyBorder(9, 12, 9, 12)
        ));

        return f;
    }

    public static JButton backButton(String text){

        JButton btn = new JButton(text);

        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setForeground(MUTED);
        btn.setFont(new Font("Avenir Next", Font.BOLD, 14));
        btn.setBorder(new EmptyBorder(5, 10, 5, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Let hovered state get brighter
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(TEXT);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(MUTED);
            }
        });

        return btn;
    }

    public static JButton primaryButton(String text){

        JButton btn = new JButton(text);

        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);

        btn.setBackground(ACCENT);
        btn.setForeground(new Color(10, 10, 20)); // Dark text for neon cyan button

        btn.setFont(new Font("Avenir Next", Font.BOLD, 16));

        btn.setBorder(new CompoundBorder(
                new LineBorder(ACCENT_DARK, 1, true),
                new EmptyBorder(10, 22, 10, 22)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(50, 255, 255)); // Brighter neon hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(ACCENT);
            }
        });

        return btn;
    }

    public static JButton secondaryButton(String text){

        JButton btn = new JButton(text);

        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);

        btn.setBackground(new Color(60, 30, 90)); // Soft purple secondary
        btn.setForeground(TEXT);

        btn.setFont(new Font("Avenir Next", Font.BOLD, 15));

        btn.setBorder(new CompoundBorder(
                new LineBorder(SECONDARY_ACCENT, 1, true), // Neon Pink border
                new EmptyBorder(10, 22, 10, 22)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(SECONDARY_ACCENT); // Fill with neon pink on hover
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(60, 30, 90));
                btn.setForeground(TEXT);
            }
        });

        return btn;
    }
}
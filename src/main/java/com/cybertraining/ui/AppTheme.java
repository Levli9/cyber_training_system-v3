package com.cybertraining.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class AppTheme {

    public static final Font SUBTITLE = null;
    public static Color BG = new Color(18,18,24);
    public static Color BG2 = new Color(28,28,38);

    public static Color CARD = new Color(32,34,45);

    public static Color TEXT = new Color(230,230,235);
    public static Color MUTED = new Color(160,165,180);

    public static Font TITLE = new Font("Arial",Font.BOLD,28);
    public static Font TEXT_FONT = new Font("Arial",Font.PLAIN,16);

    public static JPanel cardPanel(){

        JPanel panel = new JPanel();

        panel.setBackground(CARD);

        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(60,65,80)),
                new EmptyBorder(25,25,25,25)
        ));

        return panel;
    }

    public static JTextField styledTextField(){

        JTextField f = new JTextField();

        f.setBackground(new Color(24,26,34));
        f.setForeground(TEXT);

        f.setBorder(new CompoundBorder(
                new LineBorder(new Color(70,75,90)),
                new EmptyBorder(8,10,8,10)
        ));

        return f;
    }

    public static JPasswordField styledPasswordField(){

        JPasswordField f = new JPasswordField();

        f.setBackground(new Color(24,26,34));
        f.setForeground(TEXT);

        f.setBorder(new CompoundBorder(
                new LineBorder(new Color(70,75,90)),
                new EmptyBorder(8,10,8,10)
        ));

        return f;
    }

    public static JButton primaryButton(String text){

        JButton btn = new JButton(text);

        btn.setFocusPainted(false);

        btn.setBackground(new Color(52,120,246));
        btn.setForeground(Color.WHITE);

        btn.setFont(new Font("Arial",Font.BOLD,16));

        btn.setBorder(new EmptyBorder(10,20,10,20));

        return btn;
    }

    public static JButton secondaryButton(String text){

        JButton btn = new JButton(text);

        btn.setFocusPainted(false);

        btn.setBackground(new Color(45,48,60));
        btn.setForeground(TEXT);

        btn.setFont(new Font("Arial",Font.BOLD,16));

        btn.setBorder(new EmptyBorder(10,20,10,20));

        return btn;
    }
}
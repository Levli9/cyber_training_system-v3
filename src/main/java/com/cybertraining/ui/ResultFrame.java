package com.cybertraining.ui;

import javax.swing.*;

public class ResultFrame extends JFrame {

    public ResultFrame(int score){

        setTitle("תוצאה");
        setSize(600,400);
        setLocationRelativeTo(null);

        JLabel label = new JLabel();

        if(score>=60)
            label.setText("עברתם את המבחן! ציון: "+score+" 🏆");
        else
            label.setText("לא עברתם. ציון: "+score+" נסו שוב");

        label.setHorizontalAlignment(SwingConstants.CENTER);

        add(label);
    }
}
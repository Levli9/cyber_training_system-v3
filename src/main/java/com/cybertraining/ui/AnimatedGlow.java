package com.cybertraining.ui;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple animated glow that drifts horizontally and vertically.
 * Add as a lightweight component over panels to give cyber glow motion.
 */
public class AnimatedGlow extends JComponent {

    private float xPos = 0.2f; // 0..1 relative
    private float yPos = 0.2f;
    private float vx = 0.0009f;
    private float vy = 0.0006f;
    private Color color;
    private Timer timer;

    public AnimatedGlow(Color color) {
        this.color = color;
        setOpaque(false);
        setPreferredSize(new Dimension(100,100));
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step();
            }
        });
        timer.start();
    }

    private void step() {
        xPos += vx;
        yPos += vy;
        if (xPos < 0.05f || xPos > 0.95f) vx = -vx;
        if (yPos < 0.05f || yPos > 0.95f) vy = -vy;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        float cx = xPos * w;
        float cy = yPos * h;

        float radius = Math.max(w, h) * 0.6f;

        Point2D center = new Point2D.Float(cx, cy);
        float[] dist = {0.0f, 1.0f};
        Color c0 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 180);
        Color c1 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
        RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, new Color[]{c0, c1});

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2.setPaint(p);
        g2.fillOval((int)(cx - radius), (int)(cy - radius), (int)(radius*2), (int)(radius*2));

        g2.dispose();
    }

    public void stop() {
        if (timer != null) timer.stop();
    }
}

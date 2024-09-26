package src;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class Animation extends JPanel {
    public static void main(String[] args) {
        // set up window and default settings for it
        JFrame window;
        window = new JFrame("Animation");
        final Animation panel = new Animation();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(
                (screen.width - window.getWidth()) / 2,
                (screen.height - window.getHeight()) / 2);
        // set up timer 
        Timer animationTimer;
        final long startTime = System.currentTimeMillis();
        animationTimer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                panel.frameNumber++;
                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });
        window.setVisible(true);
        animationTimer.start();
    }

    private int frameNumber;
    private long elapsedTimeMillis;

    private float pixelSize;

    // constructor
    public Animation() {
        setPreferredSize(new Dimension(800, 600));
    }

    // method to draw contents
    protected void paintComponent(Graphics g) {
        // graphics2D context to draw
        Graphics2D g2 = (Graphics2D)g.create();

        // antialiasing on
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        // fill in area with bue, since that will be our sky
        g2.setPaint(Color.BLUE);
        g2.fillRect(0,0,getWidth(),getHeight());

        drawScene(g2);
    }

    private void drawScene(Graphics2D g2) {
        // hello
    }
}
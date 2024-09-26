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
        setPreferredSize(new Dimension(845, 1000));
    }

    // method to draw contents
    protected void paintComponent(Graphics g) {
        // graphics2D context to draw
        Graphics2D g2 = (Graphics2D)g.create();

        // antialiasing on
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        // fill in area with blue, since that will be our sky
        g2.setPaint(Color.BLUE);
        g2.fillRect(0,0,getWidth(),getHeight());

        applyapplyWindowToViewportTransformation(g2, -15, 15, -15, 15, true);

        drawScene(g2);
    }

    private void drawScene(Graphics2D g2) {
        // hello
        drawTrees(g2);
    }

    private void drawTrees(Graphics2D g2) {
        // left side tree
        g2.setPaint(Color.GREEN);
        g2.fill(new Ellipse2D.Double(-16, -5, 7, 7));

        g2.setPaint(Color.RED);
        g2.fill(new Rectangle(-18, -20, 40, 20));

        // right side tree
        g2.setPaint(Color.GREEN);
        g2.fill(new Ellipse2D.Double(9, -5, 7, 7));
    }



    private void applyapplyWindowToViewportTransformation(Graphics2D g2,
                                                          double left, double right, double bottom, double top, 
                                                          boolean preserveAspect) {
        int width = getWidth();   // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double)height / width);
            double requestedAspect = Math.abs(( bottom-top ) / ( right-left ));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom-top) * (displayAspect/requestedAspect - 1);
                bottom += excess/2;
                top -= excess/2;
            }
            else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right-left) * (requestedAspect/displayAspect - 1);
                right += excess/2;
                left -= excess/2;
            }
        }
        g2.scale( width / (right-left), height / (bottom-top) );
        g2.translate( -left, -top );
        double pixelWidth = Math.abs(( right - left ) / width);
        double pixelHeight = Math.abs(( bottom - top ) / height);
        pixelSize = (float)Math.max(pixelWidth,pixelHeight);
        System.out.println("left: " + left + ", right: " + right + ", bottom: " + bottom + ", top: " + top);
    }
}
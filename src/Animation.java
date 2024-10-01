package src;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class Animation extends JPanel {

    private int frameNumber;
    private long elapsedTimeMillis;

    private int anTimer;

    private float pixelSize;
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
                panel.anTimer += 2;
                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });
        window.setVisible(true);
        animationTimer.start();
    }

    // constructor
    public Animation() {
        setPreferredSize(new Dimension(750, 750));
    }

    // method to draw contents
    protected void paintComponent(Graphics g) {
        // graphics2D context to draw
        Graphics2D g2 = (Graphics2D) g.create();

        // antialiasing on
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        applyapplyWindowToViewportTransformation(g2, -15, 15, -15, 15, true);

        drawBackground(g2);
        drawPicnic(g2);
        drawScene(g2);
        drawSeeSaw(g2);
    }

    private void drawSeeSaw(Graphics2D g2)
    {
        g2.setPaint(Color.GRAY);

        Polygon triangle = new Polygon();
        triangle.addPoint(-5, -10);
        triangle.addPoint(-7, -12);
        triangle.addPoint(-3, -12);

        g2.fill(triangle);

        double seesawX = -11;
        double seesawY = -10;
        double width = 12;
        double height = 0.3;
    


        Color purple = new Color(204, 108, 231);
        g2.setPaint(purple);

        int frame = (int) (Math.sin((Math.toRadians(anTimer))) * 20);
        g2.rotate(Math.toRadians(frame), seesawX + width / 2, seesawY + height / 2);

        Rectangle2D.Double seesaw = new Rectangle2D.Double(seesawX, seesawY, width, height);

        g2.fill(seesaw);
       
    }

    private void drawPicnic(Graphics2D g2)
    {
        AffineTransform original = g2.getTransform();
        Stroke originalStroke = g2.getStroke();



        Color tan = new Color(214,181,136);

        g2.setPaint(tan);

       
        Rectangle picnic = new Rectangle(13, -12, 4, 5);
        g2.shear(0.9, 0.0);
        g2.fill(picnic);   
        
        g2.setTransform(original);

        Color brown = new Color(101, 79, 70);

        Rectangle2D.Double suitcase = new Rectangle2D.Double(7.5, -7.5, 2, 1.3);
        Arc2D.Double suitcaseArc = new Arc2D.Double(7.8, -6.4, 1.3, 0.5, 0, -180, Arc2D.OPEN);

        //Rectangle suitcase = new Rectangle(7, -7, 2, 1);
        g2.setPaint(brown);
        g2.fill(suitcase);

        g2.setStroke(new BasicStroke(0.1f));

        g2.setPaint(brown);
        g2.draw(suitcaseArc);
        //g2.fill(suitcaseArc);

        


        g2.setColor(Color.RED);
        Ellipse2D apple = new Ellipse2D.Double(6, -8.5, 0.3, 0.3);
        g2.fill(apple);

        g2.setColor(Color.WHITE);
        Ellipse2D.Double personHead = new Ellipse2D.Double(6.7, -9.4, 1.5, 1.5);
        g2.fill(personHead);
        g2.setColor(Color.BLACK);
        g2.draw(personHead);

        Line2D.Double body = new Line2D.Double(7.1, -9.3, 6, -11);
        g2.draw(body);

        Line2D.Double arm = new Line2D.Double(6.9, -9.6, 5.8, -9.6);
        g2.draw(arm);

        Line2D.Double knee = new Line2D.Double(6, -11, 5.3, -10.9);
        g2.draw(knee);

        Line2D.Double leg = new Line2D.Double(5.3, -10.9, 5.2, -11.4);
        g2.draw(leg);

        g2.setStroke(originalStroke);
    }

    private void drawBackground(Graphics2D g2) 
    {
        Color sky = new Color(135, 206, 235);
        Color grass = new Color(52, 157, 10);

        //Painting the sky
        g2.setPaint(sky);
        g2.fillRect(-20, 0, getWidth(), getHeight());

        //Painting the grass
        g2.setPaint(grass);
        g2.fill(new Rectangle(-20, -20, 40, 20));

        //Painting the lake
        g2.setPaint(Color.blue);
        
        Path2D path = new Path2D.Double();

        path.moveTo(-15, -0.5);
        path.lineTo(15, -0.5);
        path.append(new java.awt.geom.Arc2D.Double(-10, -7, 20,6, 0, 180, java.awt.geom.Arc2D.OPEN), true);
        //path.moveTo(-15, 0);
        path.closePath();

        g2.fill(path);

        g2.draw(path);

        // draw sun - with fluctuating alpha value
        int alphaEffect = (int) (Math.cos((Math.toRadians(frameNumber))) * 15);
        //System.out.println(alphaEffect);
        g2.setPaint(new Color(255, 255, 0, 70 + alphaEffect));
        g2.fill(new Ellipse2D.Double(3.5, 10.25, 10, 10));
        g2.setPaint(new Color(255, 255, 0, 70 + alphaEffect));
        g2.fill(new Ellipse2D.Double(4, 11, 9, 9));
        g2.setPaint(new Color(255, 255, 0, 70 + alphaEffect));
        g2.fill(new Ellipse2D.Double(4.5, 11.75, 8, 8));
        g2.setPaint(new Color(255, 255, 0, 70 + alphaEffect));
        g2.fill(new Ellipse2D.Double(5, 12.5, 7, 7));
        g2.setPaint(new Color(255, 255, 0, 70 + alphaEffect));
        g2.fill(new Ellipse2D.Double(5.5, 13.25, 6, 6));
        

        // draw a dot at each coordinate
        // useful tool to flick on/off
        // g2.setPaint(Color.BLACK);
        // for (int i = 0; i <= 30; i++) {
        //     for (int j = 0; j <= 30; j++) {
        //         if (j == 15) {
        //             g2.setPaint(Color.GRAY);
        //             g2.fill(new Ellipse2D.Double(i-15, j-15, .5, .5));
        //         } else if (i == 15){
        //             g2.setPaint(Color.WHITE);
        //             g2.fill(new Ellipse2D.Double(i-15, j-15, .5, .5));
        //         } else {
        //             g2.setPaint(Color.BLACK);
        //             g2.fill(new Ellipse2D.Double(i-15, j-15, .5, .5));
        //         }
                
        //     }
        // }

    }

    private void drawScene(Graphics2D g2) {
        // hello
        drawTrees(g2);
    }

    private void drawTrees(Graphics2D g2) {
        // left side tree
        // tree trunk
        Path2D polyLeft = new Path2D.Double();
        polyLeft.moveTo(-12, -3);
        polyLeft.lineTo(-13, -12);
        polyLeft.lineTo(-13.5, -12.5);
        polyLeft.lineTo(-12.5, -12);
        polyLeft.lineTo(-11, -13);
        polyLeft.lineTo(-10, -12);
        polyLeft.lineTo(-8.5, -12.5);
        polyLeft.lineTo(-9, -12);
        polyLeft.lineTo(-10, -3);
        polyLeft.closePath();
        // make tree trunk brown
        g2.setPaint(new Color(150, 75, 0));
        g2.fill(polyLeft);

        // leaves
        g2.setPaint(Color.decode("#2A7E19"));
        g2.fill(new Ellipse2D.Double(-14.85, -5, 7.8, 7.8));
        
        // right side tree
        // tree trunk
        Path2D polyRight = new Path2D.Double();
        polyRight.moveTo(12, -3);
        polyRight.lineTo(13, -12);
        polyRight.lineTo(13.5, -12.5);
        polyRight.lineTo(12.5, -12);
        polyRight.lineTo(11, -13);
        polyRight.lineTo(10, -12);
        polyRight.lineTo(8.5, -12.5);
        polyRight.lineTo(9, -12);
        polyRight.lineTo(10, -3);
        polyRight.closePath();
        // make tree trunk brown
        g2.setPaint(new Color(150, 75, 0));
        g2.fill(polyRight);

        // leaves
        g2.setPaint(Color.decode("#2A7E19"));
        g2.fill(new Ellipse2D.Double(7.15, -5, 7.8, 7.8));

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
    }
}
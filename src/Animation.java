package src;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class Animation extends JPanel {

    private int frameNumber;

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
        animationTimer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                panel.frameNumber++;
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

        // set coordinate plane
        applyapplyWindowToViewportTransformation(g2, -15, 15, -15, 15, true);

        // draw different parts of scene
        drawBackground(g2);
        drawPicnic(g2);
        drawTrees(g2);
        drawBird(g2);
        drawSeeSaw(g2);
    }

    private void drawSeeSaw(Graphics2D g2)
    {
        // make seesaw itself
        Stroke originalStroke = g2.getStroke();
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

        // frame oscillates from -20 to 20
        int frame = (int) (Math.sin((Math.toRadians(frameNumber * 2.3))) * 20);

        // rotate the plane of g2 - this affects all future drawings which is how the people stay on seesaw
        g2.rotate(Math.toRadians(frame), seesawX + width / 2, seesawY + height / 2);

        Rectangle2D.Double seesaw = new Rectangle2D.Double(seesawX, seesawY, width, height);

        g2.fill(seesaw);

        // variable that will modify the knee bend of the people
        double modifiedFrame = frame / 30.0;
       
        // make left person on seesaw
        g2.setStroke(new BasicStroke(0.1f));
        g2.setColor(Color.WHITE);
        Ellipse2D.Double person1Head = new Ellipse2D.Double(-10, -8, 1.5, 1.5);
        g2.fill(person1Head);
        g2.setColor(Color.BLACK);
        g2.draw(person1Head);
        Line2D.Double firstBody = new Line2D.Double(-9.4, -8, -9.4, -10);
        g2.draw(firstBody);
        Line2D.Double firstArm1 = new Line2D.Double(-9.4, -9, -10.5, -8.5);
        g2.draw(firstArm1);
        Line2D.Double firstArm2 = new Line2D.Double(-9.4, -9, -8.3, -8.5);
        g2.draw(firstArm2);

        // draw knees / legs - moving when person is at bottom of seesaw
        // it snaps a little, giving the illusion of jumping
        // snapping happens because frames 1, 2, 3 are treated as if frame = 0
        if (frame > 4) {
            Line2D.Double firstKnee = new Line2D.Double(-9.4, -10, -9 + modifiedFrame, -10.9);
            g2.draw(firstKnee);
            Line2D.Double firstLeg = new Line2D.Double(-9 + modifiedFrame, -10.9, -9.5, -11.8 + modifiedFrame);
        g2.draw(firstLeg);
        } else {
            Line2D.Double firstKnee = new Line2D.Double(-9.4, -10, -9, -10.9);
            g2.draw(firstKnee);
            Line2D.Double firstLeg = new Line2D.Double(-9, -10.9, -9.5, -11.8);
            g2.draw(firstLeg);
        }

        // draw second person
        g2.setStroke(new BasicStroke(0.1f));
        g2.setColor(Color.WHITE);
        Ellipse2D.Double person2Head = new Ellipse2D.Double(-2, -8, 1.5, 1.5);
        g2.fill(person2Head);
        g2.setColor(Color.BLACK);
        g2.draw(person2Head);
        Line2D.Double secondBody = new Line2D.Double(-1.4, -8, -1.4, -10);
        g2.draw(secondBody);
        Line2D.Double secondArm1 = new Line2D.Double(-1.4, -9, -2.5, -8.5);
        g2.draw(secondArm1);
        Line2D.Double secondArm2 = new Line2D.Double(-1.4, -9, -.3, -8.5);
        g2.draw(secondArm2);

        // draw knees / legs - moving when person is at bottom of seesaw
        // it snaps a little, giving the illusion of jumping
        // snapping happens because frames -1, -2, -3 are treated as if frame = 0
        if (frame < -4) {
            Line2D.Double secondKnee = new Line2D.Double(-1.4, -10, -1.8 + modifiedFrame, -10.9);
            g2.draw(secondKnee);
            Line2D.Double secondLeg = new Line2D.Double(-1.8 + modifiedFrame, -10.9, -1.5, -11.8 - modifiedFrame);
            g2.draw(secondLeg);
        } else {
            Line2D.Double secondKnee = new Line2D.Double(-1.4, -10, -1.8, -10.9);
            g2.draw(secondKnee);
            Line2D.Double secondLeg = new Line2D.Double(-1.8, -10.9, -1.5, -11.8);
            g2.draw(secondLeg);
        }
        
        g2.setStroke(originalStroke);
    }

    // method to draw the picnic
    private void drawPicnic(Graphics2D g2)
    {
        AffineTransform original = g2.getTransform();
        Stroke originalStroke = g2.getStroke();

        // draw blanket
        Color tan = new Color(214,181,136);

        g2.setPaint(tan);
       
        Rectangle picnic = new Rectangle(13, -12, 4, 5);
        g2.shear(0.9, 0.0);
        g2.fill(picnic);
        
        g2.setTransform(original);

        Color brown = new Color(101, 79, 70);

        // draw suitcase
        Rectangle2D.Double suitcase = new Rectangle2D.Double(7.5, -7.5, 2, 1.3);
        Arc2D.Double suitcaseArc = new Arc2D.Double(7.8, -6.4, 1.3, 0.5, 0, -180, Arc2D.OPEN);

        g2.setPaint(brown);
        g2.fill(suitcase);

        g2.setStroke(new BasicStroke(0.1f));

        g2.setPaint(brown);
        g2.draw(suitcaseArc);

        // draw apple
        g2.setColor(Color.RED);
        Ellipse2D apple = new Ellipse2D.Double(6, -8.5, 0.3, 0.3);
        g2.fill(apple);

        // draw person laying down
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

        // reset stroke size to typical
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
        path.closePath();
        g2.fill(path);
        g2.draw(path);

        // draw sun - with fluctuating alpha value
        int alphaEffect = (int) (Math.cos((Math.toRadians(frameNumber))) * 25);
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
    }

    private void drawBird(Graphics2D g2) {

        Stroke originalStroke = g2.getStroke();

        Path2D bird = new Path2D.Double();
        // value that the wings will flap with
        double flapValue = (Math.cos((Math.toRadians(frameNumber * 8)))) / 2;
        // value affected by the frame number of the movement of the bird - resets every 600 frames
        double frameMovement = (frameNumber % 600) / 40.0;
        // drawing the bird
        bird.moveTo(7 - frameMovement, 7);
        bird.curveTo(9 - frameMovement, 7 + flapValue, 7 - frameMovement, 7 + flapValue, 9 - frameMovement, 7);
        // bird.curveTo(8.8 - frameMovement, 7 + flapValue, 8.8 - frameMovement, 7 + flapValue, 9 - frameMovement, 7);
        g2.setPaint(Color.BLACK);
        g2.setStroke(new BasicStroke(0.1f));
        g2.draw(bird);
        g2.setStroke(originalStroke);
    }

    private void drawTrees(Graphics2D g2) {
        // left side tree
        // tree trunk
        Path2D leftTree = new Path2D.Double();
        leftTree.moveTo(-12, -3);
        leftTree.lineTo(-13, -12);
        leftTree.lineTo(-13.5, -12.5);
        leftTree.lineTo(-12.5, -12);
        leftTree.lineTo(-11, -13);
        leftTree.lineTo(-10, -12);
        leftTree.lineTo(-8.5, -12.5);
        leftTree.lineTo(-9, -12);
        leftTree.lineTo(-10, -3);
        leftTree.closePath();
        // make tree trunk brown
        g2.setPaint(new Color(150, 75, 0));
        g2.fill(leftTree);

        // leaves
        g2.setPaint(Color.decode("#2A7E19"));
        g2.fill(new Ellipse2D.Double(-14.85, -5, 7.8, 7.8));
        
        // right side tree
        // tree trunk
        Path2D rightTree = new Path2D.Double();
        rightTree.moveTo(12, -3);
        rightTree.lineTo(13, -12);
        rightTree.lineTo(13.5, -12.5);
        rightTree.lineTo(12.5, -12);
        rightTree.lineTo(11, -13);
        rightTree.lineTo(10, -12);
        rightTree.lineTo(8.5, -12.5);
        rightTree.lineTo(9, -12);
        rightTree.lineTo(10, -3);
        rightTree.closePath();
        // make tree trunk brown
        g2.setPaint(new Color(150, 75, 0));
        g2.fill(rightTree);

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
    }
}
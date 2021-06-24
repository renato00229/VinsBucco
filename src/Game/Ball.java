package Game;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball extends Rectangle implements Coordinate {
    public Random random;
    public double xVelocity;
    public double yVelocity;
    public int initialSpeed = 0;
    public int R, G, B;

    public Ball(int x, int y, int Ball_W, int Ball_H) {
        super(x, y, Ball_W, Ball_H);
        random = new Random();
        int random_XDir = random.nextInt(2);
        if (random_XDir == 0)
            random_XDir--;
        setXDirection(random_XDir * initialSpeed);
        int random_YDir = random.nextInt(2);
        if (random_YDir == 0)
            random_YDir--;
        setYDirection(random_YDir * initialSpeed);
        R = random.nextInt(255);
        G = random.nextInt(255);
        B = random.nextInt(255);

    }

    public void setXDirection(double xDir) {
        xVelocity = xDir;
    }

    public void setYDirection(double yDir) {
        yVelocity = yDir;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(R, G, B));
        Ellipse2D.Double ball = new Ellipse2D.Double(x, y, width, height);
        g2.fill(ball);
    }

    @Override
    public Point W() {
        return new Point(x, y + (height / 2));
    }

    @Override
    public Point E() {
        return new Point(x + width, y + (height / 2));
    }

    @Override
    public Point N() {
        return new Point(x + (width / 2), y);
    }

    @Override
    public Point S() {
        return new Point(x + (width / 2), y + height);
    }

}

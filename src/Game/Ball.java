package Game;

import Functions.Coordinate;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class Ball implements Coordinate {
    private static final double MAX_SPEED = 6;
    private final Random random = new Random();
    private final int R, G, B;
    private double x;
    private double y;
    private double width;
    private double height;
    public int initialSpeed = 0;
    private double xVelocity;
    private double yVelocity;

    public Ball(double x, double y, double Ball_W, double Ball_H) {
        this.setX(x);
        this.setY(y);
        this.setWidth(Ball_W);
        this.setHeight(Ball_H);
        if (initialSpeed != 0) this.randomSpeed();
        R = random.nextInt(255);
        G = random.nextInt(255);
        B = random.nextInt(255);
    }

    private void randomSpeed() {
        int random_XDir = random.nextInt(2);
        if (random_XDir == 0)
            random_XDir--;
        setXDirection(random_XDir * initialSpeed);
        int random_YDir = random.nextInt(2);
        if (random_YDir == 0)
            random_YDir--;
        setYDirection(random_YDir * initialSpeed);
    }

    public void setXDirection(double xDir) {
        setxVelocity(xDir);
    }

    public void setYDirection(double yDir) {
        setyVelocity(yDir);
    }

    public void move() {
        setxVelocity(Math.min(getxVelocity(), MAX_SPEED));
        setyVelocity(Math.min(getyVelocity(), MAX_SPEED));
        setX(getX() + getxVelocity());
        setY(getY() + getyVelocity());
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(R, G, B));
        Ellipse2D.Double ball = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        g2.fill(ball);
    }


    @Override
    public Point2D.Double center() {
        return new Point2D.Double(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
}

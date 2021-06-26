package Game;

import Functions.Coordinate;
import Functions.MovingObj;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

import static Game.GameBoard.MAX_SPEED;

public class Ball implements Coordinate, MovingObj {
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
        setXVelocity(random_XDir * initialSpeed);
        int random_YDir = random.nextInt(2);
        if (random_YDir == 0)
            random_YDir--;
        setYVelocity(random_YDir * initialSpeed);
    }


    public void move() {
        setXVelocity(Math.min(getXVelocity(), MAX_SPEED));
        setYVelocity(Math.min(getYVelocity(), MAX_SPEED));
        setX(getX() + getXVelocity());
        setY(getY() + getYVelocity());
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


    public double getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
}

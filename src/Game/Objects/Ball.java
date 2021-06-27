package Game.Objects;

import Game.Functions.Coordinate;
import Game.Functions.MovingObj;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

import static Game.GameBoard.MAX_SPEED;

public class Ball implements Coordinate, MovingObj {
    public static double initialSpeed = 0;
    private final Random random = new Random();
    private final Color ball;
    private final Color[] colors = new Color[]{
            new Color(255, 0, 0),
            new Color(255, 153, 18),
            new Color(255, 246, 50),
            new Color(126, 255, 90),
            new Color(70, 248, 234),
            new Color(18, 125, 255),
            new Color(170, 50, 255),
            new Color(255, 90, 247),

    };
    private final Ellipse2D.Double ballO, ballI;
    private double x;
    private double y;
    private double width;
    private double height;
    private double xVelocity;
    private double yVelocity;

    public Ball(double x, double y, double Ball_W, double Ball_H) {
        ballO = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        ballI = new Ellipse2D.Double(getX() + 3, getY() + 3, getWidth() - 6, getHeight() - 6);
        this.setX(x);
        this.setY(y);
        this.setWidth(Ball_W);
        this.setHeight(Ball_H);
        //if (initialSpeed != 0) this.randomSpeed();
        ball = colors[random.nextInt(colors.length)];
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
        g2.setStroke(new BasicStroke(1.f));
        g2.setColor(ball);
        g2.fill(ballO);
        g2.setColor(Color.black);
        g2.draw(ballO);
        g2.draw(ballI);
    }


    @Override
    public Point2D.Double center() {
        return new Point2D.Double(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        ballI.x = x + 2;
        ballO.x = x;
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        ballI.y = y + 2;
        ballO.y = y;
        this.y = y;
    }


    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        ballO.width = width;
        ballI.width = width - 4;
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        ballO.height = height;
        ballI.height = height - 4;
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

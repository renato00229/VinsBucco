package Game;

import Functions.Coordinate;
import Functions.MovingObj;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static Game.GameBoard.GOAL_HEIGHT;

public class Goal implements Coordinate, MovingObj {
    private final double width;
    private final double height;
    private double x;
    private double y;
    private double yVel;

    public Goal(double randomY, double gw, double speed) {
        this.x = gw - 10;
        this.y = randomY;
        this.width = 10;
        this.height = GOAL_HEIGHT;
        this.yVel = speed;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        Rectangle2D.Double paddle = new Rectangle2D.Double(x, y, width, height);
        g2.fill(paddle);
    }

    public void move() {
        setYVelocity(Math.min(getYVelocity(), 6));
        setY(getY() + getYVelocity());
    }

    public double getXVelocity() {
        return 0;
    }

    public void setXVelocity(double x) {
    }

    public double getYVelocity() {
        return yVel;
    }

    public void setYVelocity(double y) {
        this.yVel = y;
    }

    @Override
    public Point2D.Double center() {
        return new Point2D.Double(this.x + 5, this.y + (GOAL_HEIGHT / 2.));
    }

    @Override
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }
}

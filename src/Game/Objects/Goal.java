package Game.Objects;

import Game.Utils.MovingObj;
import Game.Utils.StaticObj;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static Game.GameBoard.GOAL_HEIGHT;
import static Game.GameBoard.MAX_SPEED;

/**
 *
 * the goal is represented by a yellow Rectangle
 */

public final class Goal implements StaticObj, MovingObj {
    private double x;
    private double y;
    private double yVel;
    private static Rectangle2D.Double paddle;

    public Goal(double randomY, double gw, double speed) {
        this.x = gw - 20;
        this.y = randomY;
        this.yVel = speed;
        paddle = new Rectangle2D.Double(x, y, 20, GOAL_HEIGHT);
    }

    public synchronized void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.ORANGE);
        g2.fill(paddle);
    }

    public synchronized void move() {
        setYVelocity(Math.min(getYVelocity(), MAX_SPEED));
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
        return new Point2D.Double(this.x + 10, this.y + (GOAL_HEIGHT / 2.));
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
        paddle.x = x;
        this.x = x;
    }

    @Override
    public void setY(double y) {
        paddle.y = y;
        this.y = y;
    }
}

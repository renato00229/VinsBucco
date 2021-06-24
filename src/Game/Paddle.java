package Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Paddle extends Rectangle implements MouseMotionListener, Coordinate {
    public int yVelocity, xVelocity;
    public int initialX, initialY;

    public Paddle(int x, int y, int Paddle_W, int Paddle_H) {
        super(x, y, Paddle_W, Paddle_H);
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
        xVelocity = 0;
        yVelocity = 0;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        Double paddle = new Double(x + 10, y + 10, width - 20, height - 20);
        g2.fill(paddle);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point p = e.getPoint();
        if (p.x >= x - 20 && p.x <= (x + width + 20) && p.y >= y - 20 && p.y <= (y + height + 20)) {
            setXDirection(p.x - initialX);
            setYDirection(p.y - initialY);
            move();
            initialX = p.x;
            initialY = p.y;
        }
    }


    private void setXDirection(int xDir) {
        xVelocity = xDir;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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


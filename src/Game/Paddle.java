package Game;

import Functions.Coordinate;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Paddle extends MouseAdapter implements MouseMotionListener, Coordinate {
    private final double width, height;
    private boolean drag;
    private double x;
    private double y;
    private Point p = new Point(-1, -1);

    public Paddle(int x, int y, int Paddle_W, int Paddle_H) {
        this.setX(x);
        this.setY(y);
        this.width = Paddle_W;
        this.height = Paddle_H;

    }

    public void move() {
        if (isDrag()) {
            setX(getP().x);
            setY(getP().y);
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        Ellipse2D.Double paddle = new Ellipse2D.Double(getX(), getY(), width, height);
        g2.fill(paddle);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setP(e.getPoint());
        if (getP().x >= getX() && getP().x <= (getX() + width) && getP().y >= getY() && getP().y <= (getY() + height)) {
            setDrag(true);
        }
    }

    @Override
    public Point2D.Double center() {
        return new Point2D.Double(getX() + (width / 2.), getY() + (height / 2.));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setDrag(false);
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
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

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }
}


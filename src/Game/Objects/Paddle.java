package Game.Objects;

import Game.Functions.Coordinate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Paddle extends MouseAdapter implements MouseMotionListener, Coordinate {
    private static BufferedImage paddle;
    private boolean drag;

    static {
        try {
            paddle = ImageIO.read(new File("resources/paddle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final int width, height;
    private Point mouseP = new Point(-1, -1);
    private int x;
    private int y;

    public Paddle(int x, int y, int Paddle_W, int Paddle_H) {
        this.setX(x);
        this.setY(y);
        this.width = Paddle_W;
        this.height = Paddle_H;
    }

    public void move() {
        if (isDrag()) {
            setX(getMouseP().x);
            setY(getMouseP().y);
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(paddle, x, y, x + width, y + height, 0, 0, paddle.getWidth(), paddle.getHeight(), null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setMouseP(e.getPoint());
        if (getMouseP().x >= getX() && getMouseP().x <= (getX() + width) && getMouseP().y >= getY() && getMouseP().y <= (getY() + height)) {
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
        this.x = (int) x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = (int) y;
    }

    public Point getMouseP() {
        return mouseP;
    }

    public void setMouseP(Point mouseP) {
        this.mouseP = mouseP;
    }
}


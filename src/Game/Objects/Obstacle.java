package Game.Objects;

import Game.Functions.Coordinate;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

import static Game.GameBoard.GAME_HEIGHT;
import static Game.GameBoard.GAME_WIDTH;

public class Obstacle implements Coordinate {

    private final double x, y;
    private final Ellipse2D.Double o;

    public Obstacle() {

        double p = new Random().nextInt(((int) (GAME_WIDTH / 2)) - 10);
        x = p + (GAME_WIDTH / 2);
        y = new Random().nextInt((int) GAME_HEIGHT - 10);
        o = new Ellipse2D.Double(x, y, 20, 20);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1.f));
        g2.setColor(Color.black);
        g2.fill(o);
    }

    @Override
    public Point2D.Double center() {
        return new Point2D.Double(x + 10, y + 10);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
    }
}

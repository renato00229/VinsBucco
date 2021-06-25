package Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static Game.GameBoard.GOAL_HEIGHT;

public class Goal {
    private final double x, y, width, height;

    public Goal(double randomY, double gw) {
        this.x = gw - 10;
        this.y = randomY;
        this.width = 10;
        this.height = GOAL_HEIGHT;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        Rectangle2D.Double paddle = new Rectangle2D.Double(x, y, width, height);
        g2.fill(paddle);
    }

    public double getY() {
        return y;
    }
}

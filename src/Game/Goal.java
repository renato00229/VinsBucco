package Game;

import java.awt.*;

public class Goal extends Rectangle {

    public Goal(int randomY, int gw) {
        super(gw - 10, randomY, 10, 80);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        Double paddle = new Double(x, y, width, height);
        g2.fill(paddle);
    }
}

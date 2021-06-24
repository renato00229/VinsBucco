package Game;

import java.awt.*;
import java.awt.geom.Line2D;

public class Score extends Rectangle {
    public static int GAME_WIDTH;
    public static int GAME_HEIGHT;

    public Score(int w, int h) {
        GAME_WIDTH = w;
        GAME_HEIGHT = h;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.setFont(new Font("Consolas", Font.PLAIN, 15));
        Line2D.Double line = new Line2D.Double(GAME_WIDTH / 2., 0, GAME_WIDTH / 2., GAME_HEIGHT);
        g2.draw(line);
        g2.drawString(" press SPACE for a new ball ", (GAME_WIDTH - 300), 20);
        g2.setFont(new Font("Consolas", Font.PLAIN, 20));
    }


}


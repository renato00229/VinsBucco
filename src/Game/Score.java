package Game;

import java.awt.*;
import java.awt.geom.Line2D;

public class Score {
    static int tot_score = 0;
    private static double GAME_WIDTH;
    private static double GAME_HEIGHT;

    public Score(double w, double h) {
        GAME_WIDTH = w;
        GAME_HEIGHT = h;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.setFont(new Font("Consolas", Font.PLAIN, 15));
        Line2D.Double line = new Line2D.Double(GAME_WIDTH / 2., 0, GAME_WIDTH / 2., GAME_HEIGHT);
        g2.draw(line);
        g2.drawString(" press SPACE for a new ball ", (float) (GAME_WIDTH - 300), 20);
        g2.drawString(" press ENTER for a new goal ", (float) (GAME_WIDTH - 300), 35);
        g2.setFont(new Font("Consolas", Font.PLAIN, 30));
        g2.drawString(" SCORE :  " + tot_score, (float) (GAME_WIDTH / 2 - 100), 40);

    }


}


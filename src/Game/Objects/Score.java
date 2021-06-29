package Game.Objects;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import static Game.GameBoard.GAME_HEIGHT;
import static Game.GameBoard.GAME_WIDTH;


/**
 * Score final class
 *
 * @author Elena
 * @author Vincenzo
 *
 */


public final class Score {
    private static final Ellipse2D.Double center = new Ellipse2D.Double(GAME_WIDTH / 2 - 70, GAME_HEIGHT / 2 - 70, 140, 140);
    private static final Rectangle2D.Double L = new Rectangle2D.Double(2, 1, GAME_WIDTH / 2 - 5, GAME_HEIGHT - 5),
            R = new Rectangle2D.Double(GAME_WIDTH / 2 + 2, 1, GAME_WIDTH / 2 - 5, GAME_HEIGHT - 5);
    public static int SCORE = 0;

    /**
     * This method is useful to draw graphic objects
     *
     * draw the playground and enter the high score in the center
     */

    public synchronized void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(6.f));
        g2.draw(L);
        g2.setColor(Color.RED);
        g2.draw(R);
        g2.setStroke(new BasicStroke(1.f));
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Consolas", Font.PLAIN, 15));
        g2.draw(center);
        g2.drawString(" press SPACE for a new ball ", 100f, 20);
        g2.drawString(" press ENTER for a new goal ", 100f, 35);
        g2.setFont(new Font("Consolas", Font.PLAIN, 30));
        g2.drawString("SCORE :  " + SCORE, (float) (GAME_WIDTH / 2 - 100), 40);
    }


}
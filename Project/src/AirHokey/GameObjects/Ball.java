package AirHokey.GameObjects;

import AirHokey.Panel;

import java.awt.*;
import java.util.HashMap;

public class Ball extends AbstractGameObject {
    public Ball(Panel panel, HashMap<String, GameObject> hashgo, int w, int h, int x, int y, int speedX, int speedY) {
        this.pann = panel;
        this.hashgo = hashgo;
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillOval(x,y,w,h);
    }

    @Override
    public void updatevel() {
        x+=speedX;
        y+=speedY;
        x += speedX;
        y += speedY;

        // left wall
        if (x < 0) {
            speedX = -speedX;
            Goal p = ((Goal) hashgo.get("player_dx"));
            p.setScore(p.getScore() + 1);
        }

        // right wall
        if (x > pann.getWidth() - w) {
            speedX = -speedX;
            Goal p = ((Goal) hashgo.get("player_sx"));
            p.setScore(p.getScore() + 1);
        }

        // horizontal walls
        if (y < 0 || y > pann.getHeight() - w) {
            speedY = -speedY;
        }

        // players
        for (GameObject go : hashgo.values()) {
            AbstractGameObject gam = (AbstractGameObject) go;
            if (gam != this) {
                if (gam.getBounds().intersects(getBounds())) {
                    speedX = -speedX;
                }
            }
        }
    }
}


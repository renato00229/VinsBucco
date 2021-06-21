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
}

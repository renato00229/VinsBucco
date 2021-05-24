package AirHokey;

import java.awt.*;
import java.util.HashMap;

public class Disk extends AbstractGameObject {
    public Disk(Panel panel, HashMap<String, GameObject> hashgo,int w, int h, int x, int y,int speedX, int speedY) {
        this.pann = pann;
        this.hashgo = hashgo;
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.red);
        g.fillOval(x+2,y+2, w+2, h+2);
    }
}

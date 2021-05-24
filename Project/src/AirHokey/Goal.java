package AirHokey;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Goal extends AbstractGameObject{
    int score=0;
    public Goal(JPanel pann, HashMap<String, GameObject> hashgo,
                int w, int h, int x, int y,int speedX, int speedY) {
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
        g.setColor(Color.blue);
        g.fillRect(x, y,w ,h);
    }
}

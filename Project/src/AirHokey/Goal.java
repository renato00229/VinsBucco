package AirHokey;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Goal extends AbstractGameObject{
    int score=0;
    public Goal(JPanel pann, HashMap<String, GameObject> hashgo,
                int w, int h, int x, int y) {
        this.pann = pann;
        this.hashgo = hashgo;
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
    }
    public void paint(Graphics g)
    {
        g.setColor(Color.white);
        g.fillRect(x+10,y,w,h);
    }
}

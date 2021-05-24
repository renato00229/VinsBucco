package AirHokey;

import java.awt.*;
import java.util.HashMap;

public class Ball extends AbstractGameObject {
    public Ball(Panel panel, HashMap<String, GameObject> hashgo,int w, int h, int x, int y) {
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillOval(x,y,w,h);
    }
}

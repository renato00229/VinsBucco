package AirHokey;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Ball extends AbstractGameObject {
    int score = 0;

    public Ball(JPanel pann, HashMap<String, GameObject> go, int w, int h, int x, int y, int speedX, int speedY) {
        this.pann = pann;
        this.go= go;
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public void paint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillOval(x,y, 10, 20);
    }

    @Override
    public Rectangle getBounds() {
        return Rectangle();   }
}
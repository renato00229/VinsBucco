package AirHokey;

import java.awt.*;
public interface GameObject {

    void paint(Graphics g);

    Rectangle getBounds();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getSpeedX();

    void setSpeedX(int sx);

    int getSpeedY();

    void setSpeedY(int sy);
}

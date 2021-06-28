package Game.Utils;

import java.awt.geom.Point2D;

public interface StaticObj {
    Point2D.Double center();

    double getX();

    void setX(double x);

    double getY();

    void setY(double y);
}

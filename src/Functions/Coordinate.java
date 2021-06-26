package Functions;

import java.awt.geom.Point2D;

public interface Coordinate {
    Point2D.Double center();

    double getX();

    void setX(double x);

    double getY();

    void setY(double y);
}

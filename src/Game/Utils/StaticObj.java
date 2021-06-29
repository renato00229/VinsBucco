package Game.Utils;

import java.awt.geom.Point2D;



/**
 * StaticObj Interface which defines methods for static objects
 *
 * @author Elena
 * @author Vincenzo
 *
 */


public interface StaticObj {

    Point2D.Double center();

    /**
     * x-coordinate
     * @return double
     */
    double getX();

    /**
     * set the x-coordinate
     * @param x
     */
    void setX(double x);

    /**
     * y-coordinate
     * @return double
     */
    double getY();

    /**
     * set the y-coordinate
     * @param y
     */
    void setY(double y);
}

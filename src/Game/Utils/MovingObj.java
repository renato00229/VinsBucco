package Game.Utils;


/**
 * MovingObj Interface which defines methods for moving objects
 *
 * @author Elena
 * @author Vincenzo
 *
 */


public interface MovingObj {

    /**
     * velocity of the x-coordinate
     * @return double
     */
    double getXVelocity();

    /**
     * set the velocity of the x-coordinate
     * @param xv
     */
    void setXVelocity(double xv);

    /**
     * velocity of the y-coordinate
     * @return double
     */
    double getYVelocity();

    /**
     * set the velocity of the y-coordinate
     * @param yv
     */
    void setYVelocity(double yv);
}

package Game;

import Game.Objects.*;
import Game.Utils.MovingObj;
import Game.Utils.StaticObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static Game.Objects.Score.SCORE;
import static Game.Utils.Login.startingOb;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
 * Gameboard is the main body of the game's GUI. It extends JPanel and implements
 * listeners and runnable.
 * It gives the dimension to the general panel
 *
 * GameBoard is the class where the game is played and includes:
 * Paddle
 * Goal
 * Ball
 * Obstacle
 * Score
 *
 *
 *  * @author Elena
 *  * @author Vincenzo
 *  *
 */

public class GameBoard extends JPanel implements Runnable, KeyListener {
    public static final double GAME_WIDTH = 1000, GAME_HEIGHT = 600;
    private static final Dimension SCREEN = new Dimension((int) GAME_WIDTH, (int) GAME_HEIGHT);
    public static final int PADDLE_HEIGHT = 60,
            PADDLE_WIDTH = 60,
            PADDLE_RAD = 30,
            BALL_RAD = 15,
            GOAL_HEIGHT = 150,
            MAX_SPEED = 6;

    /**
     * Initial speed = 0
     */
    public static double GOAL_SPEED = 0;

    private final Score score;
    private final Paddle paddle = new Paddle(0, 200, PADDLE_WIDTH, PADDLE_HEIGHT);
    private final List<Obstacle> obstacles = new ArrayList<>();
    private Goal goal;
    private Ball ball;


    /**
     * This method returns the distance between paddle and ball
     *
     * @param paddle
     * @param ball
     * @return abs
     */

    private static double dist(StaticObj paddle, StaticObj ball) {
        Point2D.Double cb = ball.center(), cp = paddle.center();
        return abs((cb.x - cp.x) * (cb.x - cp.x) + (cb.y - cp.y) * (cb.y - cp.y));
    }

    /**
     * New score
     *
     */

    public GameBoard() {

        this.score = new Score();
    }


    /**
     *  this method checks collision through distance
     *
     * @param a1        object 1
     * @param a2        object 2
     * @param r2        radius
     * @return boolean
     */

    private static boolean collision(StaticObj a1, StaticObj a2, double r2) {
        return dist(a1, a2) <= (BALL_RAD + r2) * (BALL_RAD + r2);
    }


    /**
     *
     * This method generate a  new ball
     */

    public void newBall() {
        this.ball = new Ball((GAME_WIDTH / 2.) - BALL_RAD,
                (GAME_HEIGHT / 2.) - BALL_RAD, BALL_RAD * 2, BALL_RAD * 2);
    }

    /**
     * Start the threads
     *
     */

    public void start() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.scheduleWithFixedDelay(this, 30, 3, TimeUnit.MILLISECONDS);
    }

    @Override
    public synchronized void run() {
        this.move();
        this.checkHit(paddle, ball);
        for (Obstacle o : this.obstacles) this.checkHit(o, ball);
        this.checkCollision();
        this.repaint();
    }


    public synchronized void move() {
        this.ball.move();
        this.paddle.move();
        this.goal.move();
    }


    public synchronized void checkCollision() {
        this.ballEdges();
        this.paddleCollisions();
        this.goalEdges();
    }


    private synchronized void goalEdges() {
        if (this.goal.getY() <= 0) {
            this.goal.setYVelocity(abs(this.goal.getYVelocity()));
            this.goal.setYVelocity(this.goal.getYVelocity());
        }
        if (this.goal.getY() >= GAME_HEIGHT - GOAL_HEIGHT) {
            this.goal.setYVelocity(abs(this.goal.getYVelocity()));
            this.goal.setYVelocity(-this.goal.getYVelocity());
        }
    }


    private synchronized void ballEdges() {
        //bounce ball off top & bottom window edges
        if (this.ball.getY() <= 0) {
            this.ball.setYVelocity(abs(this.ball.getYVelocity())); //always positive
        } else if (this.ball.getY() >= GAME_HEIGHT - 30) {
            this.ball.setYVelocity(abs(this.ball.getYVelocity()) * -1);  //always negative
        }

        if (this.ball.getX() <= 0) {
            this.ball.setXVelocity(abs(this.ball.getXVelocity()));

        } else if (this.ball.getX() > GAME_WIDTH - BALL_RAD) {
            this.ball.setXVelocity(abs(this.ball.getXVelocity()) * -1);
        }

        if (this.ball.getX() >= GAME_WIDTH - 50) {  // 50 = (20 ,30)(goal width, 2*ball radius)
            if (this.ball.getY() >= this.goal.getY() - BALL_RAD && this.ball.getY() <= this.goal.getY() + GOAL_HEIGHT - BALL_RAD)
            {
                //increases score

                SCORE++;

                //increases goal's speed

                GOAL_SPEED += 0.5;

                this.newBall();
                this.newGoal();

                //add an obstacle

                if (SCORE % 5 == 0) {
                    obstacles.add(new Obstacle());
                }
            }
        }

    }


    /**
     * This method is concerned with drawing the graphical interface.
     * Draw the ball, the goal, the paddle and score
     *
     * @param graphics		A graphic object.
     */


    @Override
    public synchronized void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(217, 217, 217, 255));
        g.fill3DRect(0, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT, false);
        this.score.draw(g);
        this.ball.draw(g);
        this.goal.draw(g);
        this.paddle.draw(g);
        for (Obstacle o : this.obstacles)
            o.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    /**
     * This method is called every time the player presses a key on the keyboard.
     * This method is concerned with seeing if the key pressed is the "SPACE" key or "ENTER" key.
     * In this way the player decides to create a new ball or create a new goal.
     * @param	e		A KeyEvent
     */

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            newBall();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            newGoal();
        }
    }


    private synchronized void paddleCollisions() {
        if (this.paddle.getY() <= 0) {
            this.paddle.setY(0);
        }
        if (this.paddle.getY() >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            this.paddle.setY((GAME_HEIGHT - PADDLE_HEIGHT));
        }
        if (this.paddle.getX() <= 0) {
            this.paddle.setX(0);
        }
        if (this.paddle.getX() >= (GAME_WIDTH / 2 - PADDLE_WIDTH)) {
            this.paddle.setX((GAME_WIDTH / 2 - PADDLE_WIDTH));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public synchronized void checkHit(StaticObj static_obj, StaticObj moving_obj) {
        double r = (static_obj instanceof Paddle) ? PADDLE_RAD : 10;

        if (collision(static_obj, moving_obj, r)) {
            //static collisions (bodies can't exists inside each other
            double dist = sqrt(dist(static_obj, moving_obj));
            dist = dist == 0 ? 1 : dist;
            double overlap = (dist - BALL_RAD - r) * .5;
            static_obj.setX(static_obj.getX() + overlap * (static_obj.getX() - moving_obj.getX()) / dist);
            static_obj.setY(static_obj.getY() + overlap * (static_obj.getY() - moving_obj.getY()) / dist);
            moving_obj.setX(moving_obj.getX() - overlap * (moving_obj.getX() - static_obj.getX()) / dist);
            moving_obj.setY(moving_obj.getY() - overlap * (moving_obj.getY() - static_obj.getY()) / dist);
            ((MovingObj) moving_obj).setXVelocity(((MovingObj) moving_obj).getXVelocity() - overlap * (moving_obj.getX() - static_obj.getX()) * .05);
            ((MovingObj) moving_obj).setYVelocity(((MovingObj) moving_obj).getYVelocity() - overlap * (moving_obj.getY() - static_obj.getY()) * .05);
        }
    }

    /**
     * this method creates a new goal
     *
     */

    private void newGoal() {
        this.goal = new Goal(new Random().nextInt((int) GAME_HEIGHT - GOAL_HEIGHT), GAME_WIDTH, GOAL_SPEED);
    }

    /**
     * The game is launched
     *
     */

    public void init() {
        this.setMaximumSize(SCREEN);
        this.setMinimumSize(SCREEN);
        this.setPreferredSize(SCREEN);
        this.setSize(SCREEN);
        this.newBall();
        this.newGoal();
        this.addMouseMotionListener(this.paddle);
        this.addKeyListener(this);
        this.setFocusable(true);

        /**
         * This loop is to create the same number of obstacles as there where when you saved the
         * score.
         */

        for (int i = 0; i < startingOb; i++)
            obstacles.add(new Obstacle());
    }
}

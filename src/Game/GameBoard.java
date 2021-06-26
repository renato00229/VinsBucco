package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class GameBoard extends JPanel implements Runnable, KeyListener {
    public static final double GAME_WIDTH = 1000, GAME_HEIGHT = 600;
    public static final Dimension SCREEN = new Dimension((int) GAME_WIDTH, (int) GAME_HEIGHT);
    public static final int PADDLE_HEIGHT = 60,
            PADDLE_WIDTH = 60,
            PADDLE_RAD = 30,
            BALL_RAD = 15,
            GOAL_HEIGHT = 150;
    private final Score score;
    private static double GOAL_SPEED = 0;
    private Goal goal;
    private Ball ball;
    private final Paddle paddle = new Paddle(0, 200, PADDLE_WIDTH, PADDLE_HEIGHT);

    public void start() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.scheduleWithFixedDelay(this, 30, 1, TimeUnit.MILLISECONDS);
    }

    public GameBoard() {
        this.setPreferredSize(SCREEN);
        this.newBall();
        this.newGoal();
        this.addMouseMotionListener(this.paddle);
        this.addKeyListener(this);
        this.score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
    }

    @Override
    public void run() {
        this.move();
        this.checkHit();
        this.checkCollision();
        this.repaint();
    }

    public void newBall() {
        this.ball = new Ball((GAME_WIDTH / 2.) - BALL_RAD,
                (GAME_HEIGHT / 2.) - BALL_RAD, BALL_RAD * 2, BALL_RAD * 2);
    }

    DoubleSupplier euclidDist = () -> {
        Point2D.Double cb = ball.center(), cp = this.paddle.center();
        return abs((cb.x - cp.x) * (cb.x - cp.x) + (cb.y - cp.y) * (cb.y - cp.y));
    };
    BooleanSupplier collisions = () -> this.euclidDist.getAsDouble() <= (BALL_RAD + PADDLE_RAD) * (BALL_RAD + PADDLE_RAD);

    public void move() {
        this.ball.move();
        this.paddle.move();
        this.goal.move();
    }

    public void checkCollision() {
        this.ballCollisions();
        this.paddleCollisions();
        this.goalCollisions();
    }

    private void goalCollisions() {
        if (this.goal.getY() <= 0) {
            this.goal.setYVelocity(abs(this.goal.getYVelocity()));
            this.goal.setYVelocity(this.goal.getYVelocity());
        }
        if (this.goal.getY() >= GAME_HEIGHT - GOAL_HEIGHT) {
            this.goal.setYVelocity(abs(this.goal.getYVelocity()));
            this.goal.setYVelocity(-this.goal.getYVelocity());
        }
    }

    private void ballCollisions() {
        //bounce ball off top & bottom window edges
        if (this.ball.getY() <= 0) {
            this.ball.setYVelocity(abs(this.ball.getYVelocity()));
            this.ball.setYVelocity(this.ball.getYVelocity());
        }
        if (this.ball.getY() >= GAME_HEIGHT - BALL_RAD) {
            this.ball.setYVelocity(abs(this.ball.getYVelocity()));
            this.ball.setYVelocity(-this.ball.getYVelocity());
        }
        if (this.ball.getX() <= 0) {
            this.ball.setXVelocity(abs(this.ball.getXVelocity()));
            this.ball.setXVelocity(this.ball.getXVelocity());
        }
        if (this.ball.getX() >= GAME_WIDTH - BALL_RAD) {
            if (this.ball.getY() >= this.goal.getY() && this.ball.getY() <= this.goal.getY() + GOAL_HEIGHT) {
                Score.tot_score++;
                GOAL_SPEED += 0.5;
                newBall();
                newGoal();
            } else {
                this.ball.setXVelocity(abs(this.ball.getXVelocity()));
                this.ball.setXVelocity(-(this.ball.getXVelocity()));
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(0, 0, 0));
        g.fill3DRect(0, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT, false);
        this.paddle.draw(g);
        this.ball.draw(g);
        this.score.draw(g);
        this.goal.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            newBall();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            newGoal();
        }
    }

    private void paddleCollisions() {
        if (this.paddle.getY() <= 0)
            this.paddle.setY(0);
        if (this.paddle.getY() >= (GAME_HEIGHT - PADDLE_HEIGHT))
            this.paddle.setY((GAME_HEIGHT - PADDLE_HEIGHT));
        if (this.paddle.getX() <= 0)
            this.paddle.setX(0);
        if (this.paddle.getX() >= (GAME_WIDTH / 2 - PADDLE_WIDTH))
            this.paddle.setX((GAME_WIDTH / 2 - PADDLE_WIDTH));
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void checkHit() {
        if (paddle.getX() >= GAME_WIDTH / 2) return;   // don't make it move in right side of the field
        if (collisions.getAsBoolean()) {
            //static collisions (bodies can't exists inside each other
            double dist = sqrt(euclidDist.getAsDouble());
            double overlap = (dist - BALL_RAD - PADDLE_RAD) * .5;
            paddle.setX(paddle.getX() + overlap * (paddle.getX() - ball.getX()) / dist);
            paddle.setY(paddle.getY() + overlap * (paddle.getY() - ball.getY()) / dist);
            ball.setX(ball.getX() - overlap * (ball.getX() - paddle.getX()) / dist);
            ball.setY(ball.getY() - overlap * (ball.getY() - paddle.getY()) / dist);
            ball.setXVelocity(ball.getXVelocity() - overlap * (ball.getX() - paddle.getX()) * .1);
            ball.setYVelocity(ball.getYVelocity() - overlap * (ball.getY() - paddle.getY()) * .1);
        }
    }

    private void newGoal() {
        this.goal = new Goal(new Random().nextInt((int) GAME_HEIGHT - GOAL_HEIGHT), GAME_WIDTH, GOAL_SPEED);
    }

}

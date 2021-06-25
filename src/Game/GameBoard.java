package Game;

import Functions.IBoolean;
import Functions.IDouble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class GameBoard extends JPanel implements Runnable, KeyListener {
    public static final double GAME_WIDTH = 1000;
    public static final double GAME_HEIGHT = 600;
    public static final Dimension SCREEN = new Dimension((int) GAME_WIDTH, (int) GAME_HEIGHT);
    private static final int PADDLE_HEIGHT = 60, PADDLE_WIDTH = 60, PADDLE_RAD = 30;
    private static final int BALL_RAD = 15;
    public static int GOAL_HEIGHT = 150;
    private final Score score;
    private final Paddle paddle;
    IDouble euclidDist = (ball, paddle) -> {
        Point2D.Double cb = ball.center(), cp = paddle.center();
        return abs((cb.x - cp.x) * (cb.x - cp.x) + (cb.y - cp.y) * (cb.y - cp.y));
    };
    IBoolean collisions = (ball, paddle) -> euclidDist.eval(ball, paddle) <= (BALL_RAD + PADDLE_RAD) * (BALL_RAD + PADDLE_RAD);
    private Goal goal;
    private Ball ball;

    public void start() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.scheduleWithFixedDelay(this, 30, 1, TimeUnit.MILLISECONDS);
    }

    public GameBoard() {
        this.setPreferredSize(SCREEN);
        this.newBall();
        this.newGoal();
        this.paddle = new Paddle(0, 200, PADDLE_WIDTH, PADDLE_HEIGHT);
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

    public void move() {
        this.ball.move();
        this.paddle.move();
    }

    public void checkCollision() {
        //bounce ball off top & bottom window edges
        if (this.ball.getY() <= 0) {
            if (this.ball.getyVelocity() == 0) this.ball.setY(0);
            this.ball.setYDirection(-this.ball.getyVelocity());
        }
        if (this.ball.getY() >= GAME_HEIGHT - BALL_RAD) {
            if (this.ball.getyVelocity() == 0) this.ball.setY(GAME_HEIGHT - BALL_RAD);
            this.ball.setYDirection(-this.ball.getyVelocity());
        }

        if (this.paddle.getY() <= 0)
            this.paddle.setY(0);
        if (this.paddle.getY() >= (GAME_HEIGHT - PADDLE_HEIGHT))
            this.paddle.setY((GAME_HEIGHT - PADDLE_HEIGHT));
        if (this.paddle.getX() <= 0)
            this.paddle.setX(0);
        if (this.paddle.getX() >= (GAME_WIDTH / 2 - PADDLE_WIDTH))
            this.paddle.setX((GAME_WIDTH / 2 - PADDLE_WIDTH));

        if (this.ball.getX() <= 0) {
            if (this.ball.getxVelocity() == 0) this.ball.setX(0);
            this.ball.setxVelocity(abs(this.ball.getxVelocity()));
            this.ball.setXDirection(this.ball.getxVelocity());
        }
        if (this.ball.getX() >= GAME_WIDTH - BALL_RAD) {
            if (this.ball.getxVelocity() == 0) this.ball.setX(GAME_WIDTH - BALL_RAD);
            if (this.ball.getY() >= this.goal.getY() && this.ball.getY() <= this.goal.getY() + GOAL_HEIGHT) {
                Score.tot_score++;
                newBall();
                newGoal();
            } else {
                this.ball.setxVelocity(abs(this.ball.getxVelocity()));
                this.ball.setXDirection(-(this.ball.getxVelocity()));
            }
        }
    }

    public void checkHit() {
        if (paddle.getX() >= GAME_WIDTH / 2) return;   // don't make it move in right side of the field
        if (collisions.eval(ball, paddle)) {
            //static collisions (bodies can't exists inside each other
            double dist = sqrt(euclidDist.eval(ball, paddle));
            double overlap = (dist - BALL_RAD - PADDLE_RAD) * .5;
            paddle.setX(paddle.getX() + overlap * (paddle.getX() - ball.getX()) / dist);
            paddle.setY(paddle.getY() + overlap * (paddle.getY() - ball.getY()) / dist);
            ball.setX(ball.getX() - overlap * (ball.getX() - paddle.getX()) / dist);
            ball.setY(ball.getY() - overlap * (ball.getY() - paddle.getY()) / dist);
            ball.setxVelocity(ball.getxVelocity() - overlap * (ball.getX() - paddle.getX()) * .1);
            ball.setyVelocity(ball.getyVelocity() - overlap * (ball.getY() - paddle.getY()) * .1);
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

    private void newGoal() {
        this.goal = new Goal(new Random().nextInt((int) GAME_HEIGHT - GOAL_HEIGHT), (int) GAME_WIDTH);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}

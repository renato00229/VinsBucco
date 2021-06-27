package Game;

import Game.Functions.Coordinate;
import Game.Functions.MovingObj;
import Game.Objects.*;

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

import static Game.Functions.Login.startingOb;
import static Game.Objects.Score.tot_score;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class GameBoard extends JPanel implements Runnable, KeyListener {
    public static final double GAME_WIDTH = 1000, GAME_HEIGHT = 600;
    public static final Dimension SCREEN = new Dimension((int) GAME_WIDTH, (int) GAME_HEIGHT);
    public static final int PADDLE_HEIGHT = 60,
            PADDLE_WIDTH = 60,
            PADDLE_RAD = 30,
            BALL_RAD = 15,
            GOAL_HEIGHT = 150,
            MAX_SPEED = 6;
    private final Score score;
    public static double GOAL_SPEED = 0;
    private Goal goal;
    private Ball ball;
    private final Paddle paddle = new Paddle(0, 200, PADDLE_WIDTH, PADDLE_HEIGHT);
    private final List<Obstacle> obstacles = new ArrayList<>();
    public void start() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.scheduleWithFixedDelay(this, 30, 1, TimeUnit.MILLISECONDS);
    }

    public GameBoard() {
        this.setMaximumSize(SCREEN);
        this.setMinimumSize(SCREEN);
        this.setPreferredSize(SCREEN);
        this.setSize(SCREEN);
        this.newBall();
        this.newGoal();
        this.addMouseMotionListener(this.paddle);
        this.addKeyListener(this);
        this.score = new Score();
        this.setFocusable(true);
        for (int i = 0; i < startingOb; i++)
            obstacles.add(new Obstacle());
    }

    private static double dist(Coordinate paddle, Coordinate ball) {
        Point2D.Double cb = ball.center(), cp = paddle.center();
        return abs((cb.x - cp.x) * (cb.x - cp.x) + (cb.y - cp.y) * (cb.y - cp.y));
    }

    public void newBall() {
        this.ball = new Ball((GAME_WIDTH / 2.) - BALL_RAD,
                (GAME_HEIGHT / 2.) - BALL_RAD, BALL_RAD * 2, BALL_RAD * 2);
    }

    private static boolean collision(Coordinate a1, Coordinate a2, double r2) {
        return dist(a1, a2) <= (BALL_RAD + r2) * (BALL_RAD + r2);
    }

    @Override
    public void run() {
        this.move();
        this.checkHit(paddle, ball);
        for (Obstacle o : this.obstacles) this.checkHit(o, ball);
        this.checkCollision();
        this.repaint();
    }


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
        if (this.ball.getY() < 0) {
            this.ball.setYVelocity(abs(this.ball.getYVelocity()));
            this.ball.setYVelocity(this.ball.getYVelocity());
        }
        if (this.ball.getY() > GAME_HEIGHT - BALL_RAD) {
            this.ball.setYVelocity(abs(this.ball.getYVelocity()));
            this.ball.setYVelocity(-this.ball.getYVelocity());
        }
        if (this.ball.getX() < 0) {
            this.ball.setXVelocity(abs(this.ball.getXVelocity()));
            this.ball.setXVelocity(this.ball.getXVelocity());
        }

        if (this.ball.getX() > GAME_WIDTH - BALL_RAD) {
            if (this.ball.getY() >= this.goal.getY() && this.ball.getY() <= this.goal.getY() + GOAL_HEIGHT) {
                tot_score++;
                GOAL_SPEED += 0.5;
                newBall();
                newGoal();
                if (tot_score % 5 == 0) {
                    obstacles.add(new Obstacle());
                }
            } else {
                this.ball.setXVelocity(abs(this.ball.getXVelocity()));
                this.ball.setXVelocity(-(this.ball.getXVelocity()));
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
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

    public void checkHit(Coordinate static_obj, Coordinate moving_obj) {
        //if (static_obj.getX() >= GAME_WIDTH / 2) return;   // don't make it move in right side of the field
        double r = (static_obj instanceof Paddle) ? PADDLE_RAD : 10;
        if (collision(static_obj, moving_obj, r)) {
            //static collisions (bodies can't exists inside each other
            double dist = sqrt(dist(static_obj, moving_obj));
            double overlap = (dist - BALL_RAD - r) * .5;
            static_obj.setX(static_obj.getX() + overlap * (static_obj.getX() - moving_obj.getX()) / dist);
            static_obj.setY(static_obj.getY() + overlap * (static_obj.getY() - moving_obj.getY()) / dist);
            moving_obj.setX(moving_obj.getX() - overlap * (moving_obj.getX() - static_obj.getX()) / dist);
            moving_obj.setY(moving_obj.getY() - overlap * (moving_obj.getY() - static_obj.getY()) / dist);
            ((MovingObj) moving_obj).setXVelocity(((MovingObj) moving_obj).getXVelocity() - overlap * (moving_obj.getX() - static_obj.getX()) * .1);
            ((MovingObj) moving_obj).setYVelocity(((MovingObj) moving_obj).getYVelocity() - overlap * (moving_obj.getY() - static_obj.getY()) * .1);
        }
    }

    private void newGoal() {
        this.goal = new Goal(new Random().nextInt((int) GAME_HEIGHT - GOAL_HEIGHT), GAME_WIDTH, GOAL_SPEED);
    }

}

package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameBoard extends JPanel implements Runnable, KeyListener {
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 600;
    public static final Dimension SCREEN = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    private static final int PADDLE_HEIGHT = 60, PADDLE_WIDTH = 60;
    public Goal goal;
    public Ball ball;
    public Score score;
    public Paddle paddle;

    public GameBoard() {
        this.setPreferredSize(SCREEN);
        this.newBall();
        this.paddle = new Paddle(0, 200, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.addMouseMotionListener(this.paddle);
        this.score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
    }

    public void newBall() {
        this.ball = new Ball((GAME_WIDTH / 2) - 20, (GAME_HEIGHT / 2) - 20, 20, 20);
        this.goal = new Goal(new Random().nextInt(GAME_HEIGHT), GAME_WIDTH);
    }

    public void start() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.scheduleWithFixedDelay(this, 30, 1, TimeUnit.MILLISECONDS);
    }

    public void move() {
        this.ball.move();
    }

    @Override
    public void run() {
        this.move();
        this.checkHit();
        this.checkCollision();
        this.repaint();
    }

    public void checkCollision() {
        //bounce ball off top & bottom window edges
        if (this.ball.y <= 0)
            this.ball.setYDirection(-this.ball.yVelocity);
        if (this.ball.y >= GAME_HEIGHT - 20)
            this.ball.setYDirection(-this.ball.yVelocity);

        if (this.paddle.y <= 0)
            this.paddle.y = 0;
        if (this.paddle.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            this.paddle.y = (GAME_HEIGHT - PADDLE_HEIGHT);
        if (this.paddle.x <= 0)
            this.paddle.x = 0;
        if (this.paddle.x >= (GAME_WIDTH / 2 - PADDLE_WIDTH))
            this.paddle.x = (GAME_WIDTH / 2 - PADDLE_WIDTH);


        int intersect = 0;
        if (this.ball.x <= 0) {
            this.ball.xVelocity = Math.abs(this.ball.xVelocity);
            this.ball.setXDirection(this.ball.xVelocity);
        }
        if (this.ball.x >= GAME_WIDTH - 20) {
            //check for y and find if goal

            //else-->
            {
                this.ball.xVelocity = Math.abs(this.ball.xVelocity);
                this.ball.setXDirection(-(this.ball.xVelocity));
            }
        }
    }

    public void checkHit() {
        if (ball.x <= paddle.x + paddle.width && ball.y <= paddle.y + paddle.height) {
            if (ball.x >= paddle.x && ball.y >= paddle.y) {
                System.out.println("HIT");
            }
        }

    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(0, 0, 0));
        g.fill3DRect(0, 0, GAME_WIDTH, GAME_HEIGHT, false);
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
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

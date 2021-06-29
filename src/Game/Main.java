package Game;

import Game.Objects.Score;
import Game.Utils.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



/**
 * Air Hockey Revisited, Single Player game.
 * The idea was born from Air Hokey: a game in which two or more players compete to score many points
 * as possible with the help of paddles. The paddle runs on the plane as if it were on ice, making
 * the game exciting and fun. The aim of the game is to get the ball into your goal, moving it with your
 * paddle.
 *
 * This Game was designed as a single player, so as to move the paddle with the mouse, by including
 * several difficulties.
 *
 * @Login #java class
 * When the game begins, you can login to store your score and then pick up where you left off!
 *
 * @Paddle #java class
 * Clicking with the mouse on the paddle, you can move it to make it go to hit the ball and that it
 * ends up in the goal
 *
 * @Goal #java class
 * if your ball goes to the goal, your score is increased by 1
 */

public class Main {
    public static String USER = null, PASS = null;
    public static GameBoard game;

    public static void launchGame() {
        if (game != null) return;
        JFrame frame = new JFrame("Air Hockey - Single Player");
        game = new GameBoard();
        game.init();
        frame.add(game);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        WindowAdapter closed = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                WindowEvent listen = new WindowEvent(frame, 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                Login.findAndSave(USER, PASS, Score.SCORE);
                System.out.println("System closed by user");
                System.exit(0);
            }
        };
        frame.addWindowListener(closed);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }

    /**
     *
     * @Login #is first invoked
     */

    public static void main(String[] args) {
        new Login();
        //launchGame();
    }
}


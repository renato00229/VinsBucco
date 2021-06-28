package Game;

import Game.Objects.Score;
import Game.Utils.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static String USER = null, PASS = null;
    public static GameBoard game;

    public static void launchGame() {
        if (game != null) return;
        JFrame frame = new JFrame("Air Hockey");
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

    public static void main(String[] args) {
        new Login();
        //launchGame();
    }
}


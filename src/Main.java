import Game.GameBoard;
import Game.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static String USER = null, PASS = null;

    public static void launchGame() {
        JFrame frame = new JFrame("Air Hockey");
        GameBoard panel = new GameBoard();
        frame.add(panel);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        WindowAdapter closed = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                WindowEvent listen = new WindowEvent(frame, 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(listen);
                System.out.println("System closed by user");
                Login.findAndSave(USER, PASS, Score.tot_score);
                System.exit(0);
            }
        };
        frame.addWindowListener(closed);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.start();
    }

    public static void main(String[] args) {
        launchGame();
    }
}


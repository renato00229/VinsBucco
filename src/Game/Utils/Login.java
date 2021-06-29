package Game.Utils;

import Game.GameBoard;
import Game.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

import static Game.Main.USER;
import static Game.Main.launchGame;
import static Game.Objects.Score.SCORE;


/**
In the first panel users can authenticate theirself whith an username and a password in order to register their score and save the game.
Also, they can cancel previous score and start a new game. We use a file to save the users informations and the greater score. 
*/
public class Login extends JFrame {
    private static final Dimension SCREEN = new Dimension(500, 500);
    private static final JTextField user = new JTextField("");
    private static final JPasswordField pass = new JPasswordField("");
    private static final JLabel userLabel = new JLabel("USERNAME:");
    private static final JLabel passLabel = new JLabel("PASSWORD:");
    private static final JButton restart = new JButton("RESET SCORE"), keep = new JButton("CONTINUE FROM SCORE");
    private static final JTextArea console = new JTextArea("Insert user and password to restore previous score", 5, 10);
    public static int startingOb = 0;


    public Login() {
        super("Login to play");
        setSize(SCREEN);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(makePanel());
        add(console, SpringLayout.SOUTH);
        setVisible(true);
        initListeners();
    }

    private static int loadGame() {
        Main.USER = user.getText();
        Main.PASS = new String(pass.getPassword());
        return getScore(Main.USER, Main.PASS);
    }

    private static int getScore(String user, String pass) {
        if (user.equals("") && pass.equals("")) return 0;
        boolean found = false;
        int score = 0;
        try {
            FileReader fr = new FileReader(new File("resources/log.txt"));
            Scanner scanner = new Scanner(fr);
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                if (s.equals(user + " " + pass)) {
                    found = true;
                    score = Integer.parseInt(scanner.nextLine());
                }
            }
            if (!found) {
                scanner.close();
                saveScore(user, pass, score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }

    private static JPanel makePanel() {
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(500, 350));
        panel.setBorder(new EmptyBorder(40, 10, 40, 10));
        panel.setLayout(new GridLayout(3, 2, 20, 90));
        panel.add(userLabel);
        panel.add(user);
        panel.add(passLabel);
        panel.add(pass);
        panel.add(restart);
        panel.add(keep);
        return panel;
    }

    public static void findAndSave(String user, String pass, int score) {
        if (user == null || pass == null) return;
        RandomAccessFile rf = null;
        try {
            File f = new File("resources/log.txt");
            rf = new RandomAccessFile(f, "rw");
            try {
                while (!rf.readLine().equals(user + " " + pass)) {
                }
            } catch (NullPointerException ignored) {
                rf.close();
            }
            rf.writeBytes(score + "\n");
            rf.close();
        } catch (IOException e) {
            if (e.getMessage().equals("Stream Closed"))
                System.err.println("User score not saved.");
            else e.printStackTrace();
        }
    }

    public static void saveScore(String user, String pass, int score) {
        if (user == null || pass == null) return;
        FileWriter fw;
        try {
            fw = new FileWriter("resources/log.txt", true);
            fw.append(user).append(" ").append(pass).
                    append("\n").append(Integer.toString(score)).append("\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void initListeners() {
        restart.addActionListener(e -> {
            loadGame();
            console.setText("Score reset for player " + USER);
            launchGame();
            dispose();
        });
        keep.addActionListener(e -> {
            SCORE = loadGame();
            console.setText("found previous score " + SCORE);
            for (int i = 0; i < SCORE; i++) {
                GameBoard.GOAL_SPEED += 0.5;
                if (i != 0 && i % 5 == 0) startingOb++;
            }
            launchGame();
            dispose();
        });
    }
}

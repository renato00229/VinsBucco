import Game.Score;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class Login extends JFrame {
    private static final Dimension SCREEN = new Dimension(500, 500);
    private static final JTextField user = new JTextField("");
    private static final JPasswordField pass = new JPasswordField("");
    private static final JLabel userLabel = new JLabel("USERNAME:");
    private static final JLabel passLabel = new JLabel("PASSWORD:");
    private static final JButton restart = new JButton("RESET SCORE"), keep = new JButton("CONTINUE FROM SCORE");
    private static final JTextArea console = new JTextArea("", 5, 10);

    static {
        restart.addActionListener(e -> {
            loadGame();
            Main.launchGame();
        });
        keep.addActionListener(e -> {
            Score.tot_score = loadGame();
            Main.launchGame();
        });
    }

    public Login() {
        super("Login to play");
        setSize(SCREEN);
        setDefaultLookAndFeelDecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(makePanel());
        add(console, SpringLayout.SOUTH);
        setVisible(true);
    }

    private static int loadGame() {
        Main.USER = user.getText();
        Main.PASS = new String(pass.getPassword());
        int score = getScore(Main.USER, Main.PASS);
        console.setText("found previous score " + score);
        return score;
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

    public static JPanel makePanel() {
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
        panel.setBackground(new Color(132, 255, 52, 188));
        return panel;
    }

    public static void main(String[] args) {
        new Login();
    }

    public static int findAndSave(String user, String pass, int score) {
        try {
            File f = new File("resources/log.txt");
            FileReader fr = new FileReader(f);
            RandomAccessFile rf = new RandomAccessFile(f, "rw");
            while (!rf.readLine().equals(user + " " + pass)) {
            }
            rf.writeBytes(score + "\n");
            rf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }

    public static void saveScore(String user, String pass, int score) {
        if (user == null || pass == null) return;
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File("resources/log.txt"), true);
            fw.append(user).append(" ").append(pass).
                    append("\n").append(Integer.toString(score)).append("\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

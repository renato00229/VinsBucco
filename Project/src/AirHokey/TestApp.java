package AirHokey;

import javax.swing.*;
import java.awt.*;

public class TestApp {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame f = new JFrame("Air Hokey");

            Panel game = new Panel();

            f.setContentPane(game);
            f.setSize(600, 400);
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setResizable(false);
            f.setVisible(true);


        });
    }
}


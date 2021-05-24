package AirHokey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Random;

public class Panel extends JPanel implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    Timer timer;
    HashMap<String, GameObject> go;

    public Panel() {
        super();
        setBackground(Color.BLACK);

        addKeyListener(this);
        setFocusable(true);
    }
    public void init() {
        Random rnd = new Random();
        go = new HashMap<>();

        go.put("ball", new Ball(this, go,
                20, 20,
                getWidth() / 2, getHeight() / 2,
                rnd.nextInt(6) + 3, rnd.nextInt(6) + 3));

        go.put("player_sx", new Porte(this, go,
                20, 60,
                0, (getHeight() - 60) / 2,
                0, 0));

        go.put("player_dx", new Porte(this, go,
                20, 60,
                getWidth() - 20, (getHeight() - 60) / 2,
                0, 0));

        timer = new Timer(20, this);
        timer.start();
    }

}

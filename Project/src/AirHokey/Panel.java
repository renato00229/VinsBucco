package AirHokey;

import AirHokey.GameObjects.Ball;
import AirHokey.GameObjects.Disk;
import AirHokey.GameObjects.GameObject;
import AirHokey.GameObjects.Goal;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Panel extends JPanel {
    private static final long serialVersionUID = 1L;
    private HashMap<String, GameObject> gameObjects;

    public Panel() {
        super(); //automatic in every class, not needed
        //setBackground(Color.white);  //anche questo può dare problemi
        //guarda   paintComponent(Graphics g);

    }

    public HashMap<String, GameObject> getGameObjects() {
        return gameObjects;
    }

    public void init() {
        addKeyListener(new Input());  //separated, easy to change later
        setFocusable(true);
        initObjects();
    }

    public void initObjects() {
        Random rd = new Random();
        gameObjects = new HashMap<>();
        gameObjects.put("ball", new Ball(this, gameObjects, 20, 20, getWidth() / 2, getHeight() / 2, rd.nextInt(6) + 3, rd.nextInt(6) + 3));
        gameObjects.put("disk_1", new Disk(this, gameObjects, 30, 30, getWidth() / 4,
                getHeight() / 2, rd.nextInt(6) + 3, rd.nextInt(6) + 3));
        gameObjects.put("disk_2", new Disk(this, gameObjects, 30, 30, getWidth() - 150,
                getHeight() / 6, rd.nextInt(6) + 3, rd.nextInt(6) + 3));
        gameObjects.put("goal_1", new Goal(this, gameObjects, 20, 100, 0,
                (getHeight() - 60) / 2, 0, 0));
        gameObjects.put("goal_2", new Goal(this, gameObjects, 20, 100, getWidth() - 20,
                (getHeight() - 60) / 2, 0, 0));
        Goal p1 = ((Goal) gameObjects.get("goal_1"));
        Goal p2 = ((Goal) gameObjects.get("goal_2"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(209, 255, 186));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (GameObject obj : gameObjects.values())
            obj.paint(g);
    }


}

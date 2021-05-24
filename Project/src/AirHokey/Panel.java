package AirHokey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Random;

public class Panel extends JPanel implements ActionListener,KeyListener{
    private static final long serialVersionUID = 1L;
    Timer time;
    HashMap<String, GameObject> hashgo;

    public Panel(){
        super();
        setBackground(Color.BLACK);
        addKeyListener( this);
        setFocusable(true);

    }

    public void start(){
        Random rd= new Random();
        hashgo = new HashMap<>();

        hashgo.put("ball", new Ball(this, hashgo, 20, 20, getWidth()/2, getHeight()/2));
        hashgo.put("disk_1", new Disk (this, hashgo, 20, 20, getWidth()/2, getHeight()/2) );
        hashgo.put("disk_2", new Disk(this, hashgo, 20, 20, getWidth()/2, getHeight()/2));
        hashgo.put("goal_1", new Goal(this, hashgo, 20, 20, getWidth()/2, getHeight()/2));
        hashgo.put("goal_2", new Goal(this, hashgo, 20, 20, getWidth()/2, getHeight()/2));

        time = new Timer(20, this);
        time.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Goal p1 = ((Goal) hashgo.get("goal_1"));
        Goal p2 = ((Goal) hashgo.get("goal_2"));



    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(GameObject obj : hashgo.values())
            obj.paint(g);


    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
    }
}

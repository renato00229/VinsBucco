package AirHokey;

import AirHokey.GameObjects.AbstractGameObject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class to handle the user input
 */
public class Input extends KeyAdapter {
    static int padSpeed = 14;
    AbstractGameObject leftPad, rightPad;

    public Input(AbstractGameObject leftPad, AbstractGameObject rightPad) {
        this.leftPad = leftPad;
        this.rightPad = rightPad;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            leftPad.y -= padSpeed;  //y is reversed in java
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            leftPad.y += padSpeed;
        }

        //per l'altro puoi usare awsd
        //System.out.println(e);
    }
}

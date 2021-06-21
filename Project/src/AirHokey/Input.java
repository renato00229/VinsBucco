package AirHokey;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class to handle the user input
 */
public class Input extends KeyAdapter {
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

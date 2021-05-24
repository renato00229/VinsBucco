package AirHokey;

import javax.swing.*;
import java.util.HashMap;

public abstract class AbstractGameObject implements GameObject{
    JPanel pann;
    HashMap<String, GameObject> hashgo;
    int w, h;
    int x, y;
    int speedX, speedY;
}

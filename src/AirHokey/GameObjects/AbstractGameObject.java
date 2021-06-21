package AirHokey.GameObjects;

import javax.swing.*;
import java.util.HashMap;

public abstract class AbstractGameObject implements GameObject {
    public JPanel pann;
    public HashMap<String, GameObject> hashgo;  //in realtà non capisco a cosa serva questo qui
    public int x, y;
    public int speedX, speedY;
    public int w, h;  //probabilmente vuoi questi "final" così non li puoi cambiare più in esecuzione
}

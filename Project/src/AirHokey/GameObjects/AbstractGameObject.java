package AirHokey.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.awt.Rectangle;

public abstract class AbstractGameObject extends Rectangle implements GameObject{
    public JPanel pann;
    public HashMap<String, GameObject> hashgo;  //in realtà non capisco a cosa serva questo qui
    public int x, y;
    public int speedX, speedY;
    public int w, h;  //probabilmente vuoi questi "final" così non li puoi cambiare più in esecuzione
}

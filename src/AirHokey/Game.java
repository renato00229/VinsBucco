package AirHokey;

import AirHokey.GameObjects.AbstractGameObject;
import AirHokey.GameObjects.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    public static Dimension SCREEN_SIZE = new Dimension(700, 400);
    private JFrame frame;
    private Panel panel;

    public void start() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        /*
        il modo più sicuro di gestire un thread è di lasciarlo gestire a loro
        Qui 3 modi per dire la stessa cosa, praticamente aspetta 30 millisecondi,
        poi prende un Runnable e lo invoca ogni 12 millisecondi
        */


        //executor.scheduleWithFixedDelay(this::loop,30,12, TimeUnit.MILLISECONDS);
        //executor.scheduleWithFixedDelay(()-> this.loop(),30,12, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                loop();
            }
        }, 30, 2, TimeUnit.MILLISECONDS);
    }

    public void loop() {
        //logica del gioco qui,

        updatePositions(this.panel.getGameObjects());
        updateVelocities(this.panel.getGameObjects());
        checkEdges(this.panel.getGameObjects());
        // magari controlla se fa goal o il risultato, o aggiorna qualcos'altro
        this.frame.repaint();  //questo automaticamente chiama tutti i component e infine la pipeline direttamente
        //.. e ricomincia
    }


    //aggiorna le posizioni aggiungendo la velocità
    //integrazione secondo il metodo di eulero
    //y(i+1)        =   y(i)        +       dt     *    dy/dt
    //next position = this position +   time step size * velocity

    //puoi considerare h = 1
    public void updatePositions(HashMap<String, GameObject> allObjects) {
        for (GameObject g : allObjects.values()) {
            AbstractGameObject gameObject = (AbstractGameObject) g;
            gameObject.x += gameObject.speedX;
            gameObject.y += gameObject.speedY;
        }
    }

    //la pallina puo andare su, giu, destra sinistra
    //TODO: aggiornare la velocità per la prossima iterazione
    // se la velocità qui diventa 0, nel prossimo loop, updatePositions()
    // aggiungerà 0 alla posizione, quindi no movimento.
    public void updateVelocities(HashMap<String, GameObject> allObjects) {
        for (GameObject g : allObjects.values()) {
            AbstractGameObject gameObject = (AbstractGameObject) g;
            gameObject.speedX += 0;   //TODO: logica qui
            gameObject.speedY += 0;
        }
    }


    //TODO : controlla che gli oggetti non vadano fuori dai limiti
    // bordi, palla e goal... non saprei qui
    // puoi controllare se due oggetti si toccano in un separato metodo

    // oppure .. CONSIGLIO :
    // potresti fare -> AbstractGameObject extends Rectangle
    // così puoi sia disegnarli in grafica (anche i cerchi sono disegnati sui rettangoli come riferimento)
    // e in aggiunta hai il metodo boolean intersect(Rectangle other) già pronto senza che perdi testa

    //altrimenti dovresti tenere d'occhio x e w (posizione, larghezza) e controllare con tutti gli altri
    //GameObject se si trovano nello stesso spazio
    public void checkEdges(HashMap<String, GameObject> allObjects) {
        for (GameObject g : allObjects.values()) {
            AbstractGameObject gameObject = (AbstractGameObject) g;
            if ((gameObject.x) >= panel.getWidth() - gameObject.w) //fuori a destra
                gameObject.x = panel.getWidth() - gameObject.w;

            //TODO: continua qui
        }
    }

    public void init() {  //init frame and panel game
        frame = new JFrame("Air Hokey");
        panel = new Panel();
        panel.setSize(SCREEN_SIZE);
        frame.setSize(SCREEN_SIZE);
        panel.init(); //put all game objects in the game
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}

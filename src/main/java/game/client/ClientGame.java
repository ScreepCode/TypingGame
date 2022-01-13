package game.client;

import Listenklassen.List;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static game.PROTOKOLL.*;

public class ClientGame{

    private final ClientHead clientHead;
    private Boolean gameRunning = false;
    private Boolean gameCanStart = true;
    private Timer timerZeit = new Timer();

    private String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
    char[] textAsCharArr = text.toCharArray();
    private List<Integer> fehler = new List<>();

    int counter = 0;
    double timeLeft = 120.0;
    double apm = 0;

    private TimerTask taskZeit = new TimerTask() {
        @Override
        public void run() {
            if(timeLeft>=0){
                clientHead.gui.lblZeit.setText(Integer.toString((int)timeLeft));
                timeLeft-=0.1;
            }
            else{
                stopGame();
            }
        }
    };

    private Thread threadAPM = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                apm = counter*60/(120-timeLeft);
                clientHead.gui.lblAnschlaege.setText(Integer.toString((int)apm));
            }
        }
    });

    public ClientGame(ClientHead clientHead) {
        this.clientHead = clientHead;
        clientHead.gui.txtpnText.setText(text);
    }

    public void startGame(){
        if(gameCanStart){
            clientHead.gui.setPanelLayout("game");
            gameRunning = true;
            threadAPM.start();
            timerZeit.schedule(taskZeit, 0, 100);
            gameCanStart = false;
        }
    }

    public void stopGame(){
        gameRunning = false;
        timerZeit.cancel();
        threadAPM.interrupt();

        clientHead.send(CS_ENDEDGAME + SEPARATOR + counter + ":" + countFehler());
    }

    public void typeChar(char c){
        if(gameRunning){
            if(c != '\b') {
                SimpleAttributeSet set = new SimpleAttributeSet();
                if (c == textAsCharArr[counter]) {
                    fehler.append(0);
                    StyleConstants.setBackground(set, new Color(0, 128, 0));
                } else {
                    fehler.append(1);
                    StyleConstants.setBackground(set, new Color(128, 0, 0));
                }
                clientHead.gui.doc.setCharacterAttributes(counter, 1, set, true);
                counter++;
                if (counter == textAsCharArr.length) {
                    stopGame();
                }
            }
        }
    }

    public void removeLastChar(){
        SimpleAttributeSet set1 = new SimpleAttributeSet();
        fehler.toLast();
        fehler.remove();
        if(counter != 0) {
            counter--;
        }
        StyleConstants.setBackground(set1, new Color(255, 255, 255));

        clientHead.gui.doc.setCharacterAttributes(counter, 1, set1, true);
    }

    private int countFehler(){
        int fehlerInt = 0;
        for(fehler.toFirst(); fehler.hasAccess(); fehler.next()){
            if(fehler.getContent() == 1){
                fehlerInt++;
            }
        }
        return fehlerInt;
    }
    
}

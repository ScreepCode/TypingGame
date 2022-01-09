package game.client;

import java.awt.Color;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.util.*;


import Listenklassen.List;

public class ClientGame{

    ClientHead clientHead;
    Boolean gameRunning = false;
    Timer timer = new Timer();
    Timer timerZeit = new Timer();
    Timer timerAPM = new Timer();

    String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
    char[] textAsCharArr = text.toCharArray();
    List<Integer> fehler = new List<Integer>();

    int counter = 0;
    double timeLeft = 60.0;
    double apm = 0;

    TimerTask taskZeit = new TimerTask() {
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

    Thread threadAPM = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                apm = counter*60/(60-timeLeft);
                clientHead.gui.lblAnschlaege.setText(Integer.toString((int)apm));
            }
        }
    });

    public ClientGame(ClientHead clientHead) {
        this.clientHead = clientHead;
        clientHead.gui.txtpnText.setText(text);
    }

    public void startGame(){
        clientHead.gui.setPanelLayout("game");
        gameRunning = true;
        threadAPM.start();
        timerZeit.schedule(taskZeit, 0, 100);
    }

    public void stopGame(){
        gameRunning = false;
        timerZeit.cancel();
        threadAPM.interrupt();
    }

    public void action(char c){
        if(gameRunning){
            SimpleAttributeSet set = new SimpleAttributeSet();
            
            if(c == textAsCharArr[counter]){
                fehler.append(0);
                StyleConstants.setBackground(set, new Color(0, 128, 0));
            }
            else{
                fehler.append(1);
                StyleConstants.setBackground(set, new Color(128, 0, 0));
            }
            clientHead.gui.doc.setCharacterAttributes(counter, 1, set, true);
            counter++;
            if(counter == textAsCharArr.length){
                stopGame();
            }
        }
    }
    
}

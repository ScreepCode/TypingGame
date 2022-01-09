package game.client;

import game.PROTOKOLL;

public class ClientLobby {

    ClientHead clientHead;

    Boolean readyStatus = false;

    public ClientLobby(ClientHead clientHead) {
        this.clientHead = clientHead;
    
    
    }

    public void startGame(){
        clientHead.gui.setPanelLayout("game");
    }

    public void refreshCounter(String data){
        clientHead.gui.lblReadyCounter.setText("Bereit: " + data);
    }
    
    public void setReady(){
        if(readyStatus == false){
            readyStatus = true;
            clientHead.send(PROTOKOLL.CS_SETREADY + PROTOKOLL.SEPARATOR + "1");
        }
        else if(readyStatus == true){
            readyStatus = false;
            clientHead.send(PROTOKOLL.CS_SETREADY + PROTOKOLL.SEPARATOR + "0");
        }
    }

}

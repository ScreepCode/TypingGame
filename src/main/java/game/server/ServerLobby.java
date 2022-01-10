package game.server;

import Listenklassen.List;
import game.PROTOKOLL;

public class ServerLobby {

    ServerHead serverHead;

    public ServerLobby(ServerHead serverHead) {
        this.serverHead = serverHead;
    }


    public void nachrichtenVerwaltung(String pClientIP, int pClientPort, String pMessage){
        int posSep1 = pMessage.indexOf(PROTOKOLL.SEPARATOR);
		String prefix = pMessage.substring(0, posSep1);
        String daten =  pMessage.substring(posSep1+1);

        Spieler tmpSpieler = serverHead.spielerSuchen(pClientIP, pClientPort);

		switch (prefix) {
            case PROTOKOLL.CS_ENTERLOBBY: {
                addPlayerToLobby(tmpSpieler);
				checkIfLobbyReady();
                }break;

            case PROTOKOLL.CS_SETREADY: {
				if(daten.equals("1")){
                    tmpSpieler.setReadyStatus(true);
                }
                else if(daten.equals("0")){
                    tmpSpieler.setReadyStatus(false);
                }
                checkIfLobbyReady();
				}break;


		}
    }


    public void checkIfLobbyReady(){
        int lobbyMember = 0;
        int readyMember = 0;

        List<Spieler> spielerList = serverHead.spieler;
        for(spielerList.toFirst(); spielerList.hasAccess(); spielerList.next()){
            if(spielerList.getContent().getJoinedLobby()){ lobbyMember++; }
            if(spielerList.getContent().getReadyStatus()){ readyMember++; }
        }

        if(readyMember == lobbyMember){
            serverHead.sendToAll(PROTOKOLL.SC_LOBBYSTATUS + PROTOKOLL.SEPARATOR + "START");
        }
        else{
            serverHead.sendToAll(PROTOKOLL.SC_LOBBYSTATUS + PROTOKOLL.SEPARATOR + readyMember + "/" + lobbyMember);
        }
    }

    public void addPlayerToLobby(Spieler spieler){
        spieler.setJoinedLobby(true);
        checkIfLobbyReady();
    }

    public void removePlayerFromLobby(){
        checkIfLobbyReady();
    }
    
}

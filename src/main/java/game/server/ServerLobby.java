package game.server;

import game.PROTOKOLL;

public class ServerLobby {

    ServerHead serverHead;
    int lobbyMember = 0;
    int readyMember = 0;

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
                lobbyMember++;
				checkIfLobbyReady();
                }break;
            
            case PROTOKOLL.CS_SETREADY: {
				if(daten.equals("1")){
                    tmpSpieler.setReadyStatus(true);
                    readyMember++;
                }
                else if(daten.equals("0")){
                    tmpSpieler.setReadyStatus(false);
                    readyMember--;
                }
                checkIfLobbyReady();
				}break;

			
		}
    }


    public void checkIfLobbyReady(){
        if(lobbyMember > 0){
            if(readyMember == lobbyMember){
                serverHead.sendToAll(PROTOKOLL.SC_LOBBYSTATUS + PROTOKOLL.SEPARATOR + "START");
            }
            else{
                serverHead.sendToAll(PROTOKOLL.SC_LOBBYSTATUS + PROTOKOLL.SEPARATOR + readyMember + "/" + lobbyMember);
            }
        }
    }
    
}

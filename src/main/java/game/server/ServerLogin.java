package game.server;

import game.PROTOKOLL;

import java.util.Random;

public class ServerLogin {

    ServerHead serverHead;

    public ServerLogin(ServerHead serverHead) {
        this.serverHead = serverHead;
    }

    public void nachrichtenVerwaltung(String pClientIP, int pClientPort, String pMessage){
        int posSep1 = pMessage.indexOf(PROTOKOLL.SEPARATOR);
		String prefix = pMessage.substring(0, posSep1);
        String daten =  pMessage.substring(posSep1+1);




        Spieler tmpSpieler = serverHead.spielerSuchen(pClientIP, pClientPort);

		switch (prefix) {
            case PROTOKOLL.CS_ACC_CREATION: {
				int posSep2 = daten.indexOf(':');
                String username = daten.substring(0, posSep2);
                String passwordHash = daten.substring(posSep2+1);
				
				try {
                    Boolean result = serverHead.connector.createAccount(username, passwordHash);
                    if(result){
                        tmpSpieler.setNickName(username);
                        String antwort = PROTOKOLL.SC_LOGINSTATUS + PROTOKOLL.SEPARATOR + "Success";
                        serverHead.send(pClientIP, pClientPort, antwort);
                    }
                    else{
                        String antwort = PROTOKOLL.SC_LOGINSTATUS + PROTOKOLL.SEPARATOR + "Failed";
                        serverHead.send(pClientIP, pClientPort, antwort);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    String antwort = PROTOKOLL.SC_LOGINSTATUS + PROTOKOLL.SEPARATOR + "Failed";
                    serverHead.send(pClientIP, pClientPort, antwort);
                }
				
				}break;

			case PROTOKOLL.CS_ACC_LOGIN: {
				String [] dataArr = daten.split(":");
				if(dataArr.length == 1) {
					if(dataArr[0].equals("Guest")) {
						tmpSpieler.setNickName("Guest" + new Random().nextInt(100));
					}
				}
				else {
					int posSep2 = daten.indexOf(':');
                    String username = daten.substring(0, posSep2);
                    String passwordHash = daten.substring(posSep2+1);
					
					if(serverHead.connector.passwordCheck(username, passwordHash)){
                        tmpSpieler.setNickName(username);
                        String antwort = PROTOKOLL.SC_LOGINSTATUS + PROTOKOLL.SEPARATOR + "Success";
                        serverHead.send(pClientIP, pClientPort, antwort);
                    }
                    else{
                        String antwort = PROTOKOLL.SC_LOGINSTATUS + PROTOKOLL.SEPARATOR + "Failed";
                        serverHead.send(pClientIP, pClientPort, antwort);
                    }
                }
				
				}break;
		}
    }

    public String getHighscores(){
        String message = PROTOKOLL.SC_HIGHSCORELIST + PROTOKOLL.SEPARATOR;
        String[][] highscorelist = serverHead.connector.getHighscores();

        for(String [] row : highscorelist){
            message += "$" + row[0] + ":" + row[1];
        }

        return message;
    }


    
}

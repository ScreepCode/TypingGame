package game.client;

import game.PROTOKOLL;
import Netzklassen.Client;


public class ClientHead extends Client{

	ClientGUI gui;
	ClientLogin login;

	public ClientHead(String pServerIP, int pServerPort) {
		super(pServerIP, pServerPort);

		System.out.println("Client: \n\n");
		gui = new ClientGUI(this);
		login = new ClientLogin(this);

		gui.setBounds(10, 11, 734, 513);
		gui.setVisible(true);
		gui.setPanelLayout("login");
	}

	@Override
	public void processMessage(String pMessage) {
		System.out.println(pMessage);
// 		String message = "";
// 		String [] daten = pMessage.split(PROTOKOLL.SEPARATOR);
// 		String prefix = daten[0];
// 		switch (prefix) {
		
// //			###############LOGINPAGE#########################
// 			case PROTOKOLL.SC_PHASES: {			
// 				String phase = daten[1];
// 				switch(phase) {
// 					case "Login": {
// 						//GUI LOAD LOGIN -> After Login
// 						}break;
						
// 					case "Game": {
// 						//GUI LOAD GAME -> Start LOGIC
// 						}break;
						
// 					case "Ende": {
// 						//GUI LOAD ENDSCREEN -> 
// 						}break;
// 				}
// 			}break;
			
// 			case PROTOKOLL.SC_LOGINSTATUS: {
// 				String [] data = daten[1].split(":");
// 				if(data[0] == "Success") {
// 					//WECHSEL AUF WARTEBILDSCHIRM
					
					
// 				}
				
// 				else if(data[0] == "Failed") {
// 					String grund = data[1];
// 					//MITTTEILUNG PASSWORT FALSCH MIT GRUND
// 				}
				
				
// 				}break;
				
				
// //			###############LOBBYPAGE#########################
// 			case PROTOKOLL.SC_LOBBYLIST: {
// 				String [] allPlayers = daten[1].split("$");
// 				for(String player : allPlayers) {
// 					String username = player.split(":")[0];
// 					String highscore = player.split(":")[1];
					
// 					//F�GE SPIELEREINTRAG IN DER GUI HINZU
// 				}
				
// 				}break;
// 		}
	}
	

	
}

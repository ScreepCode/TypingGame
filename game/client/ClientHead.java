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
		int posSep1 = pMessage.indexOf(PROTOKOLL.SEPARATOR);
		String prefix = pMessage.substring(0, posSep1);
		String daten = pMessage.substring(posSep1+1);
		
		switch (prefix) {
		
//			###############LOGINPAGE#########################
			
			case PROTOKOLL.SC_LOGINSTATUS: {
				// String [] data = daten[1].split(":");
				if(daten.equals("Success")) {
					gui.setPanelLayout("lobby");
					send(PROTOKOLL.CS_ENTERLOBBY + PROTOKOLL.SEPARATOR + " ");
				}
				
				else if(daten.equals("Failed")) {
					System.out.println("Fehler in der Anmeldung/Registrierung");
				}
				}break;
				
				
//			###############LOBBYPAGE#########################
			// case PROTOKOLL.SC_LOBBYLIST: {
			// 	String [] allPlayers = daten.split("$");
			// 	for(String player : allPlayers) {
			// 		String username = player.split(":")[0];
			// 		String highscore = player.split(":")[1];
					
			// 		//F�GE SPIELEREINTRAG IN DER GUI HINZU
			// 	}
				
			// 	}break;
		}
	}
	

	
}
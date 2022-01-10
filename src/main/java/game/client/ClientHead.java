package game.client;

import game.PROTOKOLL;
import Netzklassen.Client;


public class ClientHead extends Client{

	ClientGUI gui;
	ClientLogin login;
	ClientLobby lobby;
	ClientGame game;
	ClientErgebnis ergebnis;

	public ClientHead(String pServerIP, int pServerPort) {
		super(pServerIP, pServerPort);

		System.out.println("Client: \n\n");
		gui = new ClientGUI(this);
		login = new ClientLogin(this);
		lobby = new ClientLobby(this);
		game = new ClientGame(this);
		ergebnis = new ClientErgebnis(this);

		gui.setBounds(10, 11, 800, 600);
		gui.setVisible(true);
		gui.setPanelLayout("login");
		gui.setFocusable(true);
		gui.addKeyListener(gui);

		send(PROTOKOLL.CS_REQUESTHIGHSCORELIST + PROTOKOLL.SEPARATOR + " ");
	}

	@Override
	public void processMessage(String pMessage) {
		System.out.println(pMessage);
		int posSep1 = pMessage.indexOf(PROTOKOLL.SEPARATOR);
		String prefix = pMessage.substring(0, posSep1);
		String daten = pMessage.substring(posSep1+1);
		
		switch (prefix) {
		
//			###############LOGINPAGE#########################
			case PROTOKOLL.SC_HIGHSCORELIST: {
				login.updateHighscoreList(daten);
			}break;

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
			case PROTOKOLL.SC_LOBBYLIST: {
				lobby.setLobbyList(daten);
				}break;

			case PROTOKOLL.SC_LOBBYSTATUS: {
				if(daten.equals("START")) {
					game.startGame();
				}
				else{
					lobby.refreshCounter(daten);
				}
				}break;

			case PROTOKOLL.SC_OWNRESULT: {
				ergebnis.show(daten);
			}break;

			case PROTOKOLL.SC_ROUNDRESULT: {
				ergebnis.updateScoreList(daten);
			}break;
		}
	}
	

	
}

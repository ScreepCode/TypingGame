package game.client;

import Netzklassen.Client;

import static game.PROTOKOLL.*;


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

		send(CS_REQUESTHIGHSCORELIST + SEPARATOR + " ");
	}

	@Override
	public void processMessage(String pMessage) {
		System.out.println(pMessage);
		int posSep1 = pMessage.indexOf(SEPARATOR);
		String prefix = pMessage.substring(0, posSep1);
		String daten = pMessage.substring(posSep1+1);

		switch (prefix) {

//			###############LOGINPAGE#########################
			case SC_HIGHSCORELIST -> {
				login.updateHighscoreList(daten);
			}
			case SC_LOGINSTATUS -> {
				// String [] data = daten[1].split(":");
				if (daten.startsWith("Success")) {
					gui.setTitle(gui.getTitle() + " --> " + daten.split(":")[1]);
					gui.setPanelLayout("lobby");
					send(CS_ENTERLOBBY + SEPARATOR + " ");
				} else if (daten.equals("Failed")) {
					System.out.println("Fehler in der Anmeldung/Registrierung");
				}
			}
			case SC_LOBBYLIST -> {
				lobby.setLobbyList(daten);
			}
			case SC_LOBBYSTATUS -> {
				if (daten.equals("START")) {
					game.startGame();
				} else {
					lobby.refreshCounter(daten);
				}
			}
			case SC_OWNRESULT -> {
				ergebnis.show(daten);
			}
			case SC_ROUNDRESULT -> {
				ergebnis.updateScoreList(daten);
			}
		}
	}
	

	
}

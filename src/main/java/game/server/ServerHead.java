package game.server;

import game.PROTOKOLL;
import Listenklassen.List;
import Netzklassen.*;

public class ServerHead extends Server{

	List<Spieler> spieler = new List<>();

	SQLiteConnector connector = new SQLiteConnector("database.db");
	ServerLogin login;
	ServerLobby lobby;
	ServerErgebnis ergebnis;
	
	public ServerHead(int pPort) {
		super(pPort);

		System.out.println("SERVER: \n\n");
		login = new ServerLogin(this);
		lobby = new ServerLobby(this);
		ergebnis = new ServerErgebnis(this);
	}
	

	@Override
	public void processNewConnection(String pClientIP, int pClientPort) {
		spieler.append(new Spieler(pClientIP, pClientPort));
		
		String message = PROTOKOLL.SC_NOTIFICATION + PROTOKOLL.SEPARATOR;
		this.send(pClientIP, pClientPort, message + "Willkommen bei meinem Spiel. Bitte wähle deinen Account aus");
	}

	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		System.out.println(pMessage);
		int posSep1 = pMessage.indexOf(PROTOKOLL.SEPARATOR);
		String prefix = pMessage.substring(0, posSep1);

		switch (prefix) {
			case PROTOKOLL.CS_REQUESTHIGHSCORELIST -> {
				this.send(pClientIP, pClientPort, login.getHighscores());
			}
			case PROTOKOLL.CS_ACC_LOGIN -> {
				login.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case PROTOKOLL.CS_ACC_CREATION -> {
				login.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case PROTOKOLL.CS_ENTERLOBBY -> {
				lobby.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case PROTOKOLL.CS_SETREADY -> {
				lobby.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case PROTOKOLL.CS_ENDEDGAME -> {
				ergebnis.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case PROTOKOLL.CS_SAVEHIGHSCORE -> {
				ergebnis.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
		}
	}

	@Override
	public void processClosingConnection(String pClientIP, int pClientPort) {		
		//Benutzer aus der Liste löschen
		for(spieler.toFirst();spieler.hasAccess(); spieler.next()){
			if(spieler.getContent().getIpAdresse().equals(pClientIP)&&spieler.getContent().getPort()==pClientPort){
				spieler.remove();
				lobby.removePlayerFromLobby();
			}
		}
		if(spieler.isEmpty()){
			ergebnis.updateScoreList();
		}
	}
	
	
	public Spieler spielerSuchen(String pClientIP, int pClientPort){
		for(spieler.toFirst();spieler.hasAccess(); spieler.next()){
			if(spieler.getContent().getIpAdresse().equals(pClientIP)&&spieler.getContent().getPort()==pClientPort){
				return spieler.getContent();
			}
		}
		return null;
	}

}

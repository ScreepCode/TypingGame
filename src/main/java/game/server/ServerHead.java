package game.server;

import Listenklassen.List;
import Netzklassen.Server;

import static game.PROTOKOLL.*;

public class ServerHead extends Server{

	protected List<Spieler> spieler = new List<>();

	protected SQLiteConnector connector = new SQLiteConnector("database.db");
	protected ServerLogin login;
	protected ServerLobby lobby;
	protected ServerErgebnis ergebnis;
	
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
		
		String message = SC_NOTIFICATION + SEPARATOR;
		this.send(pClientIP, pClientPort, message + "Willkommen bei meinem Spiel. Bitte wähle deinen Account aus");
	}

	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		System.out.println(pMessage);
		int posSep1 = pMessage.indexOf(SEPARATOR);
		String prefix = pMessage.substring(0, posSep1);

		switch (prefix) {
			case CS_REQUESTHIGHSCORELIST -> {
				this.send(pClientIP, pClientPort, login.getHighscores());
			}
			case CS_ACC_LOGIN -> {
				login.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case CS_ACC_CREATION -> {
				login.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case CS_ENTERLOBBY -> {
				lobby.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case CS_SETREADY -> {
				lobby.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case CS_ENDEDGAME -> {
				ergebnis.nachrichtenVerwaltung(pClientIP, pClientPort, pMessage);
			}
			case CS_SAVEHIGHSCORE -> {
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

package game.server;

import game.PROTOKOLL;
import Listenklassen.List;
import Netzklassen.*;

public class ServerHead extends Server{

	private List<Spieler> spieler = new List<Spieler>();
	
	public ServerHead(int pPort) {
		super(pPort);
	}
	

	@Override
	public void processNewConnection(String pClientIP, int pClientPort) {
		spieler.append(new Spieler(pClientIP, pClientPort));
		
		String message = PROTOKOLL.SC_NOTIFICATION + PROTOKOLL.SEPARATOR;
		this.send(pClientIP, pClientPort, message + "Willkommen bei meinem Spiel \nBitte wähle deinen Account aus");
	}

	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		String message = "";
		String [] daten = pMessage.split(PROTOKOLL.SEPARATOR);
		String prefix = daten[0];
		Spieler tmpSpieler = this.spielerSuchen(pClientIP, pClientPort);
		
		switch (prefix) {
			case PROTOKOLL.CS_ACC_LOGIN: {
				String [] dataArr = daten[1].split(":");
				if(dataArr.length == 1) {
					if(dataArr[0] == "Guest") {
						
					}
				}
				else {
					String username = dataArr[0];
					String password = dataArr[1];
					
					//HIER DATENBANK ÜBERPRÜFUNG
				}
				
				}break;
				
			case PROTOKOLL.CS_ACC_CREATION: {
				String [] dataArr = daten[1].split(":");
				String username = dataArr[0];
				String password = dataArr[1];
				
				//HIER DATENBANK EINFÜGEN
				
				}break;
		}
	}
	
	public void sendLobbyList() {
		String message = PROTOKOLL.SC_LOBBYLIST + PROTOKOLL.SEPARATOR;
		for(spieler.toFirst(); spieler.hasAccess(); spieler.next()) {
			String tmpMessage = "$" + spieler.getContent().getNickName() + ":" + spieler.getContent().getHighscore();
			message += tmpMessage;
		}
		sendToAll(message);
	}
	

	@Override
	public void processClosingConnection(String pClientIP, int pClientPort) {		
		//Benutzer aus der Liste löschen
		for(spieler.toFirst();spieler.hasAccess(); spieler.next()){
			if(spieler.getContent().getIpAdresse().equals(pClientIP)&&spieler.getContent().getPort()==pClientPort){
				spieler.remove();
			}
		}
	}
	
	
	private Spieler spielerSuchen(String pClientIP, int pClientPort){
		for(spieler.toFirst();spieler.hasAccess(); spieler.next()){
			if(spieler.getContent().getIpAdresse().equals(pClientIP)&&spieler.getContent().getPort()==pClientPort){
				return spieler.getContent();
			}
		}
		return null;
	}

}

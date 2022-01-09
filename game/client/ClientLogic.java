package game.client;

import game.PROTOKOLL;
import Netzklassen.Client;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClientLogic extends Client{

	public ClientLogic(String pServerIP, int pServerPort) {
		super(pServerIP, pServerPort);
	}

	@Override
	public void processMessage(String pMessage) {
		String message = "";
		String [] daten = pMessage.split(PROTOKOLL.SEPARATOR);
		String prefix = daten[0];
		switch (prefix) {
		
//			###############LOGINPAGE#########################
			case PROTOKOLL.SC_PHASES: {			
				String phase = daten[1];
				switch(phase) {
					case "Login": {
						//GUI LOAD LOGIN -> After Login
						}break;
						
					case "Game": {
						//GUI LOAD GAME -> Start LOGIC
						}break;
						
					case "Ende": {
						//GUI LOAD ENDSCREEN -> 
						}break;
				}
			}break;
			
			case PROTOKOLL.SC_LOGINSTATUS: {
				String [] data = daten[1].split(":");
				if(data[0] == "Success") {
					//WECHSEL AUF WARTEBILDSCHIRM
					
					
				}
				
				else if(data[0] == "Failed") {
					String grund = data[1];
					//MITTTEILUNG PASSWORT FALSCH MIT GRUND
				}
				
				
				}break;
				
				
//			###############LOBBYPAGE#########################
			case PROTOKOLL.SC_LOBBYLIST: {
				String [] allPlayers = daten[1].split("$");
				for(String player : allPlayers) {
					String username = player.split(":")[0];
					String highscore = player.split(":")[1];
					
					//F�GE SPIELEREINTRAG IN DER GUI HINZU
				}
				
				}break;
		}
	}
	
	public void accLogin(String name, String password) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(password.getBytes());
		String passwordHash = new String(messageDigest.digest());
		
		String message = PROTOKOLL.CS_ACC_LOGIN + PROTOKOLL.SEPARATOR + name + ":" + passwordHash;
		send(message);
	}
	
	public void accCreation(String name, String password) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(password.getBytes());
		String passwordHash = new String(messageDigest.digest());
		
		String message = PROTOKOLL.CS_ACC_CREATION + PROTOKOLL.SEPARATOR + name + ":" + passwordHash;
		send(message);
	}
	
	public void accLoginAsGuest(){
		String message = PROTOKOLL.CS_ACC_LOGIN + PROTOKOLL.SEPARATOR + "Guest";
		send(message);
	}
	
}

package game.client;

import game.PROTOKOLL;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ClientLogin {

    ClientHead clientHead;

    public ClientLogin(ClientHead clientHead) {
        this.clientHead = clientHead;

    }
    

    public void accLogin(String name, String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            String passwordHash = new String(messageDigest.digest(), StandardCharsets.UTF_8);
		
            String message = PROTOKOLL.CS_ACC_LOGIN + PROTOKOLL.SEPARATOR + name + ":" + passwordHash;
            clientHead.send(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void accCreation(String name, String password){
		try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            String passwordHash = new String(messageDigest.digest(), StandardCharsets.UTF_8);
            
            String message = PROTOKOLL.CS_ACC_CREATION + PROTOKOLL.SEPARATOR + name + ":" + passwordHash;
            clientHead.send(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void accLoginAsGuest(){
		String message = PROTOKOLL.CS_ACC_LOGIN + PROTOKOLL.SEPARATOR + "Guest";
		clientHead.send(message);
	}

    public void updateHighscoreList(String data){
        clientHead.gui.dTMHHSc.setRowCount(0);
        clientHead.gui.dTMHHSc.setColumnCount(0);

        clientHead.gui.dTMHHSc.addColumn("Username");
        clientHead.gui.dTMHHSc.addColumn("Highscore");

        String [] playerArr = data.split("$");
        for(String player : playerArr){
            String [] playerData = player.split(":");
            String username = playerData[0];
            String score = playerData[1];

            clientHead.gui.dTMHHSc.addRow(playerData);
        }
    }
}

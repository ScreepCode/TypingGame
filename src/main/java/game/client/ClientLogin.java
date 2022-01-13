package game.client;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static game.PROTOKOLL.*;

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
		
            String message = CS_ACC_LOGIN + SEPARATOR + name + ":" + passwordHash;
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
            
            String message = CS_ACC_CREATION + SEPARATOR + name + ":" + passwordHash;
            clientHead.send(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void accLoginAsGuest(){
		String message = CS_ACC_LOGIN + SEPARATOR + "Guest";
		clientHead.send(message);
	}

    public void updateHighscoreList(String data){
        clientHead.gui.dTMHHSc.setRowCount(0);
        clientHead.gui.dTMHHSc.setColumnCount(0);

        clientHead.gui.dTMHHSc.addColumn("Username");
        clientHead.gui.dTMHHSc.addColumn("Highscore");
        System.out.println(data);
        String [] playerArr = data.split(";");
        for(String player : playerArr){
            String [] playerData = player.split(":");

            clientHead.gui.dTMHHSc.addRow(playerData);
        }
    }
}

package game.client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import game.PROTOKOLL;

public class ClientLogin {

    ClientHead clientHead;

    public ClientLogin(ClientHead clientHead) {
        this.clientHead = clientHead;

    }
    

    public void accLogin(String name, String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            String passwordHash = new String(messageDigest.digest());
		
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
            String passwordHash = new String(messageDigest.digest());
            
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
}

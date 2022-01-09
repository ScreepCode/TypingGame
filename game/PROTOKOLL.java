package game;

public class PROTOKOLL {
	// Client -> Server
	public static final String CS_ACC_LOGIN = "ACC_AN";
	public static final String CS_ACC_CREATION = "ACC_ER";
	public static final String CS_ENTERLOBBY = "ENTLOB";
	public static final String CS_SETREADY = "READY";
	
	
	// Server -> Client
	public static final String SC_NOTIFICATION = "N"; 		
	public static final String SC_LOGINSTATUS = "LS";
	public static final String SC_LOBBYSTATUS = "LOBSTAT";	
	public static final String SC_LOBBYLIST = "WL";								
	
	// Trenner
	public static final String SEPARATOR = "#";
}

package game;

public class PROTOKOLL {
	// Client -> Server
	public static final String CS_REQUESTHIGHSCORELIST = "RHSL";
	public static final String CS_ACC_LOGIN = "ACC_AN";
	public static final String CS_ACC_CREATION = "ACC_ER";
	public static final String CS_ENTERLOBBY = "ENTLOB";
	public static final String CS_SETREADY = "READY";
	public static final String CS_ENDEDGAME = "ENDGAME";
	public static final String CS_SAVEHIGHSCORE = "SH";
	
	
	// Server -> Client
	public static final String SC_HIGHSCORELIST = "HSL";
	public static final String SC_NOTIFICATION = "N"; 		
	public static final String SC_LOGINSTATUS = "LS";
	public static final String SC_LOBBYSTATUS = "LOBSTAT";	
	public static final String SC_LOBBYLIST = "WL";
	public static final String SC_OWNRESULT = "OR";
	public static final String SC_ROUNDRESULT = "RR";

	// Trenner
	public static final String SEPARATOR = "#";
}

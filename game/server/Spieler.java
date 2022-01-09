package game.server;

public class Spieler {
	private String ipAdresse;
	private int port; 
	private String nickName; 
	private double highscore;

	public Spieler(String ipAdresse, int port) {
		this.ipAdresse = ipAdresse;
		this.port = port;
		this.nickName="";
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIpAdresse() {
		return ipAdresse;
	}

	public int getPort() {
		return port;
	}
	
	
	public double getHighscore() {
		return highscore;
	}

	public void setHighscore(double highscore) {
		this.highscore = highscore;
	}
}

package game.server;

public class Spieler {
	private String ipAdresse;
	private int port; 
	private String nickName; 
	private double highscore;
	private Boolean readyStatus;

	public Spieler(String ipAdresse, int port) {
		this.ipAdresse = ipAdresse;
		this.port = port;
		this.nickName = "";
		this.highscore = 0.0;
		this.readyStatus = false;
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

	public Boolean getReadyStatus() {
		return readyStatus;
	}

	public void setReadyStatus(Boolean readyStatus) {
		this.readyStatus = readyStatus;
	}
}

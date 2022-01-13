package game.server;

import java.util.Random;

import static game.PROTOKOLL.*;

public class ServerLogin {

    private final ServerHead serverHead;

    public ServerLogin(ServerHead serverHead) {
        this.serverHead = serverHead;
    }

    public void nachrichtenVerwaltung(String pClientIP, int pClientPort, String pMessage){
        int posSep1 = pMessage.indexOf(SEPARATOR);
		String prefix = pMessage.substring(0, posSep1);
        String daten =  pMessage.substring(posSep1+1);

        Spieler tmpSpieler = serverHead.spielerSuchen(pClientIP, pClientPort);

        switch (prefix) {
            case CS_ACC_CREATION -> {
                int posSep2 = daten.indexOf(':');
                String username = daten.substring(0, posSep2);
                String passwordHash = daten.substring(posSep2 + 1);

                try {
                    Boolean exists = serverHead.connector.checkIfAccountnameExists(username);
                    if (exists) {
                        String antwort = SC_LOGINSTATUS + SEPARATOR + "Failed" + ": Nutzername existiert bereits";
                        serverHead.send(pClientIP, pClientPort, antwort);
                    } else {
                        serverHead.connector.createAccount(username, passwordHash);
                        tmpSpieler.setNickName(username);
                        String antwort = SC_LOGINSTATUS + SEPARATOR + "Success:" + tmpSpieler.getNickName();
                        serverHead.send(pClientIP, pClientPort, antwort);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    String antwort = SC_LOGINSTATUS + SEPARATOR + "Failed" + ": Unbekannter Fehler aufgetreten";
                    serverHead.send(pClientIP, pClientPort, antwort);
                }

            }
            case CS_ACC_LOGIN -> {
                String[] dataArr = daten.split(":");
                if (dataArr.length == 1) {
                    if (dataArr[0].equals("Guest")) {
                        tmpSpieler.setNickName("Guest" + new Random().nextInt(100));
                        String antwort = SC_LOGINSTATUS + SEPARATOR + "Success:" + tmpSpieler.getNickName();
                        serverHead.send(pClientIP, pClientPort, antwort);
                    }
                } else {
                    int posSep2 = daten.indexOf(':');
                    String username = daten.substring(0, posSep2);
                    String passwordHash = daten.substring(posSep2 + 1);

                    if (serverHead.connector.passwordCheck(username, passwordHash)) {
                        tmpSpieler.setNickName(username);
                        String antwort = SC_LOGINSTATUS + SEPARATOR + "Success:" + tmpSpieler.getNickName();
                        serverHead.send(pClientIP, pClientPort, antwort);
                    } else {
                        String antwort = SC_LOGINSTATUS + SEPARATOR + "Failed";
                        serverHead.send(pClientIP, pClientPort, antwort);
                    }
                }

            }
        }
    }

    public String getHighscores(){
        String message = SC_HIGHSCORELIST + SEPARATOR;
        String[][] highscorelist = serverHead.connector.getHighscores();

        for(String [] row : highscorelist){
            message += row[0] + ":" + row[1] + ";";
        }
        return message;
    }
}

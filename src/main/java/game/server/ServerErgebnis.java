package game.server;

import Listenklassen.List;
import game.PROTOKOLL;

public class ServerErgebnis {

    ServerHead serverHead;

    public ServerErgebnis(ServerHead serverHead) {
        this.serverHead = serverHead;
    }

    public void nachrichtenVerwaltung(String pClientIP, int pClientPort, String pMessage) {
        int posSep1 = pMessage.indexOf(PROTOKOLL.SEPARATOR);
        String prefix = pMessage.substring(0, posSep1);
        String daten =  pMessage.substring(posSep1+1);

        Spieler tmpSpieler = serverHead.spielerSuchen(pClientIP, pClientPort);

        switch (prefix) {
            case PROTOKOLL.CS_ENDEDGAME: {
                String[] dataArr = daten.split(":");
                int keys = Integer.parseInt(dataArr[0]);
                int errors = Integer.parseInt(dataArr[1]);

                //Ergebnisse:
                double resultKeys = keys;
                double resultAPM = (resultKeys*60)/120;
                double resultErrors = errors;
                String resultRechnung = "" + (int)resultKeys + "*(1-(" + (int)resultErrors + "/" + (int)resultKeys + "))";
                double resultPunktzahl = (resultKeys*(1-(resultErrors/resultKeys)));

                String message = PROTOKOLL.SC_OWNRESULT + PROTOKOLL.SEPARATOR + (int)resultKeys + ":" + (int)resultAPM + ":" + (int)resultErrors + ":" + resultRechnung + ":" + (int)resultPunktzahl + ":" + (tmpSpieler.getHighscore() < resultPunktzahl);
                serverHead.send(pClientIP, pClientPort, message);

                tmpSpieler.setHighscore(resultPunktzahl);
                tmpSpieler.setScore(resultPunktzahl);

                updateScoreList();
            }
            break;
            case PROTOKOLL.CS_SAVEHIGHSCORE: {
                serverHead.connector.saveHighscore(tmpSpieler.getNickName(), (int)tmpSpieler.getHighscore());
            }
            break;
        }
    }

    public void updateScoreList(){
        List<Spieler> playedPlayer = serverHead.spieler;
        SortLists.sortPlayerByScore(serverHead.spieler, "score");

        String message = PROTOKOLL.SC_ROUNDRESULT + PROTOKOLL.SEPARATOR;
        for(playedPlayer.toFirst(); playedPlayer.hasAccess(); playedPlayer.next()){
            message += playedPlayer.getContent().getNickName() + ":" + (int)playedPlayer.getContent().getScore() + "§";
        }
        serverHead.sendToAll(message);
    }
}

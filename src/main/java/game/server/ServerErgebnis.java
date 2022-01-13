package game.server;

import Listenklassen.List;

import static game.PROTOKOLL.*;

public class ServerErgebnis {

    private final ServerHead serverHead;

    public ServerErgebnis(ServerHead serverHead) {
        this.serverHead = serverHead;
    }

    public void nachrichtenVerwaltung(String pClientIP, int pClientPort, String pMessage) {
        int posSep1 = pMessage.indexOf(SEPARATOR);
        String prefix = pMessage.substring(0, posSep1);
        String daten =  pMessage.substring(posSep1+1);

        Spieler tmpSpieler = serverHead.spielerSuchen(pClientIP, pClientPort);

        switch (prefix) {
            case CS_ENDEDGAME -> {
                String[] dataArr = daten.split(":");

                //Ergebnisse:
                double resultKeys = Double.parseDouble(dataArr[0]);
                double resultErrors = Double.parseDouble(dataArr[1]);
                double resultAPM = (resultKeys * 60) / 120;
                String resultRechnung = "" + (int) resultKeys + "*(1-(" + (int) resultErrors + "/" + (int) resultKeys + "))";
                double resultPunktzahl = (resultKeys * (1 - (resultErrors / resultKeys)));

                String message = SC_OWNRESULT + SEPARATOR + (int) resultKeys + ":" + (int) resultAPM + ":" + (int) resultErrors + ":" + resultRechnung + ":" + (int) resultPunktzahl + ":" + (tmpSpieler.getHighscore() < resultPunktzahl);
                serverHead.send(pClientIP, pClientPort, message);

                tmpSpieler.setHighscore(resultPunktzahl);
                tmpSpieler.setScore(resultPunktzahl);

                updateScoreList();
            }
            case CS_SAVEHIGHSCORE -> {
                serverHead.connector.saveHighscore(tmpSpieler.getNickName(), (int) tmpSpieler.getHighscore());
            }
        }
    }

    public void updateScoreList(){
        List<Spieler> playedPlayer = serverHead.spieler;
        SortLists.sortPlayerByScore(serverHead.spieler, "score");

        String message = SC_ROUNDRESULT + SEPARATOR;
        for(playedPlayer.toFirst(); playedPlayer.hasAccess(); playedPlayer.next()){
            message += playedPlayer.getContent().getNickName() + ":" + (int)playedPlayer.getContent().getScore() + ";";
        }
        serverHead.sendToAll(message);
    }
}

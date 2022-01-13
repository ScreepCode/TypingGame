package game.server;

import Listenklassen.List;

public class SortLists {

    static public List<Spieler> sortPlayerByScore(List<Spieler> playerList, String whichScore){
        playerList.toFirst();

        for(int i = 0; i<length(playerList); i++) {
            for (int j = i; j < length(playerList); j++) {
                goTo(playerList, i);
                double scoreA;
                if(whichScore.equals("Highscore")){
                    scoreA = playerList.getContent().getHighscore();
                }
                else{
                    scoreA = playerList.getContent().getScore();
                }
                goTo(playerList, j);

                double scoreB;
                if(whichScore.equals("Highscore")){
                    scoreB = playerList.getContent().getHighscore();
                }
                else{
                    scoreB = playerList.getContent().getScore();
                }
                if (scoreA < scoreB) {
                    swapInt(playerList, i, j);
                }
            }
        }
        return playerList;
    }

    static private void swapInt(List<Spieler> list, int firstPos, int secondPos) {
        goTo(list, firstPos);
        Spieler first = list.getContent();

        goTo(list, secondPos);
        Spieler second = list.getContent();

        goTo(list, firstPos);
        list.setContent(second);

        goTo(list, secondPos);
        list.setContent(first);
    }

    static private void goTo (List<Spieler> list, int counter) {
        list.toFirst();
        for(int i = 0; i < counter; i++) {
            list.next();
        }
    }

    static private int length(List<Spieler> list) {
        int len = 0;
        for(list.toFirst();list.hasAccess();list.next()) {
            len++;
        }
        return len;
    }
}

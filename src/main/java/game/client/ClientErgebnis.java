package game.client;

import game.PROTOKOLL;

public class ClientErgebnis {

    ClientHead clientHead;

    public ClientErgebnis(ClientHead clientHead) {
        this.clientHead = clientHead;

    }

    public void show(String daten){
        String [] dataArr = daten.split(":");
        String resultKeys = dataArr[0];
        String resultAPM = dataArr[1];
        String resultErrors = dataArr[2];
        String resultRechnung = dataArr[3];
        String resultPunktzahl = dataArr[4];
        boolean higherRekord = Boolean.parseBoolean(dataArr[5]);

        clientHead.gui.textFieldAnschlaege.setText(resultKeys);
        clientHead.gui.textFieldAnschlaegePM.setText(resultAPM);
        clientHead.gui.textFieldFehler.setText(resultErrors);
        clientHead.gui.textFieldRechnung.setText(resultRechnung);
        clientHead.gui.textFieldErgebnis.setText(resultPunktzahl);

        if(higherRekord){
            clientHead.gui.btnRekordUpload.setEnabled(true);
        }

        clientHead.gui.setPanelLayout("ergebnis");
    }

    public void uploadRecord(){
        clientHead.send(PROTOKOLL.CS_SAVEHIGHSCORE + PROTOKOLL.SEPARATOR + "1");
    }

    public void updateScoreList(String data){
        clientHead.gui.dTMSc.setRowCount(0);
        clientHead.gui.dTMSc.setColumnCount(0);

        clientHead.gui.dTMSc.addColumn("Username");
        clientHead.gui.dTMSc.addColumn("Score");

        String [] playerArr = data.split("$");
        for(String player : playerArr){
            String [] playerData = player.split(":");
            String username = playerData[0];
            String score = playerData[1];

            clientHead.gui.dTMSc.addRow(playerData);
        }
    }
}

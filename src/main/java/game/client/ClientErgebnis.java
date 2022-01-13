package game.client;

import static game.PROTOKOLL.*;

public class ClientErgebnis {

    private final ClientHead clientHead;

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
        clientHead.send(CS_SAVEHIGHSCORE + SEPARATOR + "1");
    }

    public void updateScoreList(String data){
        clientHead.gui.dTMSc.setRowCount(0);
        clientHead.gui.dTMSc.setColumnCount(0);

        clientHead.gui.dTMSc.addColumn("Username");
        clientHead.gui.dTMSc.addColumn("Score");

        String [] playerArr = data.split(";");
        for(String player : playerArr){
            String [] playerData = player.split(":");

            clientHead.gui.dTMSc.addRow(playerData);
        }
    }
}

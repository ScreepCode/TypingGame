package game.client;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import static game.PROTOKOLL.*;

public class ClientLobby {

    private final ClientHead clientHead;

    private Boolean readyStatus = false;

    public ClientLobby(ClientHead clientHead) {
        this.clientHead = clientHead;
    }

    public void refreshCounter(String data){
        clientHead.gui.lblReadyCounter.setText("Bereit: " + data);
    }
    
    public void setReady(){
        if(readyStatus){
            readyStatus = false;
            clientHead.send(CS_SETREADY + SEPARATOR + "0");
        }
        else{
            readyStatus = true;
            clientHead.send(CS_SETREADY + SEPARATOR + "1");
        }
    }

    public void setLobbyList(String daten){
        clientHead.gui.dTMLobby.setRowCount(0);
        clientHead.gui.dTMLobby.setColumnCount(0);

        clientHead.gui.dTMLobby.addColumn("Username");
        clientHead.gui.dTMLobby.addColumn("Highscore");
        clientHead.gui.dTMLobby.addColumn("Ready?");

        String [] allPlayers = daten.split(";");
        for(String player : allPlayers) {
            String [] playerData = player.split(":");
            if(playerData[1].equals("0")){
                playerData[1] = "---";
            }
            playerData[2] = (playerData[2].equals("true") ? "Ja" : "Nein");
            clientHead.gui.dTMLobby.addRow(playerData);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            clientHead.gui.lobbyTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            clientHead.gui.lobbyTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            clientHead.gui.lobbyTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        }
    }

}

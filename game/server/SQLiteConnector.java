package game.server;

import Datenbankklassen.*;

public class SQLiteConnector{

    DatabaseConnector con;
    String dbName;

    public SQLiteConnector(String dbName){
        this.dbName = dbName;
    }

    public void startConnection(){
        con = new DatabaseConnector("", 0, dbName, "", "");
    }

}
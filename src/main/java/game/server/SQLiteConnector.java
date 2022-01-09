package game.server;

import Datenbankklassen.*;

public class SQLiteConnector{

    DatabaseConnector con;
    String dbName;

    public SQLiteConnector(String dbName){
        this.dbName = dbName;
        con = new DatabaseConnector("", 0, dbName, "", "");
    }

    public void createAccount(String username, String passwordHash){
        String command = "INSERT INTO USER (Username, Password) VALUES ('"+ username +"', '" + passwordHash + "')";
        con.executeStatement(command);
    }

    public boolean passwordCheck(String username, String passwordHash){
        con.executeStatement("SELECT Password FROM USER WHERE Username = '" + username +"'");
        QueryResult result = con.getCurrentQueryResult();
        return passwordHash.equals(result.getData()[0][0]);
    }

}
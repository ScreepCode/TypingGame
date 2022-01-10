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
            String command = "INSERT INTO USER (Username, Password, Highscore) VALUES ('"+ username +"', '" + passwordHash + "', 0)";
            con.executeStatement(command);
    }

    public Boolean checkIfAccountnameExists(String username){
        con.executeStatement("SELECT * FROM USER WHERE Username = '" + username +"'");
        return (con.getCurrentQueryResult().getData().length != 0);
    }

    public boolean passwordCheck(String username, String passwordHash){
        con.executeStatement("SELECT Password FROM USER WHERE Username = '" + username +"'");
        QueryResult result = con.getCurrentQueryResult();
        return passwordHash.equals(result.getData()[0][0]);
    }

    public void saveHighscore(String username, int highscore){
        con.executeStatement("UPDATE User SET Highscore = " + highscore + " WHERE Username = '" + username + "'");
    }

    public String[][] getHighscores(){
        con.executeStatement("SELECT Username, Highscore FROM User ORDER BY Highscore DESC LIMIT 5");
        return con.getCurrentQueryResult().getData();
    }

    public double getHighscore(String username){
        con.executeStatement("SELECT Highscore FROM User WHERE Username = '" + username +"'");
        return Double.parseDouble(con.getCurrentQueryResult().getData()[0][0]);
    }

}
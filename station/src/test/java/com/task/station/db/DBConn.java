package com.task.station.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConn 
{
	
	private static DBConn instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/iheart";
    private String username = "root";
    private String password = "";
    private Statement stmt;

    private DBConn() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            this.stmt = connection.createStatement();
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConn getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConn();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBConn();
        }

        return instance;
    }
    
    public Statement getStatement()
    {
    	return stmt;
    }
    
    
	public static ResultSet runSQLSelect(String querry) 
    {
    	try 
    	{
    		
			DBConn dbConn = DBConn.getInstance();
			Statement stmt = dbConn.getStatement();
			ResultSet rs = stmt.executeQuery(querry);
			
			return rs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
     	
    }
    
    public void runSQLDelete(String querry)
    {
    	
    }
    
    public static void main(String args[])
	{
		DBConn.runSQLSelect("Select * from station");
		
	}

    
	
}

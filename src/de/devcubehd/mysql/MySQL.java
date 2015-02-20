package de.devcubehd.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL 
{
	
	public static String host = "localhost";
	public static String port = "3306";
	public static String database = "McPlanetsSTATS";
	public static String username = "root";
	public static String password = "TeamTerra";
	public static Connection con;
	
	public static void connect()
	{
		
		try 
		{
			
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			System.out.println("Verbindung zu der Datenbank " + database + " wurde aufgebaut!");
			
		} 
		catch (SQLException e)
		{
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void disconnect()
	{
		
		if(isConnected())
		{
			
			try 
			{
				
				con.close();
				System.out.println("Verbindung zu der Datenbank " + database + " wurde geschlossen!");
				
			} 
			catch (SQLException e) 
			{
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
	public static boolean isConnected()
	{
		
		return(con == null ? false : true);

	}
	
	public static Connection getConnection()
	{
		
		return con;
		
	}

}

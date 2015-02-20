package de.devcubehd.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PunkteManager 
{
	
	public static boolean isUserExisting(UUID uuid)
	{
		
		try 
		{
			
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Punkte FROM PunkteDatenbank WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			return rs.next();
			
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
			
		}
		
		return false;
		
	}
	
	public static void update(UUID uuid, int amount, boolean remove, String name)
	{
		
		int points = getPoints(uuid);
		
		if(remove)
		{
			
			amount = points - amount;
			
		}
		else
		{
			
			amount = amount + points;
			
		}
				
		if(isUserExisting(uuid))
		{
			
			try 
			{
				
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE PunkteDatenbank SET Punkte = ? WHERE UUID = ?");
				ps.setInt(1, amount);
				ps.setString(2, uuid.toString());
				ps.executeUpdate();
				
			} 
			catch (SQLException e) 
			{
				
				e.printStackTrace();
				
			}
			
		}
		else
		{
			
			try 
			{
				
				PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO PunkteDatenbank (UUID, Name, Punkte) VALUES (?, ?, ?)");
				ps.setString(1, uuid.toString());
				ps.setString(2, name);
				ps.setInt(3, amount);
				ps.executeUpdate();
				
			} 
			catch (SQLException e) 
			{
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
	public static void delete(UUID uuid)
	{
		
		if(isUserExisting(uuid))
		{
			
			try 
			{
				
				PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE * FROM PunkteDatenbank WHERE UUID = ?");
				ps.setString(1, uuid.toString());
				ps.executeUpdate();
				
			} 
			catch (SQLException e) 
			{
				
				e.printStackTrace();
				
			}
			
		}
		else
		{
			
			System.err.println("Der Spieler befindet sich nicht in der Datenbank");
			
		}
		
	}
	
	public static Integer getPoints(UUID uuid)
	{
		
		try 
		{
			
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Punkte FROM PunkteDatenbank WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				
				return rs.getInt("Punkte");
				
			}
			
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
			
		}
		
		return -1;
		
	}

}

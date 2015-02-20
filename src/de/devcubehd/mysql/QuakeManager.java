package de.devcubehd.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuakeManager 
{
	
	public static boolean isUserExisting(String uuid)
	{
		
		try 
		{
			
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT equiped FROM QuakeDatenbank WHERE Name = ?");
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			return rs.next();
			
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
			
		}
		
		return false;
		
	}
	
	public static void update(String uuid, String name, String equiped, boolean wood, boolean stone, boolean iron, boolean diamond)
	{
						
		if(isUserExisting(uuid))
		{
			
			try 
			{
				
				PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE QuakeDatenbank equiped = ?, wood = ?, stone = ?, iron = ?, diamond = ? WHERE Name = ?");
				ps.setString(1, equiped);
				
				if(wood)
				{
					
					ps.setInt(2, 1);
					ps.setString(6, uuid.toString());
					
				}
				else
				{
					
					ps.setInt(2, 0);
					ps.setString(6, uuid.toString());
					
				}
				
				if(stone)
				{
					
					ps.setInt(3, 1);
					ps.setString(6, uuid.toString());
					
				}
				else
				{
					
					ps.setInt(3, 0);
					ps.setString(6, uuid.toString());
					
				}
				
				if(iron)
				{
					
					ps.setInt(4, 1);
					ps.setString(6, uuid.toString());
					
				}
				else
				{
					
					ps.setInt(4, 0);
					ps.setString(6, uuid.toString());
					
				}
				
				if(diamond)
				{
					
					ps.setInt(5, 1);
					ps.setString(6, uuid.toString());
					
				}
				else
				{
					
					ps.setInt(5, 0);
					ps.setString(6, uuid.toString());
					
				}
				
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
	
	public static void delete(String uuid)
	{
		
		if(isUserExisting(uuid))
		{
			
			try 
			{
				
				PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE * FROM QuakeDatenbank WHERE UUID = ?");
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
	
	public static Boolean getHOE(String uuid, String hoe)
	{
		
		boolean bool = false;
		
		if(isUserExisting(uuid))
		{
			
			if(hoe.equalsIgnoreCase("wood"))
			{
				
				bool = getWood(uuid);
				
			}
			else
			if(hoe.equalsIgnoreCase("stone"))
			{
				
				bool = getStone(uuid);
				
			}
			else
			if(hoe.equalsIgnoreCase("iron"))
			{
				
				bool = getIron(uuid);
				
			}
			else
			if(hoe.equalsIgnoreCase("diamond"))
			{
				
				bool = getDiamond(uuid);
				
			}
			
		}
		
		//System.out.println("FEHLER DA ISN SPIELER DER NIT EINGETRAGEN IST! CHECK MAL OB DA VLLT NE ANDERE UUID WEGEN OFFLINE DINGENS HAT! DANN ÄNDER ABFRAGE VON NAMEN UND UPDATE DES NAMENS ON JOIN DANGE!");
		
		return bool;
		
	}
	
	public static Boolean getWood(String name)
	{
		
		boolean bool = false;
		String obj = "wood";
		
		try 
		{
						
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT wood FROM QuakeDatenbank WHERE Name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{

				if(rs.getInt(obj) == 1)
				{
					
					bool = true;
					
				}
				else
				if(rs.getInt(obj) == 0)
				{
						
					bool = false;
						
				}

			}
			
		}
		catch (SQLException e) 
		{
			
			e.printStackTrace();
			
		}
		return bool;
		
	}
	
	public static Boolean getStone(String name)
	{
		
		boolean bool = false;
		String obj = "stone";
		
		try 
		{
						
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT stone FROM QuakeDatenbank WHERE Name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{

				if(rs.getInt(obj) == 1)
				{
					
					bool = true;
					
				}
				else
				if(rs.getInt(obj) == 0)
				{
						
					bool = false;
						
				}

			}
			
		}
		catch (SQLException e) 
		{
			
			e.printStackTrace();
			
		}
		return bool;
		
	}
	
	public static Boolean getIron(String name)
	{
		
		boolean bool = false;
		String obj = "iron";
		
		try 
		{
						
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT iron FROM QuakeDatenbank WHERE Name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{

				if(rs.getInt(obj) == 1)
				{
					
					bool = true;
					
				}
				else
				if(rs.getInt(obj) == 0)
				{
						
					bool = false;
						
				}

			}
			
		}
		catch (SQLException e) 
		{
			
			e.printStackTrace();
			
		}
		return bool;
		
	}
	
	public static Boolean getDiamond(String name)
	{
		
		boolean bool = false;
		String obj = "diamond";
		
		try 
		{
						
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT diamond FROM QuakeDatenbank WHERE Name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{

				if(rs.getInt(obj) == 1)
				{
					
					bool = true;
					
				}
				else
				if(rs.getInt(obj) == 0)
				{
						
					bool = false;
						
				}

			}
			
		}
		catch (SQLException e) 
		{
			
			e.printStackTrace();
			
		}
		return bool;
		
	}
	
	public static String getSelected(String name)
	{
		
		String hoe = null;
		
		try 
		{
						
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT equiped FROM QuakeDatenbank WHERE Name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{

				hoe = rs.getString("equiped");

			}
			
		}
		catch (SQLException e) 
		{
			
			e.printStackTrace();
			
		}
		return hoe;
		
	}

}

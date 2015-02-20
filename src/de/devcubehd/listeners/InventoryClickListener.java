package de.devcubehd.listeners;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.devcubehd.main.McPlanetsQUAKE;
import de.devcubehd.mysql.MySQL;
import de.devcubehd.mysql.QuakeManager;

public class InventoryClickListener implements Listener
{
	
	private McPlanetsQUAKE plugin;

	public InventoryClickListener(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		this.plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
		
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e)
	{
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getInventory() == null)
		{
			
			return;
			
		}
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
		{
			
			return;
			
		}
		
		if(!p.getGameMode().equals(GameMode.CREATIVE))
		{
			
			e.setCancelled(true);
			
		}

		if(e.getInventory().getName().equalsIgnoreCase("Wähle eine Karte:"))
		{
			
			if(!plugin.voted.contains(p) && !e.getCurrentItem().getType().equals(Material.AIR))
			{
				
				e.setCancelled(true);
				ItemStack item = plugin.vote.getItem(e.getSlot());
				plugin.voted.add(p);
				p.closeInventory();
				item.setAmount(e.getCurrentItem().getAmount()+1);
				p.sendMessage(plugin.accept + "Du hast erfolgreich abgestimmt!");
				
			} 
			else
			{
				
				if(!e.getCurrentItem().equals(null))
				{
					
					p.sendMessage(plugin.decline + "Du kannst nur einmal abstimmen!");
					p.closeInventory();
					
				}
				
			}
			
		}
		
		if(e.getInventory().getName().equalsIgnoreCase("Gun Selector"))
		{
	
			e.setCancelled(true);
			
			if(e.getCurrentItem().getType().equals(Material.WOOD_HOE))
			{
				
				if(QuakeManager.getHOE(p.getName(), "wood"))
				{
					
					if(QuakeManager.getSelected(p.getName()).equalsIgnoreCase("wood"))
					{
						
						p.sendMessage(plugin.decline + "Du hast diese Waffe bereits ausgewählt!");
						return;
						
					}
					
					try 
					{
						
						PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE QuakeDatenbank SET equiped = ? WHERE Name = ?");
						ps.setString(1, "wood");
						ps.setString(2, p.getName());
						ps.executeUpdate();

					} 
					catch (SQLException e1) 
					{
						
						p.sendMessage(plugin.decline);
						e1.printStackTrace();
						
					}
					
					p.closeInventory();
					p.sendMessage(plugin.accept + e.getCurrentItem().getItemMeta().getDisplayName() + " wurde ausgewählt!");
					
				}
				else
				if(!QuakeManager.getHOE(p.getName(), "wood"))
				{
					
					p.sendMessage(plugin.decline + e.getCurrentItem().getItemMeta().getDisplayName() + " hast du noch nicht gekauft!");
					
				}
				
			}
			
			if(e.getCurrentItem().getType().equals(Material.STONE_HOE))
			{
				
				if(QuakeManager.getHOE(p.getName(), "stone"))
				{
					
					if(QuakeManager.getSelected(p.getName()).equalsIgnoreCase("stone"))
					{
						
						p.sendMessage(plugin.decline + "Du hast diese Waffe bereits ausgewählt!");
						return;
						
					}
					
					try 
					{
						
						PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE QuakeDatenbank SET equiped = ? WHERE Name = ?");
						ps.setString(1, "stone");
						ps.setString(2, p.getName());
						ps.executeUpdate();

					} 
					catch (SQLException e1) 
					{
						
						p.sendMessage(plugin.decline);
						e1.printStackTrace();
						
					}
					
					p.closeInventory();
					p.sendMessage(plugin.accept + e.getCurrentItem().getItemMeta().getDisplayName() + " wurde ausgewählt!");
					
				}
				else
				if(!QuakeManager.getHOE(p.getName(), "stone"))
				{
					
					p.sendMessage(plugin.decline + e.getCurrentItem().getItemMeta().getDisplayName() + " hast du noch nicht gekauft!");
					
				}
				
			}
			
			if(e.getCurrentItem().getType().equals(Material.IRON_HOE))
			{
				
				if(QuakeManager.getHOE(p.getName(), "iron"))
				{
					
					if(QuakeManager.getSelected(p.getName()).equalsIgnoreCase("iron"))
					{
						
						p.sendMessage(plugin.decline + "Du hast diese Waffe bereits ausgewählt!");
						return;
						
					}
					
					try 
					{
						
						PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE QuakeDatenbank SET equiped = ? WHERE Name = ?");
						ps.setString(1, "iron");
						ps.setString(2, p.getName());
						ps.executeUpdate();

					} 
					catch (SQLException e1) 
					{
						
						p.sendMessage(plugin.decline);
						e1.printStackTrace();
						
					}
					
					p.closeInventory();
					p.sendMessage(plugin.accept + e.getCurrentItem().getItemMeta().getDisplayName() + " wurde ausgewählt!");
					
				}
				else
				if(!QuakeManager.getHOE(p.getName(), "iron"))
				{
					
					p.sendMessage(plugin.decline + e.getCurrentItem().getItemMeta().getDisplayName() + " hast du noch nicht gekauft!");
					
				}
				
			}

			if(e.getCurrentItem().getType().equals(Material.DIAMOND_HOE))
			{
				
				if(QuakeManager.getHOE(p.getName(), "diamond"))
				{
								
					if(QuakeManager.getSelected(p.getName()).equalsIgnoreCase("diamond"))
					{
						
						p.sendMessage(plugin.decline + "Du hast diese Waffe bereits ausgewählt!");
						return;
						
					}
					
					try 
					{
						
						PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE QuakeDatenbank SET equiped = ? WHERE Name = ?");
						ps.setString(1, "diamond");
						ps.setString(2, p.getName());
						ps.executeUpdate();

					} 
					catch (SQLException e1) 
					{
						
						p.sendMessage(plugin.decline);
						e1.printStackTrace();
						
					}
					
					p.closeInventory();
					p.sendMessage(plugin.accept + e.getCurrentItem().getItemMeta().getDisplayName() + " wurde ausgewählt!");
					
				}
				else
				if(!QuakeManager.getHOE(p.getName(), "diamond"))
				{
					
					p.sendMessage(plugin.decline + e.getCurrentItem().getItemMeta().getDisplayName() + " hast du noch nicht gekauft!");
					
				}
			
			}
			
		}
		
	}
	
}
package de.devcubehd.listeners;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.devcubehd.main.McPlanetsQUAKE;

public class PlayerInteractListener implements Listener
{
	
	private McPlanetsQUAKE plugin;

	public PlayerInteractListener(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		this.plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
		
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e)
	{
		
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			
			final Player p = e.getPlayer();
			
			if(!p.getGameMode().equals(GameMode.CREATIVE))
			{
				
				e.setCancelled(true);
				
			}
			
			if(e.getItem() == null)
			{
				
				return;
				
			}
			
			if(e.getItem().getType().equals(Material.INK_SACK))
			{
								
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(b);
				
				try 
				{
					
					out.writeUTF("Connect");
					out.writeUTF("lobby001");
					
				} 
				catch (IOException e2) 
				{
					
					e2.printStackTrace();
					
				}

				p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
								
			}
			else
			if(e.getItem().getType().equals(Material.EMPTY_MAP))
			{	
					
					p.openInventory(plugin.vote);
					
			}
			else
			if(e.getItem().getType().equals(Material.IRON_HOE))
			{
				
				if(plugin.ingame.contains(p))
				{
					
					if(plugin.canshoot.get(p) == true)
					{
						
						p.launchProjectile(Arrow.class);
						plugin.canshoot.put(p, false);
						p.setExp(0);
						p.setLevel(0);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() 
							{
								
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 100);
								p.setExp(1);
								p.setLevel(0);
								plugin.canshoot.put(p, true);
								
							}
							
						} ,40L);
						
					}
					
				}
				else
				{
					
					plugin.shopQCmake(p);
					p.openInventory(plugin.shopQCPlayer.get(p));
					
				}
				
			}
			else
			if(e.getItem().getType().equals(Material.WOOD_HOE))
			{
				
				if(plugin.ingame.contains(p))
				{
					
					if(plugin.canshoot.get(p) == true)
					{
						
						p.launchProjectile(Arrow.class);
						plugin.canshoot.put(p, false);
						p.setExp(0);
						p.setLevel(0);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() 
							{
								
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 100);
								p.setExp(1);
								p.setLevel(0);
								plugin.canshoot.put(p, true);
								
							}
							
						} ,80L);
						
					}
					
				}
				
			}
			else
			if(e.getItem().getType().equals(Material.STONE_HOE))
			{
				
				if(plugin.ingame.contains(p))
				{
					
					if(plugin.canshoot.get(p) == true)
					{
						
						p.launchProjectile(Arrow.class);
						plugin.canshoot.put(p, false);
						p.setExp(0);
						p.setLevel(0);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() 
							{
								
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 100);
								p.setExp(1);
								p.setLevel(0);
								plugin.canshoot.put(p, true);
								
							}
							
						} ,60L);
						
					}
					
				}
				
			}
			else
			if(e.getItem().getType().equals(Material.DIAMOND_HOE))
			{
				
				if(plugin.ingame.contains(p))
				{
					
					if(plugin.canshoot.get(p) == true)
					{
						
						p.launchProjectile(Arrow.class);
						plugin.canshoot.put(p, false);
						p.setExp(0);
						p.setLevel(0);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() 
							{
								
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 100);
								p.setExp(1);
								p.setLevel(0);
								plugin.canshoot.put(p, true);
								
							}
							
						} ,20L);
						
					}
					
				}
				
			}
			else
			{
				
				return;
				
			}
			
		} 
		else
		{
			
			return;
			
		}
		
	}
	
}
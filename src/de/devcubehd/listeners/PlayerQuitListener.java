package de.devcubehd.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.devcubehd.main.McPlanetsQUAKE;

public class PlayerQuitListener implements Listener
{
	
	private McPlanetsQUAKE plugin;

	public PlayerQuitListener(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		this.plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
		
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e)
	{
		
		e.setQuitMessage("");
		plugin.board.resetScores(e.getPlayer().getName());
		
		if(Bukkit.getOnlinePlayers().size() == 1)
		{
			
			Bukkit.reload();
			
		}
		
	}
	
}
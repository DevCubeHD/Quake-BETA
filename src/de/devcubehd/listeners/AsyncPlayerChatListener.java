package de.devcubehd.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.devcubehd.main.McPlanetsQUAKE;

public class AsyncPlayerChatListener implements Listener
{
	
	private McPlanetsQUAKE plugin;

	public AsyncPlayerChatListener(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		this.plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
		
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) 
	{
		
		if (e.getPlayer().isOp()) 
		{
			
			e.setFormat("§4§l" + "%s" + "§8§l" + " > " + "§c" + "%s");
			
		}
		else
		{
			
			e.setFormat("§2§l" + "%s" + "§8§l" + " > " + "§a" + "%s");
			
		}
		
	}

}

package de.devcubehd.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.devcubehd.main.McPlanetsQUAKE;

public class ServerListPingListener implements Listener
{
	
	private McPlanetsQUAKE plugin;

	public ServerListPingListener(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		this.plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
		
	}
	
	 @EventHandler
	 public void motd(ServerListPingEvent e)
	 {
		 
		 e.setMotd(plugin.GameState);
		 
		 
	 }

}

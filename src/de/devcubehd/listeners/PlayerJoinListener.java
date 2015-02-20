package de.devcubehd.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.devcubehd.main.McPlanetsQUAKE;

public class PlayerJoinListener implements Listener
{
	
	private McPlanetsQUAKE plugin;

	public PlayerJoinListener(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		this.plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
		
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e)
	{
		
		Player p = e.getPlayer();
		
		if(plugin.ingamebool == true)
		{
			
			e.setJoinMessage("");
			p.setGameMode(GameMode.SPECTATOR);
			removeTeams(p);
			p.setScoreboard(plugin.board);
			World w = Bukkit.getWorld("world");
			Location loc = new Location(w, -605, 12.5, -391);
			p.teleport(loc);
			p.setGameMode(GameMode.SPECTATOR);
			
		}
		else
		{
			
			e.setJoinMessage(plugin.prefix + "[§8" + Bukkit.getOnlinePlayers().size() + "§7/§8" + Bukkit.getMaxPlayers() + "§7]" +" §6" + p.getName() + " §7ist dem Spiel beigetreten!");
			p.setGameMode(GameMode.ADVENTURE);
			removeTeams(p);
			p.setExp(0);
			p.setLevel(0);
			
			if(p.isOp())
			{
				
				plugin.Owner.addPlayer(p);
				
			}
			else
			{
				
				plugin.Spieler.addPlayer(p);
				
			}
			
			World w = Bukkit.getWorld("world");
			Location loc = new Location(w, -605, 12.5, -391);
			
			p.getInventory().clear();
			p.getInventory().setItem(0, plugin.make(Material.IRON_HOE, "§8§l>> §3Gun§bSelector", 1, 0));
			p.getInventory().setItem(4, plugin.make(Material.EMPTY_MAP, "§8§l>> §3Map§bSelector", 1, 0));
			p.getInventory().setItem(8, plugin.make(Material.INK_SACK, "§8§l>> §3Spiel §bverlassen", 1, 1));
			
			p.teleport(loc);
			plugin.canshoot.put(p, true);
			plugin.spawnPhase.put(p, false);
			p.setScoreboard(plugin.board);
			p.setHealthScale(2);
			p.setExp(1);
			p.setLevel(0);
			
		}
		
	}
	
	public void removeTeams(Player p)
	{
		
		plugin.Owner.removePlayer(p);
		plugin.Admin.removePlayer(p);
		plugin.Premium.removePlayer(p);
		plugin.Spieler.removePlayer(p);
		
	}

}
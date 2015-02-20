package de.devcubehd.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devcubehd.main.McPlanetsQUAKE;

public class AddSpawnCommand implements CommandExecutor
{
	
	private McPlanetsQUAKE plugin;

	public AddSpawnCommand(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) 
	{
		
		if(sender instanceof Player)
		{
			
			Player p = (Player) sender;
			
			if(args.length == 4)
			{
				
				plugin.cfg.set("map00" + args[0] + ".spawns." + args[3] + ".x", p.getLocation().getX());
				plugin.cfg.set("map00" + args[0] + ".spawns." + args[3] + ".y", p.getLocation().getY());
				plugin.cfg.set("map00" + args[0] + ".spawns." + args[3] + ".z", p.getLocation().getZ());
				
				plugin.cfg.set("map00" + args[0] + ".spawns." + args[3] + ".pitch", p.getLocation().getPitch());
				plugin.cfg.set("map00" + args[0] + ".spawns." + args[3] + ".yaw", p.getLocation().getYaw());
				plugin.cfg.set("map00" + args[0] + ".name", args[1]);
				plugin.cfg.set("map00" + args[0] + ".material", args[2]);
				
				//addspawn <MAP-NUM> <DISPLAY> <MATERIAL> <SPAWN>
				p.sendMessage(plugin.accept + "Map00" + args[0] + ", Name: " + args[1] + ", Material: " + args[2] + ", Spawn: " + args[3]);
				
				plugin.saveConfig();
				
			}
			
		}
		
		return true;
		
	}

}
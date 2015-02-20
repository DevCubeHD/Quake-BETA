package de.devcubehd.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devcubehd.main.McPlanetsQUAKE;

public class StartCommand implements CommandExecutor
{
	
	private McPlanetsQUAKE plugin;

	public StartCommand(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) 
	{
		
		if(sender instanceof Player)
		{
			
			plugin.saveConfig();
			
			plugin.getVoteResult();
			
			plugin.GameState = "INGAME";
			plugin.startGame();
			plugin.ingamebool = true;
			
			
		}
		
		return true;
		
	}
	
}
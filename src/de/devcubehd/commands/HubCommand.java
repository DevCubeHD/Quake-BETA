package de.devcubehd.commands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devcubehd.main.McPlanetsQUAKE;

public class HubCommand implements CommandExecutor
{
	
	private McPlanetsQUAKE plugin;

	public HubCommand(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) 
	{
		
		if(sender instanceof Player)
		{
			
			Player p = (Player) sender;
			
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			
			try 
			{
				
				out.writeUTF("Connect");
				out.writeUTF("lobby001");
				
			} 
			catch (IOException e) 
			{
				
				e.printStackTrace();
				
			}
			
			p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
			
		}
		
		return true;
	}

}

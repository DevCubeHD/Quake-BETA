package de.devcubehd.listeners;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Score;

import de.devcubehd.main.McPlanetsQUAKE;
import de.devcubehd.mysql.MySQL;

public class PlayerRulesListener implements Listener
{
	
	private McPlanetsQUAKE plugin;

	public PlayerRulesListener(McPlanetsQUAKE McPlanetsQUAKE) 
	{
		
		this.plugin = McPlanetsQUAKE;
		this.plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
		
	}
	
	@EventHandler
	public void onPlayerRuleONE(PlayerDropItemEvent e)
	{
		
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onPlayerRuleTWO(final PlayerRespawnEvent e) 
	{
		
		final Player p = e.getPlayer();
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() 
			{
				
				if(plugin.ingame.contains(e.getPlayer()))
				{
					
					int spawn = plugin.rdmInt(1, 12);
					double x = (double) plugin.cfg.get("map00" + plugin.mapplaying + ".spawns." + spawn + ".x");
					double y = (double) plugin.cfg.get("map00" + plugin.mapplaying + ".spawns." + spawn + ".y");
					double z = (double) plugin.cfg.get("map00" + plugin.mapplaying + ".spawns." + spawn + ".z");
					
					int pitch = plugin.cfg.getInt("map00" + plugin.mapplaying + ".spawns." + spawn + ".pitch");
					int yaw = plugin.cfg.getInt("map00" + plugin.mapplaying + ".spawns." + spawn + ".yaw");
							
					World world = Bukkit.getWorld("world");
					
					e.setRespawnLocation(new Location(world, x, y, z, yaw, pitch));
					p.teleport(new Location(world, x, y, z, yaw, pitch));
					
					p.getInventory().clear();
					plugin.setHoe(p);
					plugin.spawnPhase.put(p, true);
					p.setHealthScale(2);
					p.setExp(1);
					p.setLevel(0);
					
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

						@Override
						public void run() 
						{
							
							plugin.spawnPhase.put((Player) e.getPlayer(), false);
							
						}
						
					} ,80L);
					return;
					
				}
				else
				{
					
					return;
					
				}
				
			}
			
		}, 5L);
		
	}
	
	@EventHandler
	public void onPlayerRuleTHREE(PlayerMoveEvent e)
	{
		
		Player p = e.getPlayer();
		
		if(p.getLocation().getY() <= 5)
		{
			
			World w = Bukkit.getWorld("world");
			Location loc = new Location(w, -604.5, 12.5, -390.5);
			p.teleport(loc);
			
		}
		
		if(p.getLocation().getBlock().getType().equals(Material.CARPET))
		{
			
			if(!plugin.jnr.contains(p))
			{
				
				FireworkEffect.Builder builder = FireworkEffect.builder();
		        builder.withTrail();
		        builder.withFlicker();
		        builder.withFade(Color.GREEN);
		        builder.with(FireworkEffect.Type.BURST);
		        builder.withColor(Color.GREEN);
		        
				Bukkit.broadcastMessage(plugin.accept + "§8" + p.getName() + " §7hat das Jump 'n' Run geschafft!");
				plugin.jnr.add(p);
				Firework fw1 = (Firework) p.getWorld().spawn(p.getLocation().add(0.00, 0.00, 2.00).subtract(0.00, 12.00, 0.00), Firework.class);
			    FireworkMeta meta1 = fw1.getFireworkMeta();
			    
		        meta1.addEffects(builder.build());
		        meta1.setPower(1);
		        fw1.setFireworkMeta(meta1);
			    
			    Firework fw2 = (Firework) p.getWorld().spawn(p.getLocation().add(2.00, 0.00, 0.00).subtract(0.00, 12.00, 0.00), Firework.class);
			    
		        fw2.setFireworkMeta(meta1);
			    
			    Firework fw3 = (Firework) p.getWorld().spawn(p.getLocation().subtract(0.00, 12.00, 2.00), Firework.class);
			    
		        fw3.setFireworkMeta(meta1);
			    
			    Firework fw4 = (Firework) p.getWorld().spawn(p.getLocation().subtract(2.00, 12.00, 0.00), Firework.class);
			    
		        fw4.setFireworkMeta(meta1);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerRuleFOUR(PlayerPickupItemEvent e)
	{
		
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onPlayerRuleFIVE(final EntityDamageEvent e)
	{
		
		if(e.getEntity() instanceof Player)
		{
			
			if(!e.getCause().equals(DamageCause.PROJECTILE))
			{
				
				e.setCancelled(true);
				
			}
			else
			{
				
				e.setCancelled(false);
				
			}
		
		}
	
	}
	
	@EventHandler
	public void onShootBow(final EntityShootBowEvent e) 
	{
		
		e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(20));
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() 
			{
				
				e.getProjectile().remove();
				
			}
			
		} ,20L);
			
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) 
	{
				
	    if(e.getDamager() instanceof Arrow) 
	    {
	    	
	        Arrow arrow = (Arrow) e.getDamager();
	        Player killer = null;
	        Score gscore = null;

	        if(arrow.getShooter() instanceof Player) 
	        {
	        	
	        	if(e.getEntity() instanceof Player)
	        	{
	        		
	        		if(plugin.spawnPhase.get(e.getEntity()) == false)
	        		{
	        			
	        			e.setDamage(40);
	        			e.setCancelled(false);
	        			killer = (Player) arrow.getShooter();
	        			Bukkit.broadcastMessage(plugin.accept + killer.getName() + " §a+1");
	        	        Score score = plugin.objective.getScore(killer.getName());
	        			score.setScore(score.getScore()+1);
	        			gscore = score;
	        			int punkte = 0;
	        			try 
	        			{
	        				
	        				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Punkte FROM PunkteDatenbank WHERE Name = ?");
	        				ps.setString(1, killer.getName());
	        				ResultSet rs = ps.executeQuery();
	        				
	        				while(rs.next())
	        				{
	        					
	        					punkte = rs.getInt("Punkte");
	        					
	        				}
	        				
	        				PreparedStatement ps2 = MySQL.getConnection().prepareStatement("UPDATE PunkteDatenbank SET Punkte = ? WHERE Name = ?");
	        				ps2.setInt(1, punkte+1);
	        				ps2.setString(2, killer.getName());
	        				ps2.executeUpdate();
	        				
	        			} 
	        			catch (SQLException e1) 
	        			{
	        				
	        				e1.printStackTrace();
	        				
	        			}	 
	        			
	        		}
	        		else
	        		{
	        			
	        			e.setDamage(0);
	        			e.setCancelled(true);
	        			killer = (Player) arrow.getShooter();
	        			killer.sendMessage(plugin.decline + "Dieser Spieler hat ein Respawn Schild!");
	        			return;
	        			
	        		}
	        		
	        	}
	            
	        }
	        else
	        {
	        	
	        	e.setCancelled(true);
	        	return;
	        	
	        }
			
			if(gscore.getScore() == 20)
			{
				
				for(Player all : Bukkit.getOnlinePlayers())
				{
					
					plugin.ingame.remove(all);
					all.getInventory().clear();
					World w = Bukkit.getWorld("world");
					Location loc = new Location(w, -605, 12.5, -391);
					all.teleport(loc);
					plugin.canshoot.put(all, false);
					all.getInventory().clear();
					
				}
				
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(plugin.prefix + "§8" + killer.getName() + " §7hat gewonnen!");
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(" ");

				int punkte = 0;
    			try 
    			{
    				
    				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Punkte FROM PunkteDatenbank WHERE Name = ?");
    				ps.setString(1, killer.getName());
    				ResultSet rs = ps.executeQuery();
    				
    				while(rs.next())
    				{
    					
    					punkte = rs.getInt("Punkte");
    					
    				}
    				
    				PreparedStatement ps2 = MySQL.getConnection().prepareStatement("UPDATE PunkteDatenbank SET Punkte = ? WHERE Name = ?");
    				ps2.setInt(1, punkte+30);
    				ps2.setString(2, killer.getName());
    				ps2.executeUpdate();
    				
    			} 
    			catch (SQLException e1) 
    			{
    				
    				e1.printStackTrace();
    				
    			}	 
				
				plugin.endGame();
				
			}
			
			arrow.remove();
	        
	    }
	    
	}
	
	@EventHandler
	public void onDeath(final PlayerDeathEvent e) 
	{
		
		e.setDroppedExp(0);
		e.getDrops().clear();
		
		new BukkitRunnable() 
		{

			@Override
			public void run() 
			{
				
				PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
				((CraftPlayer)e.getEntity()).getHandle().playerConnection.a(packet);
				
			}
			
		}.runTaskLater(plugin, 1L);
		
	}
	
}
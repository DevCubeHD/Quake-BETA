package de.devcubehd.main;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import de.devcubehd.commands.AddSpawnCommand;
import de.devcubehd.commands.HubCommand;
import de.devcubehd.commands.StartCommand;
import de.devcubehd.listeners.AsyncPlayerChatListener;
import de.devcubehd.listeners.InventoryClickListener;
import de.devcubehd.listeners.PlayerInteractListener;
import de.devcubehd.listeners.PlayerJoinListener;
import de.devcubehd.listeners.PlayerQuitListener;
import de.devcubehd.listeners.PlayerRulesListener;
import de.devcubehd.listeners.ServerListPingListener;
import de.devcubehd.mysql.MySQL;
import de.devcubehd.mysql.QuakeManager;

public class McPlanetsQUAKE extends JavaPlugin implements Listener 
{
	
	//TODO: Add Sounds, presize Listeners, AfterTP 3 Seconds countdown, MOTD to Hub, player slowness
	public String prefix = "§3§lQuake§b§lCraft §8§l>> §7";
	public String accept = "§a§l✔ §8§l>> §7";
	public String decline = "§c§l✘ §8§l>> §7";
	public String GameState = null;
	
	public int countdown = 120;
	
	public File file = new File("plugins/McPlanetsQUAKE", "config.yml");
	public FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	public Scoreboard board = manager.getNewScoreboard();
	public Objective objective = board.registerNewObjective("Kills", "dummy");
	
	public static McPlanetsQUAKE plugin;
	
	public int mapplaying;
		
	public World world = Bukkit.getWorld("world");
	
	public Team Owner;
	public Team Admin;
	public Team Premium;
	public Team Spieler;
	
	public ArrayList<Player> ingame = new ArrayList<>();
	public ArrayList<Player> voted = new ArrayList<>();
	public ArrayList<Player> jnr = new ArrayList<>();
	public ArrayList<Player> hax = new ArrayList<>();
	
	public HashMap<Player, Boolean> canshoot = new HashMap<>();
	public HashMap<Player, Inventory> shopQCPlayer = new HashMap<>();
	public HashMap<Player, Boolean> spawnPhase = new HashMap<>();
	
	public Inventory vote;
	public Inventory shopQC;
	public Inventory hoes;
	
	public boolean cancel;
	public boolean ingamebool;
		
	public void onEnable()
	{
		
		System.out.println("-----------------------------------------------------------");
		System.out.println(" .d88888b.  888     888       d8888 888    d8P  8888888888 ");
		System.out.println("d88P' 'Y88b 888     888      d88888 888   d8P   888        ");
		System.out.println("888     888 888     888     d88P888 888  d8P    888        ");
		System.out.println("888     888 888     888    d88P 888 888d88K     8888888    ");
		System.out.println("888     888 888     888   d88P  888 8888888b    888        ");
		System.out.println("888 Y8b 888 888     888  d88P   888 888  Y88b   888        ");
		System.out.println("Y88b.Y8b88P Y88b. .d88P d8888888888 888   Y88b  888        ");
		System.out.println(" 'Y888888'   'Y88888P' d88P     888 888    Y88b 8888888888 ");
		System.out.println("       Y8b                           ");
		System.out.println("                     -  - ENABLED -  -             ");
		System.out.println("-----------------------------------------------------------");
	
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		plugin = this;
		
		registerCommands();
		registerListener();
		startCountdown();
		registerVoteInv();
		playParticles();
		ArrowRemove();
		registerScoreboard();
		
		shopQC = Bukkit.createInventory(null, 9 * 3, "Gun Selector");
		
		shopQC.setItem(0, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(1, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(2, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(3, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(4, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(5, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(6, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(7, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(8, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		
		shopQC.setItem(10, this.make(Material.WOOD_HOE, "§7Luftgewehr", 1, 0));
		shopQC.setItem(12, this.make(Material.STONE_HOE, "§7Auto Sniper", 1, 0));
		shopQC.setItem(14, this.make(Material.IRON_HOE, "§7Pulse Rifle", 1, 0));
		shopQC.setItem(16, this.make(Material.DIAMOND_HOE, "§7Fusion Blaster", 1, 0));
		
		shopQC.setItem(18, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(19, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(20, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(21, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(22, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(23, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(24, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(25, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		shopQC.setItem(26, this.make(Material.STAINED_GLASS_PANE, " ", 1, 8));
		
		for(Player all : Bukkit.getOnlinePlayers())
		{
			
			board.resetScores(all.getName());
			all.setScoreboard(manager.getNewScoreboard());
			
		}
		
		objective.setDisplayName("§8§l>> §3Kills §8§l<<");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		GameState = "LOBBY";
		cancel = false;
		ingamebool = false;
		plugin.canshoot.clear();
		plugin.spawnPhase.clear();
		
		MySQL.connect();
		
		//KILL SCOREBOARD
		/*
		 * 
		 * Score score = objective.getScore(p);
		 * score.setScore(score + 1);
		 * 
		 * if(score == 20)
		 * {
		 * 
		 * 		winPlayer(p);
		 * 
		 * }
		 * 
		 */
				
	}

	public void onDisable()
	{
		
		System.out.println("-----------------------------------------------------------");
		System.out.println(" .d88888b.  888     888       d8888 888    d8P  8888888888 ");
		System.out.println("d88P' 'Y88b 888     888      d88888 888   d8P   888        ");
		System.out.println("888     888 888     888     d88P888 888  d8P    888        ");
		System.out.println("888     888 888     888    d88P 888 888d88K     8888888    ");
		System.out.println("888     888 888     888   d88P  888 8888888b    888        ");
		System.out.println("888 Y8b 888 888     888  d88P   888 888  Y88b   888        ");
		System.out.println("Y88b.Y8b88P Y88b. .d88P d8888888888 888   Y88b  888        ");
		System.out.println(" 'Y888888'   'Y88888P' d88P     888 888    Y88b 8888888888 ");
		System.out.println("       Y8b                           ");
		System.out.println("                     -  - DISABLED -  -             ");
		System.out.println("-----------------------------------------------------------");
		
		saveConfig();
		MySQL.disconnect();
		
	}
	
	public void registerScoreboard() 
	{
		
		Owner = board.registerNewTeam("Owner");
		Admin = board.registerNewTeam("Admin");
		Premium = board.registerNewTeam("Premium");
		Spieler = board.registerNewTeam("Spieler");
		
		Owner.setPrefix("§4");
		Admin.setPrefix("§c");
		Premium.setPrefix("§6");
		Spieler.setPrefix("§a");
		
	}
	
	public void shopQCmake(Player p)
	{

		this.shopQCPlayer.put(p, shopQC);
		
		ItemStack item1 = this.make(Material.WOOD_HOE, "§7Luftgewehr", 1, 0);
		ItemMeta meta1 = item1.getItemMeta();
		if(QuakeManager.getHOE(p.getName(), "wood"))
		{
			
			meta1.setLore(Arrays.asList(this.accept, "§7Klicken zum Ausrüsten!"));
			
		}
		else
		{
			
			meta1.setLore(Arrays.asList(this.decline + ""));
			
		}
		item1.setItemMeta(meta1);
		shopQCPlayer.get(p).setItem(10, item1);
		
		ItemStack item2 = this.make(Material.STONE_HOE, "§7Auto Sniper", 1, 0);
		ItemMeta meta2 = item2.getItemMeta();
		if(QuakeManager.getHOE(p.getName(), "stone"))
		{
			
			meta2.setLore(Arrays.asList(this.accept, "§7Klicken zum Ausrüsten!"));
			
		}
		else
		{
			
			meta2.setLore(Arrays.asList(this.decline + ""));
			
		}
		item2.setItemMeta(meta2);
		shopQCPlayer.get(p).setItem(12, item2);
		
		ItemStack item3 = this.make(Material.IRON_HOE, "§7Pulse Rifle", 1, 0);
		ItemMeta meta3 = item3.getItemMeta();
		if(QuakeManager.getHOE(p.getName(), "iron"))
		{
			
			meta3.setLore(Arrays.asList(this.accept, "§7Klicken zum Ausrüsten!"));
			
		}
		else
		{
			
			meta3.setLore(Arrays.asList(this.decline + ""));
			
		}
		item3.setItemMeta(meta3);
		shopQCPlayer.get(p).setItem(14, item3);
		
		ItemStack item4 = this.make(Material.DIAMOND_HOE, "§7Fusion Blaster", 1, 0);
		ItemMeta meta4 = item4.getItemMeta();
		if(QuakeManager.getHOE(p.getName(), "diamond"))
		{
			
			meta4.setLore(Arrays.asList(this.accept, "§7Klicken zum Ausrüsten!"));
			
		}
		else
		{
			
			meta4.setLore(Arrays.asList(this.decline + ""));
			
		}
		item4.setItemMeta(meta4);
		shopQCPlayer.get(p).setItem(16, item4);
				
		p.updateInventory();
		
	}
	
	public void setHoe(Player p)
	{
		
		String hoe = QuakeManager.getSelected(p.getName());
		
		if(hoe.equalsIgnoreCase("wood"))
		{
			
			p.getInventory().setItem(0, this.make(Material.WOOD_HOE, "§8§l>> §2Luftgewehr", 1, 0));
			
		}
		else
		if(hoe.equalsIgnoreCase("stone"))
		{
			
			p.getInventory().setItem(0, this.make(Material.STONE_HOE, "§8§l>> §eAuto Sniper", 1, 0));
			
		}
		else
		if(hoe.equalsIgnoreCase("iron"))
		{
			
			p.getInventory().setItem(0, this.make(Material.IRON_HOE, "§8§l>> §6Pulse Rifle", 1, 0));
			
		}
		else
		if(hoe.equalsIgnoreCase("diamond"))
		{
			
			p.getInventory().setItem(0, this.make(Material.DIAMOND_HOE, "§8§l>> §cFusion Blaster", 1, 0));
			
		}
		
	}
	
	public void ArrowRemove()
	{
		
		new BukkitRunnable() 
		{

			@Override
			public void run() 
			{
				
				for (final Arrow arrow : Bukkit.getWorld("world").getEntitiesByClass(Arrow.class)) 
				{
					
					arrow.setVelocity(arrow.getVelocity().multiply(20));
					
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

						@Override
						public void run() 
						{
							
							arrow.remove();
							
						}
						
					} ,20L);
					
				}
				
			}
		
		}.runTaskTimer(this, 20L, 1L);
		
	}
	
	public void registerCommands() 
	{
		
		HubCommand cHubCommand = new HubCommand(this);
		getCommand("hub").setExecutor(cHubCommand);
		
		AddSpawnCommand cAddSpawnCommand = new AddSpawnCommand(this);
		getCommand("addspawn").setExecutor(cAddSpawnCommand);
		
		StartCommand cStartCommand = new StartCommand(this);
		getCommand("start").setExecutor(cStartCommand);
		
	}
	
	public void registerListener() 
	{
		
		getServer().getPluginManager().registerEvents(this, this);
		new PlayerJoinListener(this);
		new PlayerInteractListener(this);
		new InventoryClickListener(this);
		new PlayerRulesListener(this);
		new ServerListPingListener(this);
		new AsyncPlayerChatListener(this);
		new PlayerQuitListener(this);
		
	}
	
	public void registerVoteInv()
	{
		
		vote = Bukkit.createInventory(null, 9, "Wähle eine Karte:");
		
		ArrayList<Integer> nums = new ArrayList<>();
		
		nums.add(1);
		nums.add(2);
		nums.add(3);
		nums.add(4);
		nums.add(5);
		
		Random r = new Random();
		int sel;
		sel = r.nextInt(nums.size());

		this.vote.setItem(1, make(Material.getMaterial(cfg.getString("map00" + nums.get(sel) + ".material")), "§7" + cfg.getString("map00" + nums.get(sel) + ".name"), 0, 0));
		
		nums.remove(sel);
		sel = r.nextInt(nums.size());

		this.vote.setItem(4, make(Material.getMaterial(cfg.getString("map00" + nums.get(sel) + ".material")), "§7" + cfg.getString("map00" + nums.get(sel) + ".name"), 0, 0));
		
		nums.remove(sel);
		sel = r.nextInt(nums.size());

		this.vote.setItem(7, make(Material.getMaterial(cfg.getString("map00" + nums.get(sel) + ".material")), "§7" + cfg.getString("map00" + nums.get(sel) + ".name"), 0, 0));
		
		nums.remove(sel);
		
	}
	
	public void endGame()
	{
		
		Bukkit.broadcastMessage(plugin.decline + "§cDer Server startet in 10 Sekunden neu!");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() 
			{
				
				for(Player all : Bukkit.getOnlinePlayers())
				{
					
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
					
					all.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
					board.resetScores(all.getName());
					
				}
				
				GameState = "RESTARTING";
				
				Bukkit.reload();
				
			}
			
		} ,200L);
		
	}
	
	public void startCountdown() 
	{
		
		new BukkitRunnable() 
		{

			@Override
			public void run() 
			{
				
				countdown--;
				
				if(cancel == true)
				{
					
					cancel();
					
				}
				
				if(Bukkit.getOnlinePlayers().size() == 0)
				{
					
					countdown = 121;
					registerVoteInv();
					
				}
				
				for(Player all : Bukkit.getOnlinePlayers())
				{
					
					all.setLevel(countdown);
					
				}
			
				if(countdown == 120 || countdown == 60 || countdown == 30 || countdown == 15 || countdown == 10 || countdown == 5 || countdown == 4 || countdown == 3 || countdown == 2)
				{
					
					Bukkit.broadcastMessage(prefix + "Das Spiel beginnt in §8" + countdown + "§7 Sekunden!");
					
				}
				
				if(countdown == 1)
				{
					
					Bukkit.broadcastMessage(prefix + "Das Spiel beginnt in §8" + "einer" + "§7 Sekunde!");
					
				}
				
				if(countdown == 15 && Bukkit.getOnlinePlayers().size() <= 4)
				{
					
					if(!(Bukkit.getOnlinePlayers().size() == 4))
					{
						
						countdown = 120;
						Bukkit.broadcastMessage(decline + "Der Countdown wurde auf 120 zurückgesetzt!");
						if(Bukkit.getOnlinePlayers().size() == 3)
						{
							
							Bukkit.broadcastMessage(decline + "Es fehlt §8" + (4 - Bukkit.getOnlinePlayers().size()) + " §7Spieler!");
							
						} 
						else
						if(Bukkit.getOnlinePlayers().size() <= 3)
						{
							
							Bukkit.broadcastMessage(decline + "Es fehlen §8" + (4 - Bukkit.getOnlinePlayers().size()) + " §7Spieler!");
							
						}
						
					}
					
				}
				
				if(countdown == 10)
				{
					
					getVoteResult();
					
				}
				
				if(countdown == 0)
				{
					
					startGame();
					ingamebool = true;
					GameState = "INGAME";
					
				}
				
			}
			
		}.runTaskTimer(plugin, 20L, 20L);
		
	}
	
	public void getVoteResult()
	{
		
		int i1 = vote.getItem(1).getAmount();
		int i2 = vote.getItem(4).getAmount();
		int i3 = vote.getItem(7).getAmount();
		String mapm = "";
		
		HashMap<String, Integer> v = new HashMap<>();
		
		v.put("ONE", i1);
		v.put("TWO", i2);
		v.put("THREE", i3);
		
		int max = 0;
		for(int i : v.values())
		{
			
			if(i > max)
			{
				
				max = i;
				
			}
			
		}
		
		String map = null;
		
		for(String all : v.keySet())
		{
			
			if(v.get(all) == max)
			{
				
				map = (String) plugin.getKey(v, max);
			
			}
				
		}
							
		if(map.equalsIgnoreCase("ONE"))
		{
			
			mapm = vote.getItem(1).getType().toString();
			
		}
		else
		if(map.equalsIgnoreCase("TWO"))
		{
			
			mapm = vote.getItem(4).getType().toString();
			
		}
		else
		if(map.equalsIgnoreCase("THREE"))
		{
			
			mapm = vote.getItem(7).getType().toString();
			
		}
		
		if(cfg.getString("map001.material").equalsIgnoreCase(mapm))
		{
			
			mapplaying = 1;
			
		}
		
		if(cfg.getString("map002.material").equalsIgnoreCase(mapm))
		{
			
			mapplaying = 2;
			
		}
		
		if(cfg.getString("map003.material").equalsIgnoreCase(mapm))
		{
			
			mapplaying = 3;
			
		}
		
		if(cfg.getString("map004.material").equalsIgnoreCase(mapm))
		{
			
			mapplaying = 4;
			
		}
		
		if(cfg.getString("map005.material").equalsIgnoreCase(mapm))
		{
			
			mapplaying = 5;
			
		}
		
	}
	
	public void preGameWait()
	{
				
		new BukkitRunnable() 
		{

			int i = 4;
						
			@Override
			public void run() 
			{
				
				i--;
				
				if(i != 0)
				{

					Bukkit.broadcastMessage(plugin.prefix + "Das Spiel beginnt in: §8" + i);
					
				}
				
				if(i == 3)
				{
					
					for(Player all : Bukkit.getOnlinePlayers())
					{
						
						all.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 100));
						
					}
					
				}
					
				if(i == 0)
				{
					
					for(Player all : Bukkit.getOnlinePlayers())
					{
						
						ingame.add(all);
						
					}
					Bukkit.broadcastMessage(plugin.prefix + "Los gehts!");
					cancel();
					
				}
								
			}
			
		}.runTaskTimer(plugin, 20L, 20L);
		
	}
	
	public void playParticles()
	{
				
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() 
			{
				
				for(Entity entity : world.getEntities())
				{
					
					if(entity.getType().equals(EntityType.ARROW))
					{
						
						Arrow a = (Arrow) entity;
						a.getVelocity().multiply(20D);
						
					}
					
				}
				
			}
			
		} ,1L, 5L);
		
	}
	
	public Object getKey(HashMap<?, ?> hashmap, Object value) {
		for(Object o : hashmap.keySet()) {
			if(hashmap.get(o).equals(value)) return o;
		}
		return null;
	}
	
	public String getHighest(HashMap<String, ? extends Number> hashmap) {
		if(hashmap == null || hashmap.isEmpty()) return "-";
		Number t = null;
		String s = "-";
		for(Number l : hashmap.values()) {
			if(t == null) {
				t = l;
				s = (String) getKey(hashmap, l);
			}
			if(l instanceof Integer) {
				if(l.intValue() > t.intValue()) {
					t = l;
					s = (String) getKey(hashmap, l);
				}
			}else if(l instanceof Long) {
				if(l.longValue() > t.longValue()) {
					t = l;
					s = (String) getKey(hashmap, l);
				}
			}else if(l instanceof Float) {
				if(l.floatValue() > t.floatValue()) {
					t = l;
					s = (String) getKey(hashmap, l);
				}
			}else if(l instanceof Byte) {
				if(l.byteValue() > t.byteValue()) {
					t = l;
					s = (String) getKey(hashmap, l);
				}
			}else if(l instanceof Double) {
				if(l.doubleValue() > t.doubleValue()) {
					t = l;
					s = (String) getKey(hashmap, l);
				}
			}else if(l instanceof Short) {
				if(l.shortValue() > t.shortValue()) {
					t = l;
					s = (String) getKey(hashmap, l);
				}
			}
			
		}
		return s;
	}

	
	public void saveConfig()
	{
		
		try 
		{
			
			cfg.save(file);
			
		} 
		catch (IOException e)
		{
			
			e.printStackTrace();
			
		}
		
	}
	
	public ItemStack make(Material item, String name, int amount, int dmg)
	{
		
		ItemStack items = new ItemStack(item, amount, (short) dmg);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(name);
		items.setItemMeta(meta);
		
		return items;
	}
	
	public void startGame()
	{
		
		preGameWait();
		int spawn = 12;
		for(Player all : Bukkit.getOnlinePlayers())
		{
			
			all.getInventory().clear();
			cancel = true;
			canshoot.put(all, true);
			setHoe(all);
			all.setScoreboard(board);
			TPSpawn(all, spawn);	
			all.setLevel(0);
			all.setExp(0);
			spawn--;
			
		}
		
	}
	
	public int rdmInt(int min, int max) 
	{

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public void TPSpawn(Player p, int spawn)
	{
		
		double x = (double) cfg.get("map00" + this.mapplaying + ".spawns." + spawn + ".x");
		double y = (double) cfg.get("map00" + this.mapplaying + ".spawns." + spawn + ".y");
		double z = (double) cfg.get("map00" + this.mapplaying + ".spawns." + spawn + ".z");
		
		int pitch = cfg.getInt("map00" + this.mapplaying + ".spawns." + spawn + ".pitch");
		int yaw = cfg.getInt("map00" + this.mapplaying + ".spawns." + spawn + ".yaw");
				
		World world = Bukkit.getWorld("world");
		p.teleport(new Location(world, x, y, z, yaw, pitch));
		
	}
	
}
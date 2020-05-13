package oneric.bukkit.walls.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import oneric.bukkit.walls.commands.ComArmor;
import oneric.bukkit.walls.commands.ComBuildArena;
import oneric.bukkit.walls.commands.ComCreateArena;
import oneric.bukkit.walls.commands.ComCreateSign;
import oneric.bukkit.walls.commands.ComDeleteArena;
import oneric.bukkit.walls.commands.ComDeleteSpawn;
import oneric.bukkit.walls.commands.ComJoin;
import oneric.bukkit.walls.commands.ComLeave;
import oneric.bukkit.walls.commands.ComPlayerList;
import oneric.bukkit.walls.commands.ComSetSpawn;
import oneric.bukkit.walls.commands.ComWallFall;
import oneric.bukkit.walls.commands.CommandHandler;
import oneric.bukkit.walls.commands.ComEditArena;
import oneric.bukkit.walls.events.BlockEvents;
import oneric.bukkit.walls.events.OtherEvents;
import oneric.bukkit.walls.events.PlayerEvents;
import oneric.bukkit.walls.generators.PlainChunkGenerator;
import oneric.bukkit.walls.utils.ArenaLoader;



public class WallsPlugin extends JavaPlugin{
	
	
	public Updater updater;
	public ConfigManager configManager;
	private CommandHandler commandHandler;
	public static WallsPlugin me;
	
	
	public List<WallsArena> arenen;
	public HashMap<String, Integer> arenaMap;
	public List<WallsRound> wallsRounds;
	
	/** The Player creating the sign is the key. The associated Arena the Value*/
	public HashMap<String, String> signCreator;
	
	public static final String PERMISSION_PLAY = "walls.play";
	public static final String PERMISSION_MANIPULATE = "walls.manipulate";
	
	@Override
	public void onDisable()
	{
		//Save Config
		this.saveConfig();
		
		//Cancel all WallsGames and recreate the Arena
		/*for(WallsRound round : this.wallsRounds)
		{
			for(ArrayList<Player> group : round.Groups)
			{
				for(Player p : group)
				{
					
					p.teleport(new Location(this.getServer().getWorld(round.getArena().getWorldName()), 996, 64, 996));
				}
			}
		}
		
		for(WallsRound round : this.wallsRounds)
		{
			round.getArenaManager().recreate();
		}*/
		
		for(int roundIndex = 0; roundIndex < this.wallsRounds.size(); roundIndex++)
		{
			for(int groupIndex = 0; groupIndex < this.wallsRounds.get(roundIndex).Groups.size(); groupIndex++)
			{
				for(int playerIndex = 0; playerIndex < this.wallsRounds.get(roundIndex).Groups.get(groupIndex).size(); playerIndex++)
				{
						this.wallsRounds.get(roundIndex).doLeavingStuff(this.wallsRounds.get(roundIndex).Groups.get(groupIndex).get(playerIndex), this, false, true);
						this.wallsRounds.get(roundIndex).Groups.get(groupIndex).remove(playerIndex);
				}
			}
		}
		
		
		
		this.getLogger().info("[Walls Plugin] by Oneric aka cmdmole disabled :( !");
		
		
	}
	
	
	@Override
	public void onEnable()
	{
		
		me = this;
		
		if(!(new File(this.getDataFolder() + "/" + "arenas").exists()))
		{
			new File(this.getDataFolder() + "/" + "arenas").mkdirs();
			this.getLogger().info("Create Arena Directory succesfully !");
		}
		
		if(!(new File(this.getDataFolder() + "/" + "arenas" + "/" + "index.index").exists()))
		{
			try {
				new File(this.getDataFolder() + "/" + "arenas" + "/" + "index.index").createNewFile();
				this.getLogger().info("Created the index File succesfully !");
			} catch (IOException e) {
				this.getLogger().warning("Failed to Create the index File !");
			}
		}
		
		
		
		
		this.loadArenas();
		this.wallsRounds = new ArrayList<WallsRound>();
		
		this.signCreator = new HashMap<String, String>();
		
		
		//Config
		this.configManager = new ConfigManager(this);
		this.saveDefaultConfig();
		
		//Command
		this.commandHandler = new CommandHandler(this);
		
		//Variabeln
		PluginDescriptionFile descr = this.getDescription();
		//PluginManager manager = this.getServer().getPluginManager();
		
		//register the Events
		this.registerEvents();
		
		//activate Command Executors
		this.setUpCommands();
		
		//Logger
		this.getLogger().info("by Oneric aka cmdmole enabled :D !");
		this.getLogger().info("version: " + descr.getVersion());
		
		me = this;
		
		
		
		updater = new Updater(this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, updater, 0, ConfigManager.updateIntervall);
		
		
		
		
		
		
	}
	
	
	/**
	 * 
	 * COMMANDS
	 * 
	 * **/
	@Override
	public boolean onCommand(CommandSender sender, Command com, String comLabel, String[] arg)
	{
		
		return this.commandHandler.onCommand(sender, com, comLabel, arg);
		
	}
	
	
	/**
	 * 
	 * EVENTS
	 * 
	 * **/
	private void registerEvents()
	{
		this.getServer().getPluginManager().registerEvents(new OtherEvents(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockEvents(this), this);
	}
	
	/** 
	 * COMMANDS
	 * */
	private void setUpCommands()
	{
		this.getCommand("wallsArmor").setExecutor(new ComArmor(this));
		
		this.getCommand("wallsEditArena").setExecutor(new ComEditArena(this));
		this.getCommand("wallsDelArena").setExecutor(new ComDeleteArena(this));
		this.getCommand("wallsCreateArena").setExecutor(new ComCreateArena(this));
		
		this.getCommand("wallsBuildArena").setExecutor(new ComBuildArena(this));
		this.getCommand("wallFall").setExecutor(new ComWallFall(this));
		
		this.getCommand("wallsDelSpawn").setExecutor(new ComDeleteSpawn(this));
		this.getCommand("wallsSetSpawn").setExecutor(new ComSetSpawn(this));
		
		this.getCommand("wallsCreateSign").setExecutor(new ComCreateSign(this));
		
		
		this.getCommand("wallsPlayerList").setExecutor(new ComPlayerList(this));
		
		this.getCommand("wallsJoin").setExecutor(new ComJoin(this));
		this.getCommand("wallsLeave").setExecutor(new ComLeave(this));
		
	}
	
	
	public void loadArenas()
	{
		this.arenaMap = new HashMap<String, Integer>();
		this.arenen = new ArrayList<WallsArena>();
		
		
		
		try {
			
			FileReader r = new FileReader((this.getDataFolder() + "/" + "arenas" + "/" + "index.index"));
			BufferedReader reader = new BufferedReader(r);
			
			String line;
			int counter = 0;
			while((line = reader.readLine()) != null)
			{
				this.arenaMap.put(line, counter);
				this.arenen.add(counter, ArenaLoader.loadArena(line));
				counter++;
			}
			
			reader.close();
			
			
			
			
		} catch (FileNotFoundException e) {
			
			this.getLogger().warning("Failed to load index or an Arena !");
		} catch (IOException e) {
			this.getLogger().warning("Failed to load index or an Arena !");
		}
		
		
	}
	
	public boolean putInMapAndList(String key, WallsArena arena) throws IOException
	{
		int pos = this.arenen.size();
		
		if(!this.arenaMap.containsKey(key))
		{
			this.arenaMap.put(key, pos);
			this.arenen.add(pos, arena);
			
			FileWriter w = new FileWriter((this.getDataFolder() + "/" + "arenas" + "/" + "index.index"), true);
			BufferedWriter writer = new BufferedWriter(w);
			
			writer.write(key);
			writer.newLine();
			
			writer.flush();
			writer.close();
			
			return true;
		}
		else{
			return false;
		}

	}
	
	
	public WallsPlugin instance()
	{
		return this;
	}
	
	public ItemStack getMyHead()
	{
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta meta = (SkullMeta)item.getItemMeta();
		meta.setOwner("nkioe");
		meta.setDisplayName("Oneric's / cmdmole's Head");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Developer of the :");
		lore.add("     - Ultimate Nether Mod");
		lore.add("     - Walls Plugin");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public boolean isArenaWorld(String world)
	{
		for(WallsArena arena : this.arenen)
		{
			if(arena.getWorldName().equals(world))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isArenaCenterBlock(Block block)
	{
		WallsArena arena = this.getArenaForWorld( block.getWorld().getName() );
		if(arena != null)
		{
			int x1,x2, z1,z2;
			
			x1 = 0;
			x2 = arena.getXWide();
			z1 = arena.getZWide();
			z2 = arena.getZWide() * 2;
			
			if(block.getX() < x2 && block.getX() >= x1)
			{
				if(block.getZ() < z2 && block.getZ() >= z1)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public WallsArena getArenaForWorld(String world)
	{
		if(isArenaWorld(world))
		{
			for(WallsArena arena : this.arenen)
			{
				if(arena.getWorldName().equals(world))
				{
					return arena;
				}
			}
		}
		
		return null;
	}
	
	
	/** 
	 * Returns the name of the Arena the Sign is associated with or NULL if it's not associated with anything
	 * */
	public String isWallsSign(Location loc)
	{
		 for(WallsArena arena: this.arenen)
		 {
			  for(Location signLoc : arena.getSigns())
			  {
				  if(signLoc.getWorld().getName().equals(loc.getWorld().getName()) && 
						  signLoc.getBlockX() == loc.getBlockX() &&
						  signLoc.getBlockY() == loc.getBlockY() &&
						  signLoc.getBlockZ() == loc.getBlockZ() )
				  {
					  return arena.getName();
				  }
			  }
		 }
		 return null;
	}
	
	/**
	 * Checks if the given Player is playing "The Walls"
	 * */
	public boolean isWallsPlayer(String player)
	{
		for(WallsRound round : this.wallsRounds)
		{
			for(ArrayList<String> group : round.Groups)
			{
				for(String p : group)
				{
					if(p.equals(player))
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	/**
	 * Gets an Walls-Player and return all his indexes.
	 * [0] = roundIndex
	 * [1] = groupIndex
	 * [2] = playerIndex
	 * 
	 * If the Player is not playing walls it return null
	 * 
	 * */
	public int[] getWallsPlayer(String player)
	{
		for(int roundIndex = 0; roundIndex < this.wallsRounds.size(); roundIndex++)
		{
			for(int groupIndex = 0; groupIndex < this.wallsRounds.get(roundIndex).Groups.size(); groupIndex++)
			{
				for(int playerIndex = 0; playerIndex < this.wallsRounds.get(roundIndex).Groups.get(groupIndex).size(); playerIndex++)
				{
					if(this.wallsRounds.get(roundIndex).Groups.get(groupIndex).get(playerIndex).equals(player))
					{
						return new int[]{roundIndex, groupIndex, playerIndex};
					}
				}
			}
		}
		
		return null;
	}


	public void killWallsRoundByArena(String arenaName) {
		
		this.updater.killInNextUpdate(arenaName);
		
	}
	
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		return new PlainChunkGenerator();
	}
	
	
	
	
		
		

}

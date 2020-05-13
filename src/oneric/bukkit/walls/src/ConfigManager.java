package oneric.bukkit.walls.src;

import oneric.bukkit.walls.enums.EnumSaveSpawns;

import org.bukkit.Location;

public class ConfigManager {
	
	private WallsPlugin plugin;
	
	public static boolean WelcomeEnable;
	public static boolean betterExplosionProtection;
	
	public static int dungeon1Rarity;
	public static int dungeon2Rarity;
	
	public static int oreLapisRarity;
	public static int oreIronRarity;
	public static int oreDiamondRarity;
	public static int oreRedstoneRarity;
	public static int oreGoldRarity;
	public static int oreCoalRarity;
	public static int oreEmeraldRarity;
	
	public static boolean regenerateOres;
	public static boolean regenerateDungeons;
	public static boolean refreshMiddle;
	
	public static int maxPlayersPerGroup;
	public static int minPlayersPerGroup;
	
	public static long timeTillWalls;
	
	public static long updateIntervall;
	
	/**0: Id; 1: Meta; 2: HowMany*/
	public static int[] reward;
	
	public static EnumSaveSpawns saveSpawns;
	
	public ConfigManager(WallsPlugin pluginTmp)
	{
		this.plugin = pluginTmp;
		
		reward = new int[3];
		
		this.loadConfig();
	}
	
	
	/**
	 * 
	 * LOAD OR CREATE THE CONFIG
	 * 
	 ***/
	public void loadConfig()
	{
		
		String path22 = "config.oneric.walls.settings.general.reward.amount";
		plugin.getConfig().addDefault(path22, 0);
		reward[2] = plugin.getConfig().getInt(path22, 0);
		
		String path21 = "config.oneric.walls.settings.general.reward.metaData";
		plugin.getConfig().addDefault(path21, 0);
		reward[1] = plugin.getConfig().getInt(path21, 0);
		
		String path20 = "config.oneric.walls.settings.general.reward.id";
		plugin.getConfig().addDefault(path20, 0);
		reward[0] = plugin.getConfig().getInt(path20, 0);
		
		String path19 = "config.oneric.walls.settings.general.advancedExplosionProtection";
		plugin.getConfig().addDefault(path19, false);
		betterExplosionProtection = plugin.getConfig().getBoolean(path19, false);
		

		String path18 = "config.oneric.walls.settings.general.saveSpawns(How)";
		plugin.getConfig().addDefault(path18, EnumSaveSpawns.CONFIG.name());
		String tmp = plugin.getConfig().getString(path18);
		try{
			saveSpawns = EnumSaveSpawns.valueOf(tmp);
		}catch(NullPointerException | IllegalArgumentException ex){
			saveSpawns = EnumSaveSpawns.CONFIG;
			plugin.getLogger().warning("FAILED TO LOAD 'SAVE SPAWN' VARIABLE FROM CONFIG; SET IT TO 'CONFIG' !");
		}
		
		
		String path17 = "config.oneric.walls.settings.general.refreshMiddle";
		plugin.getConfig().addDefault(path17, true);
		refreshMiddle = plugin.getConfig().getBoolean(path17, true);
		
		
		String path16 = "config.oneric.walls.settings.general.updateIntervall(sec/20)";
		plugin.getConfig().addDefault(path16, 20);
		updateIntervall = plugin.getConfig().getLong(path16);
		
		
		String path15 = "config.oneric.walls.settings.general.timeTillRemoveWalls(sec)";
		plugin.getConfig().addDefault(path15, 900);
		timeTillWalls = plugin.getConfig().getLong(path15);
		
		String path14 = "config.oneric.walls.settings.general.minPlayersperGroup";
		plugin.getConfig().addDefault(path14, 2);
		minPlayersPerGroup = plugin.getConfig().getInt(path14);
		
		
		String path13 = "config.oneric.walls.settings.general.maxPlayersperGroup";
		plugin.getConfig().addDefault(path13, 4);
		maxPlayersPerGroup = plugin.getConfig().getInt(path13);
		
		String path12 = "config.oneric.walls.settings.regenerate.dungeons";
		plugin.getConfig().addDefault(path12, true);
		regenerateDungeons = plugin.getConfig().getBoolean(path12);
		
		String path11 = "config.oneric.walls.settings.regenerate.ores";
		plugin.getConfig().addDefault(path11, true);
		regenerateOres = plugin.getConfig().getBoolean(path11);
		
		String path10 = "config.oneric.walls.settings.rarity.ores.diamond";
		plugin.getConfig().addDefault(path10, 0);
		oreDiamondRarity = plugin.getConfig().getInt(path10);
		
		String path9 = "config.oneric.walls.settings.rarity.ores.redstone";
		plugin.getConfig().addDefault(path9, 8);
		oreRedstoneRarity = plugin.getConfig().getInt(path9);
		
		String path8 = "config.oneric.walls.settings.rarity.ores.gold";
		plugin.getConfig().addDefault(path8, 2);
		oreGoldRarity = plugin.getConfig().getInt(path8);
		
		String path7 = "config.oneric.walls.settings.rarity.ores.coal";
		plugin.getConfig().addDefault(path7, 20);
		oreCoalRarity = plugin.getConfig().getInt(path7);
		
		String path6 = "config.oneric.walls.settings.rarity.ores.lapis";
		plugin.getConfig().addDefault(path6, 2);
		oreLapisRarity = plugin.getConfig().getInt(path6);
		
		String path5 = "config.oneric.walls.settings.rarity.ores.iron";
		plugin.getConfig().addDefault(path5, 20);
		oreIronRarity = plugin.getConfig().getInt(path5);
		
		String path4 = "config.oneric.walls.settings.rarity.ores.emerald";
		plugin.getConfig().addDefault(path4, 0);
		oreEmeraldRarity = plugin.getConfig().getInt(path4);
		
		String path1 = "config.oneric.walls.settings.EnableBroadcast";
		plugin.getConfig().addDefault(path1, true);
		WelcomeEnable = (boolean)plugin.getConfig().getBoolean(path1);
		
		String path2 = "config.oneric.walls.settings.rarity.dungeons.d1";
		plugin.getConfig().addDefault(path2, 2);
		dungeon1Rarity = plugin.getConfig().getInt(path2);
		
		String path3 = "config.oneric.walls.settings.rarity.dungeons.d2";
		plugin.getConfig().addDefault(path3, 3);
		dungeon1Rarity = plugin.getConfig().getInt(path3);
		
		
		
		
		
		
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	
	/**
	 * Saves an Location into the config. Arguments: loc - Location to save; path - where it will be saved
	 * */
	public void saveLocation(Location loc, String path)
	{
		plugin.getConfig().addDefault(path + ".world", loc.getWorld().getName());
		plugin.getConfig().addDefault(path + ".x", loc.getX());
		plugin.getConfig().addDefault(path + ".y", loc.getY());
		plugin.getConfig().addDefault(path + ".z", loc.getZ());
		plugin.getConfig().addDefault(path + ".yaw", (double)loc.getYaw());
		plugin.getConfig().addDefault(path + ".pitch", (double)loc.getPitch());
		
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	/**
	 * Loads an Location from the config and return it.
	 * Arguments: path- where the Location is stored.
	 * - returns null if nothing is saved under this path.
	 * */
	public Location getLocation(String path)
	{
		if(plugin.getConfig().contains(path + ".world"))
		{
			
			String world;
			double x,y,z;
			float yaw, pitch;
			
			world = plugin.getConfig().getString(path + ".world");
			x = plugin.getConfig().getDouble(path + ".x");
			y = plugin.getConfig().getDouble(path + ".y");
			z = plugin.getConfig().getDouble(path + ".z");
			yaw = (float)plugin.getConfig().getDouble(path + ".yaw");
			pitch = (float)plugin.getConfig().getDouble(path + ".pitch");
			
			if(world == null)
			{
				System.out.println(" [W A R N I N G]  CAN'T FIND THIS WORLD !!!!");
				return null;
			}
			
			return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
		
		}
		else
		{
			return null;
			//return new Location(plugin.getServer().getWorld("world"), 0, 199, 0, 0, 0);
		}
		
		
		
	}
	
	
	/**
	 * Gets the return - Location for returning Walls Players.
	 * If not set yet it returns null
	 * */
	public Location getReturnLocation()
	{
		String path = "config.oneric.walls.settings.general.returnLocation";
		if(plugin.getConfig().contains(path + ".world"))
		{
			
			String world;
			double x,y,z;
			float yaw, pitch;
			
			world = plugin.getConfig().getString(path + ".world");
			x = plugin.getConfig().getDouble(path + ".x");
			y = plugin.getConfig().getDouble(path + ".y");
			z = plugin.getConfig().getDouble(path + ".z");
			yaw = (float)plugin.getConfig().getDouble(path + ".yaw");
			pitch = (float)plugin.getConfig().getDouble(path + ".pitch");
			
			if(world == null)
			{
				System.out.println(" [W A R N I N G]  CAN'T FIND THIS WORLD !!!!");
				return null;
			}
			
			return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
		
		}
		else
		{
			return null;
			//return new Location(plugin.getServer().getWorld("world"), 0, 199, 0, 0, 0);
		}
	}
	
	/**
	 * Sets the return - Location for returning Walls Players.
	 * */
	public void setReturnLocation(Location loc)
	{
		String path = "config.oneric.walls.settings.general.returnLocation";
		
		plugin.getConfig().addDefault(path + ".world", loc.getWorld().getName());
		plugin.getConfig().addDefault(path + ".x", loc.getX());
		plugin.getConfig().addDefault(path + ".y", loc.getY());
		plugin.getConfig().addDefault(path + ".z", loc.getZ());
		plugin.getConfig().addDefault(path + ".yaw", (double)loc.getYaw());
		plugin.getConfig().addDefault(path + ".pitch", (double)loc.getPitch());
		
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	public boolean hasReturnPos()
	{
		if(plugin.getConfig().contains("config.oneric.walls.settings.general.returnLocation.world"))
		{
			return true;
		}
		else{
			return false;
		}
		
	}
	
	
	
	
	

}

package oneric.bukkit.walls.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import oneric.bukkit.walls.enums.EnumArenaManagementType;
import oneric.bukkit.walls.enums.EnumRecreateType;
import oneric.bukkit.walls.utils.LocationLoader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class WallsRound{

	private WallsPlugin plugin;
	
	private WallsArena arena;
	/** Contains the usernames of the Players sorted by groups*/
	public ArrayList<ArrayList<String>> Groups; //TODO REPLACE WTIH NAMES & RECREATE WITHOUTLOAD & KILL TASK !!

	
	
	public STATE state;
	private boolean canJoin;
	public boolean canInteract;
	public boolean areWallsDown;
	
	
	
	private long startTime;
	
	public WallsRound(WallsArena arena, WallsPlugin plg)
	{
		this.arena = arena;
		this.state = STATE.WAITING;
		this.canJoin = true;
		this.areWallsDown = false;
		this.canInteract = false;
		
		this.Groups = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < arena.AnzahlGruppen; i++)
		{
			Groups.add(i, new ArrayList<String>());
		}
		
		this.plugin = plg;
		this.updateSigns();
	}
	
	
	public void setStartTime()
	{
		startTime = System.currentTimeMillis();
	}
	
	public void setUp()
	{
		
	}
	
	
	/**
	 * Called every tick. Handle all Updates and the Recreating and the Removing of the Walls
	 * */
	public void tick()
	{
		
		
		switch(state)
		{
		
		case WAITING:
			this.tickWaiting();
			break;
			
			
		case COUNTDOWN:
			this.tickCountdown();
			break;
			
			
		case BEFORE_WALLS:
			this.tickBeforeWalls();
			break;
			
		
		case AFTER_WALLS:
			this.tickAfterWalls();
			break;
			
		case RECREATING:
			//NIIICHHHTSSSSS
			break;
			
		
		
		}
			
	}
	
	public void updateSigns()
	{
		//TODO
		Set<Location> corrupted = new HashSet<Location>();
		for(Location loc : arena.getSigns())
		{
			if(loc.getBlock().getType() == Material.SIGN_POST || loc.getBlock().getType() == Material.WALL_SIGN)
			{
				Sign sign = (Sign) loc.getBlock().getState();
				sign.setLine(1, ChatColor.GRAY + this.state.toString());
				sign.update();
			}
			else{
				corrupted.add(loc);
			}
		}
		
		for(Location loc : corrupted)
		{
			this.arena.removeSign(loc);
		}
	}
	
	private void tickWaiting()
	{
		boolean flag = true;
		for(ArrayList<String> group : Groups)
		{
			if(group.size() < ConfigManager.minPlayersPerGroup)
			{
				flag = false;
				break;
			}
		}
		if(flag)
		{
			this.state = STATE.COUNTDOWN;
			this.setStartTime();
			this.updateSigns();
			/*new CountdownMessenger(5_000L, 60_000L, new CountdownMessage(){

				@Override
				public String getMessage(long t) {
					
					return ChatColor.BLUE + ((t/1000) + " seconds remaining till the Game starts !");
				}

				@Override
				public void callMessage(long t) {
					call(this.getMessage(t));
					
				}}, true).runTaskTimer(this.plugin, 0L, 100L); // All 5 seconds*/
		}
	}
	
	private long counterLastAnnounce = -1L;
	private void tickCountdown()
	{
		if(/* No need !(this.counterLastAnnounce >= 60L) &&*/ System.currentTimeMillis() >= this.startTime + 60000)
		{
			this.call(ChatColor.AQUA + " GAME STARTS !");
			this.state = STATE.BEFORE_WALLS;
			this.canJoin = false;
			this.setStartTime();
			this.resetPlayerStats();
			this.canInteract = true;
			
			this.updateSigns();
			this.counterLastAnnounce = -1L;
			
			return;
		}
		else if(!(this.counterLastAnnounce >= 59L) && System.currentTimeMillis() >= this.startTime + 59000)
		{
			this.call(ChatColor.RED + "1" + ChatColor.BLUE + " seconds till the Game will start !");
			this.counterLastAnnounce = 59L;
			return;
		}
		else if(!(this.counterLastAnnounce >= 58L) && System.currentTimeMillis() >= this.startTime + 58000)
		{
			this.call(ChatColor.RED + "2" + ChatColor.BLUE + " seconds till the Game will start !");
			this.counterLastAnnounce = 58L;
			return;
		}
		else if(!(this.counterLastAnnounce >= 57L) && System.currentTimeMillis() >= this.startTime + 57000)
		{
			this.call(ChatColor.RED + "3" + ChatColor.BLUE + " seconds till the Game will start !");
			this.counterLastAnnounce = 57L;
			return;
		}
		else if(!(this.counterLastAnnounce >= 55L) && System.currentTimeMillis() >= this.startTime + 55000)
		{
			this.call(ChatColor.BLUE + " 5 seconds till the Game will start !");
			this.counterLastAnnounce = 55L;
			return;
		}
		else if(!(this.counterLastAnnounce >= 50L) && System.currentTimeMillis() >= this.startTime + 50000)
		{
			this.call(ChatColor.BLUE + " 10 seconds till the Game will start !");
			this.counterLastAnnounce = 50L;
			return;
		}
		else if(!(this.counterLastAnnounce >= 45L) && System.currentTimeMillis() >= this.startTime + 45000)
		{
			this.call(ChatColor.BLUE + " 15 seconds till the Game will start !");
			this.counterLastAnnounce = 45L;
			return;
		}
		else if(!(this.counterLastAnnounce >= 30L) && System.currentTimeMillis() >= this.startTime + 30000)
		{
			this.call(ChatColor.BLUE + " 30 seconds till the Game will start !");
			this.counterLastAnnounce = 30L;
			return;
		}
		else if(!(this.counterLastAnnounce >= 15L) && System.currentTimeMillis() >= this.startTime + 15000)
		{
			this.call(ChatColor.BLUE + " 45 seconds till the Game will start !");
			this.counterLastAnnounce = 15L;
			return;
		}
		else if(!(this.counterLastAnnounce >= 0L) && System.currentTimeMillis() >= this.startTime)
		{
			//Bukkit.getScheduler().runTaskTimer(this.plugin, task, 0, period)
			this.call(ChatColor.BLUE + " 60 seconds till the Game will start !");
			this.counterLastAnnounce = 0L;
			return;
		}
	}
	
	
	private void tickBeforeWalls()
	{
		
		
		if(System.currentTimeMillis() >= startTime + ConfigManager.timeTillWalls * 1000)
		{
			this.call(ChatColor.AQUA + "WALLS HAVE BEEN REMOVED, OTHER GRUOPS CAN NOW HUNT YOU");
			new ArenaManagement(arena, WallsPlugin.me).removeWalls(arena.xWide, arena.yWide);
			this.state = STATE.AFTER_WALLS;
			this.areWallsDown = true;
			this.updateSigns();
			this.checkForWinners();
		}
		else if(!(this.counterLastAnnounce <= 5L) && System.currentTimeMillis() >= (startTime + ConfigManager.timeTillWalls * 1000) - 5_000 )
		{
			this.call(ChatColor.AQUA + "Walls will fall in 5 sec !");
			this.counterLastAnnounce = 5L;
		}
		else if(!(this.counterLastAnnounce <= 15L) && System.currentTimeMillis() >= (startTime + ConfigManager.timeTillWalls * 1000) - 15_000 )
		{
			this.call(ChatColor.AQUA + "Walls will fall in 15 sec !");
			this.counterLastAnnounce = 15L;
		}
		else if(!(this.counterLastAnnounce <= 30L) && System.currentTimeMillis() >= (startTime + ConfigManager.timeTillWalls * 1000) - 30_000 )
		{
			this.call(ChatColor.AQUA + "Walls will fall in 30 sec !");
			this.counterLastAnnounce = 30L;
		}
		else if(!(this.counterLastAnnounce <= 60L) && System.currentTimeMillis() >= (startTime + ConfigManager.timeTillWalls * 1000) - 60_000 )
		{
			this.call(ChatColor.AQUA + "Walls will fall in 60 sec !");
			this.counterLastAnnounce = 60L;
		}
		else if(!(this.counterLastAnnounce <= 120L) && System.currentTimeMillis() >= (startTime + ConfigManager.timeTillWalls * 1000) - 120_000 )
		{
			this.call(ChatColor.AQUA + "Walls will fall in 2 min !");
			this.counterLastAnnounce = 120L;
		}
		else if(!(this.counterLastAnnounce <= 180L) && System.currentTimeMillis() >= (startTime + ConfigManager.timeTillWalls * 1000) - 180_000 )
		{
			this.call(ChatColor.AQUA + "Walls will fall in 3 min !");
			this.counterLastAnnounce = 180L;
		}
		
		
	}
	
	
	private void tickAfterWalls() {}
	
	public void checkForWinners()
	{
		
		if(this.state == STATE.WAITING || this.state == STATE.COUNTDOWN)
		{
			return;
		}
		
		
		//boolean hasWon = false;
		int winnerTeam = -1;
		int counter = 0;
		
	
		for (int i = 0; i < this.Groups.size(); i++)
		{
			if (this.Groups.get(i).isEmpty())
			{
				counter++;
			}
			else
			{
				winnerTeam = i;
			}
		}
		if(counter == this.Groups.size())
		{
			WallsPlugin.me.getServer().broadcastMessage(ChatColor.RED + "All Teams are dead ! Don't know what to do. Please refresh this Arena manually ! Arena : " + this.arena.getName());
		}
		else if(counter == this.Groups.size() - 1)
		{
			WallsPlugin.me.getServer().broadcastMessage(
					ChatColor.GREEN + "The Group " + ChatColor.GOLD
							+ (winnerTeam + 1) + ChatColor.GREEN
							+ " has won a Walls - Round !");
			WallsPlugin.me.getServer().broadcastMessage(ChatColor.GREEN + "members are :");
			for(String p : this.Groups.get(winnerTeam))
			{
				WallsPlugin.me.getServer().broadcastMessage(ChatColor.GOLD + "       " + p);
			}
			
			
			
			
			//If UNEQUAL LOAD
			if(this.getArena().arenaRegeneratingWay != EnumRecreateType.LOAD)
			{
				ArrayList<String> winners = new ArrayList<String>();
				
				for(String p : this.Groups.get(winnerTeam))
				{
					winners.add(p);
				}
				
				this.Groups.get(winnerTeam).clear();
				
				plugin.getServer().broadcastMessage("NO-LOAD");
				for(String p : winners)
				{
					Bukkit.getPlayer(p).sendMessage("Teleport 1");
					//this.removePlayer(p.getName());
					Bukkit.getPlayer(p).teleport(new Location(WallsPlugin.me.getServer().getWorld(this.getArena().currentWorld), 996, 64, 996));
					this.doLeavingStuff(p, false, false);
					
				}
				
				this.state = STATE.RECREATING;
				this.updateSigns();
				Bukkit.getScheduler().runTaskLater(plugin, new RecreateWithoutLoad(this.getArenaManager(), winners, plugin), 10);
				
				
				
				
				
				
				
			}
			//If Equals LOAD
			else
			{
				plugin.getServer().broadcastMessage("LOAD");
				ArrayList<String> winners = this.Groups.get(winnerTeam);
				
				this.Groups.get(winnerTeam).clear();
				
				for(String p : winners)
				{
					Bukkit.getPlayer(p).teleport(WallsPlugin.me.configManager.getReturnLocation());
					this.doLeavingStuff(p, false, true);
					Bukkit.getPlayer(p).getInventory().addItem(new ItemStack(ConfigManager.reward[0], ConfigManager.reward[2], (short)ConfigManager.reward[1]));
				}
				
				this.Groups.get(winnerTeam).clear();
				this.state = STATE.RECREATING;
				this.updateSigns();
				Bukkit.getScheduler().runTaskLater(plugin, new RecreateWithLoad(this.getArenaManager(), this.plugin), 10); 
			}
			
			/** Wird im Task gekillt */
			
			//IMMER
			//plugin.updater.killInNextUpdate(taskNumber);
			//System.out.println("Killed Round " + taskNumber + " after a successful Regeneration !");
			this.state = STATE.RECREATING;
			this.updateSigns();
			
			/*
			this.state = STATE.WAITING;
			this.canJoin = true;
			this.areWallsDown = false;
			this.canInteract = false;
			*/
			
			
		}
			
		
		
		
	}
	 
	
	
	
	
	
	/**
	 * Sends a message to all active Players.
	 * */
	public void call(String message)
	{
		for(ArrayList<String> group : this.Groups)
		{
			for(String p : group)
			{
				Bukkit.getPlayer(p).sendMessage(message);
			}
		}
	}
	
	
	
	/**
	 * Reset all Player States,Level, etc
	 * */
	public void resetPlayerStats()
	{
		for(ArrayList<String> group : this.Groups)
		{
			for(String player : group)
			{
				Bukkit.getPlayer(player).setHealth(20);
				Bukkit.getPlayer(player).setFoodLevel(20);
				//Bukkit.getPlayer(player).setExp(0);
				//Bukkit.getPlayer(player).setLevel(0);
				Bukkit.getPlayer(player).setFireTicks(0);
			}
		}
	}
	
	
	
	/**
	 * Sends a message to all Players in this group.
	 * */
	public boolean callGroup(String message, int group)
	{
		if(this.Groups.size() > group)
		{
			for(String p : this.Groups.get(group))
			{
				Bukkit.getPlayer(p).sendMessage(message);
			}
			return true;
		}
		
		return false;
	}
	
	
	
	/**
	 * 
	 * Add / Remove Player to List, look if the group is full or if other groups need People
	 * 
	 * */
	
	public void addPlayer(String p, Random rand)
	{
		if(canJoin)
		{
			int groupNumber = 0;
			int playerCount = ConfigManager.maxPlayersPerGroup;
			
			for(int i = 0; i < this.Groups.size(); i++)
			{
				if(this.Groups.get(i).size() < playerCount)
				{
					groupNumber = i;
					playerCount = this.Groups.get(i).size();
				}
			}
			
			if(playerCount >= ConfigManager.maxPlayersPerGroup)
			{
				Bukkit.getPlayer(p).sendMessage(ChatColor.RED + "All groups are full, you can't join !");
				return;
			}
			else
			{
				this.addPlayerToListAndTeleport(p, groupNumber+1);
			}
			
			
		}
	}
	
	
	public void removePlayer(String pName)
	{
		for(int groupIndex = 0; groupIndex < Groups.size(); groupIndex++)
		{
			for(int playerIndex = 0; playerIndex < Groups.get(groupIndex).size(); playerIndex++)
			{
				if(Groups.get(groupIndex).get(playerIndex).equals(pName))
				{
					Groups.get(groupIndex).remove(playerIndex);
				}
			}
		}
		
	}
	
	public boolean hasPlayer(String p)
	{
		for(ArrayList<String> group : Groups)
		{
			if(group.contains(p))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void addPlayer(String p, int group)
	{
		if(canJoin && group < this.Groups.size() && group >= 0)
		{
			
			int underMin = 0;
			if(Groups.get(group-1).toArray().length < ConfigManager.maxPlayersPerGroup)
			{
				boolean flag = true;
				if(!(Groups.get(group-1).toArray().length < ConfigManager.minPlayersPerGroup))
				{
					
					for(int i = 0; i < Groups.toArray().length; i++)
					{
						if(Groups.get(i).toArray().length < ConfigManager.minPlayersPerGroup)
						{
							flag = false;
							underMin = i+1;
							break;
						}
					}
					
				}
				
				if(flag)
				{
					if(!(this.addPlayerToListAndTeleport(p, group)))
					{
						return;
					}
					
				}
				else
				{
					this.addPlayerToListAndTeleport(p, underMin);
					Bukkit.getPlayer(p).sendMessage(ChatColor.RED + "Your new group was under the Minimum and needs you to get enough players, sorry but you can't join the group " + group);
					return;
				}
			}
			else
			{
				Bukkit.getPlayer(p).sendMessage(ChatColor.RED + "This group is full, try another one !");
				//this.addPlayer(p, (new Random().nextInt(Groups.toArray().length)));
			}
			
		}
		else{
			Bukkit.getPlayer(p).sendMessage(ChatColor.RED + "Game already started !");
		}
	}
	
	private boolean addPlayerToListAndTeleport(String player, int groupNumber)
	{
		boolean succes = true;
		try
		{
			
			//Location loc = WallsPlugin.me.configManager.getLocation("spawns." + arena.arenaName.toLowerCase() + ".g" + String.valueOf(groupNumber));
			/*(Location)WallsPlugin.me.getConfig().get("spawns." + arena.getName().toLowerCase() + "." + groupNumber);*/
			//System.out.println("spawns." + arena.arenaName.toLowerCase() + ".g" + String.valueOf(groupNumber));
			
			Location loc;
			
			switch(ConfigManager.saveSpawns)
			{
			case FILE:
				try{
					loc = LocationLoader.loadLocation(arena.getName(), String.valueOf(groupNumber));
					//player.teleport(loc);
				}catch(IOException ex) {
					Bukkit.getPlayer(player).sendMessage(ChatColor.RED + "File Not Found !");
					return false;
				} catch(NumberFormatException ex) {
					Bukkit.getPlayer(player).sendMessage(ChatColor.RED + "File damaged !");
					return false;
				} catch(IllegalArgumentException ex){
					Bukkit.getPlayer(player).sendMessage(ChatColor.RED + "File damaged !");
					return false;
				}
				break;
				
				default:
					loc = WallsPlugin.me.configManager.getLocation("spawns." + arena.arenaName.toLowerCase() + ".g" + String.valueOf(groupNumber));
					//player.teleport(loc);
					break;
			}
			
			
			
			
			if(loc == null)
			{
				Bukkit.getPlayer(player).sendMessage("Failed to load Location !");
				succes = false;
				return false;
			}
			else
			{
				//tp
				Bukkit.getPlayer(player).teleport(loc);
				Bukkit.getPlayer(player).setGameMode(GameMode.SURVIVAL);
				Bukkit.getPlayer(player).setHealth(20);
				Bukkit.getPlayer(player).setFoodLevel(20);
				//player.setExp(0);
				//player.setLevel(0);
				Bukkit.getPlayer(player).setFireTicks(0);
				
				//Clear Inv
				ItemStack[] inventory = Bukkit.getPlayer(player).getInventory().getContents();
				for (int i = 0; i < inventory.length; i++) {
				    inventory[i] = null;
				 }
				Bukkit.getPlayer(player).getInventory().setContents(inventory);
				Bukkit.getPlayer(player).getInventory().clear();
				
				ItemStack[] invetoryArmor = Bukkit.getPlayer(player).getInventory().getArmorContents();
				for (int i = 0; i < invetoryArmor.length; i++) {
				    invetoryArmor[i] = null;
				 }
				Bukkit.getPlayer(player).getInventory().setArmorContents(invetoryArmor);
			}
			
			
			
		}catch(Exception ex)
		{
			//ex.printStackTrace();
			
			Bukkit.getPlayer(player).sendMessage(ChatColor.RED + "Failed to teleport you, maybe this spawnpoint don't exist, please ask an OP to set the SpawnPoint in the Arena !");
			succes = false;
			return false;
		}
		
		if(succes)
		{
			Groups.get(groupNumber-1).add(player);
			Bukkit.getPlayer(player).sendMessage(ChatColor.AQUA + "You joined the Group Number " + groupNumber);
			return true;
		}
		else{
			return false;
		}
		
		
	}
	
	public void doLeavingStuff(String player, boolean drop, boolean teleport)
	{
		this.doLeavingStuff(player, WallsPlugin.me, drop, teleport);
	}
	
	public void doLeavingStuff(String player, WallsPlugin plugin, boolean drop, boolean teleport)
	{
		Bukkit.getPlayer(player).setHealth(20);
		Bukkit.getPlayer(player).setFoodLevel(20);
		Bukkit.getPlayer(player).setExp(0);
		Bukkit.getPlayer(player).setLevel(0);
		Bukkit.getPlayer(player).setFireTicks(0);
		
		
		PlayerInventory inv = Bukkit.getPlayer(player).getInventory();
		Location l = Bukkit.getPlayer(player).getLocation();

		if(drop)
		{
			
			for (ItemStack i : inv.getContents()) {
				if (i != null)
					l.getWorld().dropItemNaturally(l, i);
			}
			for (ItemStack i : inv.getArmorContents()) {
				if ((i != null) && (i.getType() != Material.AIR)) {
					l.getWorld().dropItemNaturally(l, i);
				}
			}
			
		}
		
		ItemStack[] inventory = Bukkit.getPlayer(player).getInventory().getContents();
		for (int i = 0; i < inventory.length; i++) {
		    inventory[i] = null;
		 }
		Bukkit.getPlayer(player).getInventory().setContents(inventory);
		
		ItemStack[] invetoryArmor = Bukkit.getPlayer(player).getInventory().getArmorContents();
		for (int i = 0; i < invetoryArmor.length; i++) {
		    invetoryArmor[i] = null;
		 }
		Bukkit.getPlayer(player).getInventory().setArmorContents(invetoryArmor);
	      
	     
		if(teleport)
		{
			Bukkit.getPlayer(player).teleport(plugin.configManager.getReturnLocation());
		}
	    
	}
	
	
	/**
	 * Returns the ArenaManager for the current Walls-Arena.
	 * */
	public ArenaManagement getArenaManager()
	{
		if(arena.getManagemnetType() == EnumArenaManagementType.ADVANCED)
		{
			return new ArenaManagementAdvanced(arena, WallsPlugin.me);
		}
		else
		{
			return new ArenaManagementBase(arena, WallsPlugin.me);
		}
			
		
	}
	
	
	/**
	 * Returns the actual WallsArena
	 * */
	public WallsArena getArena()
	{
		return this.arena;
	}
	
	/**
	 * The State which the Round can be.
	 * */
	public enum STATE {
		
		WAITING,
		COUNTDOWN,
		BEFORE_WALLS,
		AFTER_WALLS,
		RECREATING
		
		
		
	}
	
	
	

}

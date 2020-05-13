package oneric.bukkit.walls.events;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import oneric.bukkit.walls.src.ConfigManager;
import oneric.bukkit.walls.src.WallsArena;
import oneric.bukkit.walls.src.WallsPlugin;
import oneric.bukkit.walls.utils.ArenaLoader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerEvents implements Listener{
	
private WallsPlugin plugin;
	
	
	public PlayerEvents(WallsPlugin pluginTmp)
	{
		this.plugin = pluginTmp;
		//plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		if(ConfigManager.WelcomeEnable)
		{
			event.getPlayer().sendMessage(ChatColor.BLUE + "Welcome " + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.BLUE + " to this awesome Server"/* + ", which use The Walls Plugin by cmdmole aka Oneric !"*/);
			plugin.getServer().broadcastMessage(ChatColor.BLUE + "There are " + ChatColor.YELLOW + plugin.getServer().getOnlinePlayers().length + ChatColor.BLUE + " Players online !");
		}
		if(event.getPlayer().isOp() && !plugin.configManager.hasReturnPos())
		{
			event.getPlayer().sendMessage(ChatColor.RED + "WANRNING : You have not set a return POS yet ! Do this with /wallsSetReturnPos !!");
		}
		
	}
	
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event)
	{
		int[] indexes = plugin.getWallsPlayer( event.getPlayer().getName() );
		if(indexes != null)
		{
			plugin.wallsRounds.get(indexes[0]).Groups.get(indexes[1]).remove(indexes[2]);
			plugin.wallsRounds.get(indexes[0]).call(
					ChatColor.GOLD + event.getPlayer().getName()
							+ ChatColor.AQUA + " has leaved the Arena.");
			
			
			plugin.wallsRounds.get(indexes[0]).doLeavingStuff(event.getPlayer().getName(), plugin, true, true);
			plugin.wallsRounds.get(indexes[0]).checkForWinners();
		}
	}
	
	@EventHandler
	public void onPlayerDie(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			int[] indexes = plugin.getWallsPlayer( player.getName() );
			if(indexes != null)
			{
				
				if(!plugin.wallsRounds.get(indexes[0]).canInteract)
				{
					event.setCancelled(true);
					return;
				}
				
				
				Entity damager = null;
				try{
					damager = ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
				} catch(NullPointerException ex) {return;}
				
				if(damager instanceof Player)
				{
					int[] damagerIndex = plugin.getWallsPlayer( ((Player)damager).getName() );
					if(damagerIndex != null)
					{
						if(damagerIndex[0] == indexes[0] && damagerIndex[1] == indexes[1])
						{
							event.setCancelled(true);
							return;
						}
					}
				}
				
				
				if(player.getHealth() <= event.getDamage())
				{	
					String reason = "";
					try{
						//Entity damager = ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
						if(damager == null) reason = ChatColor.AQUA + " died.";
						else if(damager instanceof Player) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_RED + ((Player)damager).getName();
						else if((damager instanceof Creeper)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Creeper";
						else if((damager instanceof Zombie)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Zombie";
						else if((damager instanceof Spider)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Spider";
						else if((damager instanceof CaveSpider)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "CaveSpider";
						else if((damager instanceof Enderman)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Enderman";
						else if((damager instanceof Silverfish)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Silverfish";
						else if(damager.getType() == EntityType.MAGMA_CUBE) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "MagmaCube";
						else if(damager.getType() == EntityType.SLIME) reason = ChatColor.AQUA + "was killed by " + ChatColor.DARK_PURPLE + "Slime";
						else if((damager instanceof Wolf)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Wolf";
						else if((damager instanceof PigZombie)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "PigZombie";
						else  if((damager instanceof IronGolem)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "IronGolem";
						else if((damager instanceof Giant)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Giant";
						else if(damager instanceof Blaze) reason =  ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Blaze";
						else if(damager.getType() == EntityType.WITCH) reason =  ChatColor.AQUA + " was killed by an " + ChatColor.DARK_PURPLE + "Bitch, ehhhm... Witch !";
						else if(damager.getType() == EntityType.SKELETON) {
				            Skeleton skelett = (Skeleton)damager;
				            if (skelett.getSkeletonType().equals(Skeleton.SkeletonType.NORMAL)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Skeleton";
				            if (skelett.getSkeletonType().equals(Skeleton.SkeletonType.WITHER)) reason = ChatColor.AQUA + " was killed by " + ChatColor.DARK_PURPLE + "Wither-Skeleton";
				          }
						else if(player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) reason = ChatColor.AQUA + "was killed by " + ChatColor.DARK_GRAY + "an angry Mob !";
						else reason = ChatColor.AQUA + " died.";
					}catch(Exception ex)
					{
						reason = ChatColor.AQUA + " died.";
					}
					
					
					plugin.wallsRounds.get(indexes[0]).Groups.get(indexes[1]).remove(indexes[2]);
					plugin.wallsRounds.get(indexes[0]).call(
							ChatColor.GOLD + player.getName()
									+ ChatColor.AQUA + ", from Team " + ChatColor.GOLD
									+ (indexes[1]+1) + reason);
					event.setCancelled(true);
					
					
					plugin.wallsRounds.get(indexes[0]).doLeavingStuff(player.getName(), plugin, true, true);
					
					plugin.wallsRounds.get(indexes[0]).checkForWinners();
					
					
				}
			}
		}
		
	}
	
	
	
	
	
	//TODO
	
		 @EventHandler(priority=EventPriority.HIGHEST)
		  public void onPlayerMove(PlayerMoveEvent event) {
			int[] indexes = plugin.getWallsPlayer(event.getPlayer().getName());
		    if (indexes != null)
		    {
		    	if(!plugin.wallsRounds.get(indexes[0]).canInteract)
		    	{
		    		if (((event.getTo().getX() != event.getFrom().getX()) || (event.getTo().getZ() != event.getFrom().getZ()))) {
		    			
		    			this.allowTeleportFor.add(event.getPlayer().getName());
		    			event.setTo(event.getFrom());
		    			
		    			//event.setCancelled(true);
		                return;
		            }
		    	}
		    	
		    }
		      
		    
		  }
		 
		 
		 
		 private Set<String> allowTeleportFor = new HashSet<String>();
		//TODO
			@EventHandler(priority=EventPriority.HIGH)
			public void onPlayerTeleport(PlayerTeleportEvent event)
			{
				if(plugin.isWallsPlayer( event.getPlayer().getName()) /*&& event.*/)
				{
					if(this.allowTeleportFor.remove(event.getPlayer().getName()))
					{
						return;
					} 
					event.getPlayer().sendMessage(ChatColor.RED + "You are in a Walls Game, you can't teleport ! Use /wallsLeave instead.");
					event.setCancelled(true);
				}
			}
		 
		 
		 
		 

		  @EventHandler
		  public void onPlayerDropItem(PlayerDropItemEvent event) {
			  int[] indexes = plugin.getWallsPlayer(event.getPlayer().getName());
			    if (indexes != null)
			    {
			    	if(!plugin.wallsRounds.get(indexes[0]).canInteract) event.setCancelled(true);
			    }
		  }

		  @EventHandler
		  public void onPlayerPickupItem(PlayerPickupItemEvent event) {
			  int[] indexes = plugin.getWallsPlayer(event.getPlayer().getName());
			    if (indexes != null)
			    {
			    	if(!plugin.wallsRounds.get(indexes[0]).canInteract) event.setCancelled(true);
			    }
		  }
		  
		  
		  
		  @EventHandler
		  public void onPlayerRightClick(PlayerInteractEvent event)
		  {
			  if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
			  {
				  String arenaName = null;
				  if( (arenaName = this.plugin.signCreator.get( event.getPlayer().getName())) != null)
				  {
					  if(event.getClickedBlock().getType() == Material.WALL_SIGN || event.getClickedBlock().getType() == Material.SIGN_POST)
					  {
						  //TODO Saving the change and associated with the arena
						  
						  if(plugin.isWallsSign(event.getClickedBlock().getLocation()) != null)
						  {
							  event.getPlayer().sendMessage(ChatColor.RED + "THis sign is already associated with an Arena. ABorting !!");
							  this.plugin.signCreator.remove(event.getPlayer().getName());
							  return;
						  }
						  
						  final WallsArena arena = this.plugin.arenen.get( this.plugin.arenaMap.get(arenaName) );
						  
						  Sign sign = (Sign)event.getClickedBlock().getState();
						  
						  sign.setLine(0, ChatColor.BLUE + arenaName);
						  sign.setLine(1, ChatColor.GRAY + "UNKNOWN");
						  sign.setLine(2, ChatColor.DARK_GREEN + (arena.AnzahlGruppen + " groups"));
						  sign.setLine(3, /*ChatColor.BLACK + */(arena.getXWide() + " x " + arena.getZWide()) );
						  
						  sign.update();
						  
						  //event.getClickedBlock().setData(sign.getData());
						  
						  this.plugin.signCreator.remove(event.getPlayer().getName());
						  
						  arena.addSigns(event.getClickedBlock().getLocation());
						  
						  Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable(){
							 @Override
							 public void run()
							 {
								 try {
									ArenaLoader.saveArena(arena.getName(), arena);
								} catch (IOException e) {
									e.printStackTrace();
								} 
							 }
						  });
						  
						  
						  event.getPlayer().sendMessage(ChatColor.GREEN + "Finished !");
						  return;
					  }
					  else
					  {
						  event.getPlayer().sendMessage(ChatColor.RED + "Not a sign ! Aborting !");
						  return;
					  }
				  }
				  
				  
				  else if(event.getClickedBlock().getType() == Material.WALL_SIGN || event.getClickedBlock().getType() == Material.SIGN_POST)
				  {
					  
					  //System.out.println("Hey it's a SIGN !");
					  
					  boolean flag = false;
					  String nameOfArena = null;
					  for(WallsArena arena: this.plugin.arenen)
					  {
						  for(Location signLoc : arena.getSigns())
						  {
							  if(signLoc.getWorld().getName().equals(event.getClickedBlock().getLocation().getWorld().getName()) && 
									  signLoc.getBlockX() == event.getClickedBlock().getLocation().getBlockX() &&
									  signLoc.getBlockY() == event.getClickedBlock().getLocation().getBlockY() &&
									  signLoc.getBlockZ() == event.getClickedBlock().getLocation().getBlockZ() )
							  {
								  flag = true;
								  nameOfArena = arena.getName();
								  break;
							  }
						  }
						  if(flag)
							  break;
					  }
					  
					  if(flag)
					  {
						 // System.out.println("Hey it's also an JOIN Thingy !");
						  event.getPlayer().chat(("/wallsJoin " + nameOfArena));
					  }
					  
					  
				  }
				  
				  
				  
				  
			  }
				  
		  }
	
	
	
	

}

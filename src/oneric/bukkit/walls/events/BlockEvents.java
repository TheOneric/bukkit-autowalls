package oneric.bukkit.walls.events;

import oneric.bukkit.walls.src.WallsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEvents implements Listener{
	
private WallsPlugin plugin;
	
	
	public BlockEvents(WallsPlugin pluginTmp)
	{
		this.plugin = pluginTmp;
		//plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		
		if(event.getBlock().getType() == Material.SIGN_POST || event.getBlock().getType() == Material.WALL_SIGN)
		{
			String arenaName;
			if((arenaName = this.plugin.isWallsSign(event.getBlock().getLocation())) != null)
			{
				if(event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.CREATIVE)
				{
					this.plugin.arenen.get( this.plugin.arenaMap.get(arenaName) ).removeSign(event.getBlock().getLocation());
					event.getPlayer().sendMessage(ChatColor.RED + "Successfully removed Arena Sign !");
					event.setCancelled(false);
					return;
				}
				else
				{
					event.setCancelled(true);
					return;
				}
			}
		}
		
		
		if(event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.CREATIVE)
		{
			
		}
		else
		{
			if(event.getBlock().getType() == Material.SOUL_SAND && plugin.isArenaWorld( event.getBlock().getWorld().getName() ))
			{
				event.setCancelled(true);
				return;
			}
			else if(plugin.isArenaCenterBlock(event.getBlock()))
			{
				event.setCancelled(true);
				return;
			}
			
			
			int[] indexes = plugin.getWallsPlayer(event.getPlayer().getName());
		    if (indexes != null)
		    {
		    	if(!plugin.wallsRounds.get(indexes[0]).canInteract) event.setCancelled(true);
		    }
		}
	}
	
	
	
	
	@EventHandler
	public void onBlockSet(BlockPlaceEvent event)
	{
		if(event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		
		
		
		if(event.getBlock().getType() == Material.SOUL_SAND && plugin.isArenaWorld( event.getBlock().getWorld().getName() ))
		{
			event.setCancelled(true);
			return;
		}
		else if(plugin.isArenaCenterBlock(event.getBlock()))
		{
			event.setCancelled(true);
			return;
		}
		else if(event.getBlock().getY() >= (event.getBlock().getWorld().getMaxHeight()-3))
		{
			event.setCancelled(true);
			return;
		}
		
		
		int[] indexes = plugin.getWallsPlayer(event.getPlayer().getName());
	    if (indexes != null)
	    {
	    	if(!plugin.wallsRounds.get(indexes[0]).canInteract) event.setCancelled(true);
	    }
		
		
	}
	
	

}

package oneric.bukkit.walls.events;

import java.util.List;

import oneric.bukkit.walls.src.WallsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockSpreadEvent;
//import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OtherEvents implements Listener{
	
	private WallsPlugin plugin;
	
	
	public OtherEvents(WallsPlugin pluginTmp)
	{
		this.plugin = pluginTmp;
		//plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	
	
	
	
	
	
	
	
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		List<org.bukkit.block.Block> list = event.blockList();
		
		if( plugin.isArenaWorld( event.getLocation().getWorld().getName() ))
		{
		
			/*if(ConfigManager.betterExplosionProtection)
			{
				
				HashMap<Location, int[]> prevented = new HashMap<Location, int[]>(); 
				
				for (org.bukkit.block.Block b : list) {

					if(b.getTypeId() == Block.slowSand)
					{
						prevented.put(b.getLocation(), new int[]{b.getTypeId(), b.getData()});
					}
					else if(plugin.isArenaCenterBlock(b))
					{
						prevented.put(b.getLocation(), new int[]{b.getTypeId(), b.getData()});
					}
				}
				
				
				Bukkit.getScheduler().runTaskLater(plugin, new ResetExplodedBlocks(prevented), 1);
				
				
				
			}
			else
			{*/
				
				for (org.bukkit.block.Block b : list) {

					if(b.getType() == Material.SOUL_SAND){

						event.setCancelled(true);
						return;
					}
					else if(plugin.isArenaCenterBlock(b))
					{
						event.setCancelled(true);
						return;
					}
				}

				
			//}
			
		}
		
		
		for(org.bukkit.block.Block b : list)
		{
			if(b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)
			{
				String arenaName;
				if((arenaName = this.plugin.isWallsSign(b.getLocation())) != null)
				{
					this.plugin.arenen.get( this.plugin.arenaMap.get(arenaName) ).removeSign(b.getLocation());
				}	
			}
				
		}
		
		
	}
	
	/*
	private class ResetExplodedBlocks implements Runnable
	{

		private HashMap<Location, int[]> prevented;
		private World world;
		
		public ResetExplodedBlocks(HashMap<Location, int[]> map, World w)
		{
			this.prevented = map;
			this.world = w;
		}
		
		
		@Override
		public void run() {
			
			for (Map.Entry<Location, int[]> entry : prevented.entrySet())
	        {
				Location loc = entry.getKey();
				int[] datas = entry.getValue();
	            loc.getWorld().getBlockAt(loc).setTypeId(datas[0]);
	            loc.getWorld().getBlockAt(loc).setData((byte)datas[1]);
	        }
			
			List<Entity> entList = this.world.getEntities();//get all entities in the world
			 
	        for(int i = 0; i < entList.toArray().length; i++){//loop through the list
	            if (entList.get(i) instanceof Item){//make sure we aren't deleting mobs/players
	            	if(((Item)entList.get(i)).getItemStack().getTypeId() == Block.slowSand)
	            	entList.get(i).remove();//remove it
	            	
	            }
	        }
			
			
			
			
		}
		
	}*/
	
	
	
	
	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent event)
	{
		if(plugin.isArenaWorld( event.getBlock().getWorld().getName() ))
		{
			List<org.bukkit.block.Block> blocks = event.getBlocks();
			
			for(org.bukkit.block.Block block : blocks)
			{
				if(block.getType() == Material.SOUL_SAND)
				{
					event.setCancelled(true);
					return;
				}
				else if(plugin.isArenaCenterBlock(block))
				{
					event.setCancelled(true);
					return;
				}
			}
		}
		//event.setCancelled(true);
	}
	
	@EventHandler
	public void onPistonReturn(BlockPistonRetractEvent event)
	{
		if(event.isSticky())
		{
			if (plugin.isArenaWorld(event.getBlock().getWorld().getName()))
			{
				org.bukkit.block.Block block = event.getBlock().getRelative(event.getDirection(), 2);

				
					if (block.getType() == Material.SOUL_SAND) {
						event.setCancelled(true);
						return;
					} 
					else if (plugin.isArenaCenterBlock(block)) {
						event.setCancelled(true);
						return;
					
				}
			}
		}
		//event.setCancelled(true);
	}
	
	
	
	
	
	
	/*
	public void onPlayerDie(PlayerDeathEvent event)
	{
		int[] indexes = plugin.getWallsPlayer( event.getEntity() );
		if(indexes != null)
		{
			
			plugin.wallsRounds.get(indexes[0]).Groups.get(indexes[1]).remove(indexes[2]);
			plugin.wallsRounds.get(indexes[0]).call(
					ChatColor.GOLD + event.getEntity().getName()
							+ ChatColor.AQUA + ", from Team " + ChatColor.GOLD
							+ indexes[1] + ChatColor.AQUA + " because: "
							+ event.getDeathMessage());
			
			event.getEntity().teleport(plugin.configManager.getReturnLocation());
			event.getEntity().setHealth(20);
			event.getEntity().setFoodLevel(20);
			event.getEntity().setExp(0);
			event.getEntity().setFireTicks(0);
			
		}
	}*/
	
	
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		int[] indexes = plugin.getWallsPlayer( event.getPlayer().getName() );
		if(indexes != null)
		{
			if(!plugin.wallsRounds.get(indexes[0]).areWallsDown)
			{
				plugin.wallsRounds.get(indexes[0]).callGroup(ChatColor.DARK_BLUE + "[G" + (indexes[1] + 1) + "] " + ChatColor.BLUE + "[" + event.getPlayer().getName() + "]" + ChatColor.RESET + event.getMessage(), indexes[1]);
				event.setCancelled(true);
			}
		}
		
		
	}
	
	
	@EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e){
        if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest){
            
        	int[] indexes = plugin.getWallsPlayer(e.getPlayer().getName());
        	if(indexes != null)
        	{
        		if(!plugin.wallsRounds.get(indexes[0]).canInteract)
        		{
        			e.setCancelled(true);
        			((Player)e.getPlayer()).sendMessage(ChatColor.RED + "In the currrent game state you're not allowed to open a chest !");
        		}
        	}
        	
        }
    }
	
	@EventHandler
	public void onFireSpread(BlockSpreadEvent e)
	{
		if(e.getSource().getType() == Material.FIRE)
		{
			if(plugin.isArenaCenterBlock( e.getBlock() ))
			{
				e.getSource().setType(Material.AIR);
				e.setCancelled(true);
			}
		}
	}
	
	
	@EventHandler
	public void onBluckBurnDown(BlockBurnEvent e)
	{
		if(plugin.isArenaCenterBlock( e.getBlock() ))
		{
			e.setCancelled(true);
		}
	}
	
	
	
	
	
	
	

}

package oneric.bukkit.walls.src;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RecreateWithoutLoad implements Runnable {

	ArenaManagement manager;
	ArrayList<String> winners; //TODO REPLACE WITH AN STRING
	WallsPlugin plugin;
	
	public RecreateWithoutLoad(ArenaManagement managerTmp, ArrayList<String> winnersTmp, WallsPlugin plg)
	{
		this.manager = managerTmp;
		this.winners = winnersTmp;
		this.plugin = plg;
	}
	
	
	@Override
	public void run() {
		
		System.out.println("------ I am running ! ------");
		manager.recreate();
		
		Bukkit.getScheduler().runTaskLater(plugin, new ResetMiddleChests(Bukkit.getPlayer(winners.get(0))), 1);
		
		for(int i = 1; i < winners.size(); i++)
		{
			Bukkit.getPlayer(winners.get(i)).teleport(plugin.configManager.getReturnLocation());
			Bukkit.getPlayer(winners.get(i)).sendMessage("Teleport 2");
		}

	}
	
	private class ResetMiddleChests implements Runnable
	{

		Player player;
		private ResetMiddleChests(Player p)
		{
			this.player = p;
		}
		
		@Override
		public void run()
		{	
			int maxHeight = player.getWorld().getHighestBlockYAt(0, 0);
			
			player.teleport(new Location(player.getWorld(), (manager.arena.xWide+10), (maxHeight-4), 10));
			this.createKapsel(player.getWorld(), (manager.arena.xWide+10), (maxHeight-4), 10, Material.BEDROCK);
			
			manager.resetMiddle(manager.arena.xWide, manager.arena.yWide);
			
			this.createKapsel(player.getWorld(), (manager.arena.xWide+10), (maxHeight-4), 10, Material.AIR);
			player.teleport(plugin.configManager.getReturnLocation());
			
			plugin.updater.killInNextUpdate(manager.arena.getName());
			
		}
		
		
		private void createKapsel(World world, int x, int y, int z, Material BlockID)
		{
			world.getBlockAt(x, y-1, z).setType(BlockID);
			
			for(int i = 0; i < 2; i++)
			{
				world.getBlockAt(x-1, y+i, z).setType(BlockID);
				world.getBlockAt(x, y+i, z-1).setType(BlockID);
				world.getBlockAt(x+1, y+i, z).setType(BlockID);
				world.getBlockAt(x, y+i, z+1).setType(BlockID);
			}
		}
		
	}

}



package oneric.bukkit.walls.src;

import java.util.ArrayList;
import java.util.List;

public class Updater implements Runnable {

	
	private WallsPlugin plugin;
	private List<String> deadTasksRunning;
	
	public Updater(WallsPlugin plg)
	{
		this.plugin = plg;
		this.deadTasksRunning = new ArrayList<String>();
	}
	
	
	@Override
	public void run() {
		
		for(WallsRound round : plugin.wallsRounds)
		{
			round.tick();
		}
		
		for(String name : deadTasksRunning)
		{
			for(int i = 0; i < plugin.wallsRounds.size(); i++)
			{
				if(plugin.wallsRounds.get(i).getArena().getName().equals(name))
				{
					plugin.wallsRounds.remove(i);
					System.out.println("----- Removed Task " + i);
				}
			}
		}
		
		deadTasksRunning.clear();
		
	}
	
	
	/**
	 * Kill an WallsRound of the given Arena in the next Tick
	 * */
	public void killInNextUpdate(String arenaName)
	{
		deadTasksRunning.add(arenaName);
	}
	

}

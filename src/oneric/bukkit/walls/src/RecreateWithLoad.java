package oneric.bukkit.walls.src;

public class RecreateWithLoad implements Runnable {

	ArenaManagement manager;
	WallsPlugin plugin;
	
	public RecreateWithLoad(ArenaManagement man, WallsPlugin plg)
	{
		manager = man;
		plugin = plg;
	}
	
	@Override
	public void run()
	{
		manager.recreate();
		plugin.killWallsRoundByArena(manager.arena.getName());
	}

}

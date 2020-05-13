package oneric.bukkit.walls.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import oneric.bukkit.walls.src.ArenaManagementBase;
import oneric.bukkit.walls.src.WallsArena;
import oneric.bukkit.walls.src.WallsPlugin;

public class ComWallFall extends WallsCommand {

	public ComWallFall(WallsPlugin plugin) {
		super(plugin, 1, true, WallsPlugin.PERMISSION_MANIPULATE);
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg)
	{
		
		if(plugin.arenaMap.containsKey(arg[0]))
		{
			
			WallsArena arena = plugin.arenen.get(plugin.arenaMap.get(arg[0]));
			ArenaManagementBase manager = new ArenaManagementBase(arena, plugin);
			
			manager.removeWalls(arena.getXWide(), arena.getZWide());
			
			return true;
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "There's no Arena with this Name !");
			return false;
		}
	}
	
	

}

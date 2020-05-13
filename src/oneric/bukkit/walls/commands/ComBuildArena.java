package oneric.bukkit.walls.commands;

import java.util.List;

import oneric.bukkit.walls.enums.EnumArenaManagementType;
import oneric.bukkit.walls.src.ArenaManagement;
import oneric.bukkit.walls.src.ArenaManagementAdvanced;
import oneric.bukkit.walls.src.ArenaManagementBase;
import oneric.bukkit.walls.src.WallsArena;
import oneric.bukkit.walls.src.WallsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class ComBuildArena extends WallsCommand {

	public ComBuildArena(WallsPlugin plugin) {
		super(plugin, 1, 2, true, WallsPlugin.PERMISSION_MANIPULATE);
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg) 
	{	
		Player player = (Player) sender;
		
		EnumArenaManagementType type = EnumArenaManagementType.BASE;
		if(arg.length == 2)
		{
			if(arg[1].equals(EnumArenaManagementType.ADVANCED.toString()))
					type = EnumArenaManagementType.ADVANCED;
			else if(!arg[1].equals(EnumArenaManagementType.BASE.toString()))
			{
				sender.sendMessage(ChatColor.RED + "Invalid Managementtype. Valid types are '" + EnumArenaManagementType.BASE.toString() + "' and '" + EnumArenaManagementType.ADVANCED.toString() + "'.");
				return false;
			}	
		}
		
		
		if (plugin.arenaMap.containsKey(arg[0])) {

			WallsArena arena = plugin.arenen.get(plugin.arenaMap.get(arg[0]));
			
			if(arg.length == 1)
				type = EnumArenaManagementType.valueOf(arena.arenaManagementType.toString());
			
			ArenaManagement manager = new ArenaManagementBase(arena, plugin);
			if(type == EnumArenaManagementType.ADVANCED)
				manager = new ArenaManagementAdvanced(arena, plugin);

			manager.recreate();

			List<Entity> entList = player.getServer().getWorld(player.getWorld().getName()).getEntities();  // get all entities in the world

			for (int i = 0; i < entList.toArray().length; i++)   // loop through the list
			{
				if (entList.get(i) instanceof Item)   // make sure we aren't deleting mobs/players
				{
					entList.get(i).remove();  // remove it
				}
			}


			sender.sendMessage(ChatColor.GREEN + "Finished !");

			return true;

		} 
		else 
		{
			sender.sendMessage(ChatColor.RED + "There's no arena with this name ! ");
			return false;
		}
		
		
	}

}

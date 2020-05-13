package oneric.bukkit.walls.commands;

import oneric.bukkit.walls.enums.EnumSaveSpawns;
import oneric.bukkit.walls.src.ConfigManager;
import oneric.bukkit.walls.src.WallsPlugin;
import oneric.bukkit.walls.utils.LocationLoader;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ComDeleteSpawn extends WallsCommand {

	public ComDeleteSpawn(WallsPlugin plugin) {
		super(plugin, 2, false, WallsPlugin.PERMISSION_MANIPULATE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg)
	{
		
		if(!this.plugin.arenaMap.containsKey(arg[0]))
		{
			sender.sendMessage(ChatColor.RED + "There's no arena called ' " + arg[0] + " '.");
			return false;
		}
		
		int groupNumber = -1;
		try{
			groupNumber = Integer.parseInt(arg[1]);
		} catch(NumberFormatException ex){
			sender.sendMessage(ChatColor.RED + "Group Number must be an Integer !");
			return false;
		}
		
		if(groupNumber < 1)
		{
			sender.sendMessage(ChatColor.RED + "The Group Number can only have a value between 1 and 8.");
			return false;
		}
		else if(groupNumber > this.plugin.arenen.get(this.plugin.arenaMap.get(arg[0])).AnzahlGruppen)
		{
			sender.sendMessage(ChatColor.RED + "This arena have only " + this.plugin.arenen.get(this.plugin.arenaMap.get(arg[0])).AnzahlGruppen + " groups. Please reduce the value of your group number.");
			return false;
		}
		
		if(ConfigManager.saveSpawns == EnumSaveSpawns.CONFIG)
			{
				String path = "spawns." + arg[0].toLowerCase() + "." + arg[1].toLowerCase();
				
				try{
					
					plugin.getConfig().set(path, null);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GREEN + "Spawnpoint " + ChatColor.BLUE + arg[1] + ChatColor.GREEN + "of Arena " + ChatColor.BLUE + arg[0] + ChatColor.GREEN + " succecfully delted.");
					return true;
					
				}catch(Exception ex) {
					
					sender.sendMessage(ChatColor.RED + "Spawnpoint doesn't exist ! Can't delete non-existing things ;) !");
					return false;
				}
				
			}
			
			
		else if(ConfigManager.saveSpawns == EnumSaveSpawns.FILE)
			{
				//String path = "spawns." + arg[0].toLowerCase() + "." + arg[1].toLowerCase();
				
				try{
					
				if(LocationLoader.alreadyExists(arg[0], arg[1]))
				{
					if(LocationLoader.delete(arg[0], arg[1]))
					{
						sender.sendMessage(ChatColor.GREEN + "Spawnpoint " + ChatColor.BLUE + arg[1] + ChatColor.GREEN + "of Arena " + ChatColor.BLUE + arg[0] + ChatColor.GREEN + " succecfully delted.");
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Spawnpoint doesn't exist ! Can't delete non-existing things ;) !");
						return false;
					}
					
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "Spawnpoint doesn't exist ! Can't delete non-existing things ;) !");
					return false;
				}
				
					
				}catch(Exception ex) {
					sender.sendMessage(ChatColor.RED + "Failed to delte the Spwanpoint !");
					return false;
				}
				
			}
		
		else
		{
			sender.sendMessage(ChatColor.RED + "Your configs seems to be damaged.");
			return false;
		}
		
		
	}

}

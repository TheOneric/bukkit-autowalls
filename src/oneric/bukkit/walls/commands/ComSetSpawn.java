package oneric.bukkit.walls.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oneric.bukkit.walls.src.ConfigManager;
import oneric.bukkit.walls.src.WallsPlugin;
import oneric.bukkit.walls.utils.LocationLoader;

public class ComSetSpawn extends WallsCommand{

	public ComSetSpawn(WallsPlugin plugin) 
	{
		super(plugin, 2, true, WallsPlugin.PERMISSION_MANIPULATE);
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg)
	{
		
		Player player = (Player) sender;
		
		Location loc = player.getLocation();
			int group = 1;
			try{
				group = Integer.parseInt(arg[1]);
			}catch(NumberFormatException ex){
				player.sendMessage(ChatColor.DARK_RED + arg[1] + ChatColor.RED + " : is not a valid group Number !");
				return false;
			}
			
			
			if(group < 1 || group > 8)
			{
				player.sendMessage(ChatColor.RED + "GroupNumber Out of Range, Please take a Number between 1 - 8");
				return false;
			}
			
		if (!plugin.arenaMap.containsKey(arg[0])) 
		{
			player.sendMessage(ChatColor.RED + "There's no arena with this name !");
			return false;
		}

			String path = ("spawns." + arg[0].toLowerCase()
					+ ".g" + (String.valueOf(group)));
			
			System.out.println(path);

			
			switch(ConfigManager.saveSpawns)
			{
			case CONFIG:
				if (!plugin.getConfig().contains(path)) {
					/*
					 * plugin.getConfig().addDefault(path, loc);
					 * plugin
					 * .getConfig().options().copyDefaults(true);
					 * plugin.saveConfig();
					 */

					plugin.configManager.saveLocation(loc, path);
					
					player.sendMessage(ChatColor.GREEN + "Spawnpoint four Group " + ChatColor.YELLOW + arg[1] + ChatColor.GREEN + " in the Arena " + ChatColor.YELLOW + arg[0] + ChatColor.GREEN + " succesfull saved !");
					
					} else {
					player.sendMessage(ChatColor.RED + "Spawnpoint already set !");
					return false;
				}
				break;
				
			case FILE:
				if(!LocationLoader.alreadyExists(arg[0], arg[1])){
					
					try {
						LocationLoader.saveLocation(arg[0], arg[1], loc);
					} catch (IOException e) {
						e.printStackTrace();
						player.sendMessage(ChatColor.RED + "Failed to save the location !");
						return false;
					}

					player.sendMessage(ChatColor.GREEN + "Spawnpoint four Group " + ChatColor.YELLOW + arg[1] + ChatColor.GREEN + " in the Arena " + ChatColor.YELLOW + arg[0] + ChatColor.GREEN + " succesfull saved !");
				} else {
					player.sendMessage(ChatColor.RED
							+ "Spawnpoint already set !");
					return false;
				}
				break;
				
			}
			
				
				
				
			

			return true;
		
		
			
		
		
	}

}

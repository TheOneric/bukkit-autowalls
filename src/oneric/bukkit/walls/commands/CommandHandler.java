package oneric.bukkit.walls.commands;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import oneric.bukkit.walls.src.ArenaManagementAdvanced;
import oneric.bukkit.walls.src.ArenaManagementBase;
import oneric.bukkit.walls.src.WallsArena;
import oneric.bukkit.walls.src.WallsPlugin;
import oneric.bukkit.walls.src.WallsRound;


public class CommandHandler {
	
	private WallsPlugin plugin;
	
	public CommandHandler(WallsPlugin pluginTmp)
	{
		this.plugin = pluginTmp;
	}
	
	
	public boolean onCommand(CommandSender sender, Command com, String comLabel, String[] arg)
	{
		if (sender instanceof Player) {
	           Player player = (Player) sender;
	          
	       
	           
	           
	   		if(player.hasPermission(WallsPlugin.PERMISSION_MANIPULATE))
	   		{
	   			
	   			if(com.getName().equalsIgnoreCase("wallsSetReturnPos"))
	   			{
	   				plugin.configManager.setReturnLocation(player.getLocation());
	   				player.sendMessage(ChatColor.GREEN + "Return position succesful saved !");
	   				return true;
	   			}
	   			
	   			if(com.getName().equalsIgnoreCase("wallsReloadConfig"))
	   			{
	   				plugin.configManager.loadConfig();
	   				player.sendMessage(ChatColor.GOLD + "Config succesful reloaded !");
	   			}
	   			
	   			
	   			if(com.getName().equalsIgnoreCase("wallsReloadArenas"))
	   			{
	   				plugin.loadArenas();
	   				player.sendMessage(ChatColor.GOLD + "Arenas succecsful reloaded !");
	   				return true;
	   			}
	   			
	   		//Default Befehl
	   			if(com.getName().equalsIgnoreCase("walls"))
	   			{
	   				if(arg.length == 0)
	   				{
	   					player.sendMessage(ChatColor.RED + "[Walls] is active !" + ChatColor.GREEN + "Thanks for using. You'll be healed :D ");
	   					player.setHealth(20);
	   					player.setFoodLevel(20);
	   					player.giveExp(2);
	   					player.sendMessage(ChatColor.RED + "[Walls]" + ChatColor.GREEN + "You are healed now !");
	   					player.getInventory().addItem(plugin.getMyHead());
	   					
	   					return true;
	   				}
	   				
	   			}
	   			
	   			

	   			// Ruestung für Op - Extra Executor
	   			// Edit Arena - Extra Executor
	   			// Create Arena
	   			// Delete Arena
	   			// Build ARena
	   			// WallFall
	   			// SetSpawn
	   			// DeleteSpawn
	   			// PlayerList
	   			
	   			
	   			
	   		}
	         
	   		if(!sender.hasPermission(WallsPlugin.PERMISSION_PLAY))
	   		{
	   			sender.sendMessage(ChatColor.RED + "Sorry, you do not have the required permission (' " + WallsPlugin.PERMISSION_PLAY + " ') to use this command. If you suspect this to be an error please contact the responsible administrator.");
	   			return false;
	   		}
	   			
	   		
	   		
	   		
	   		
	   		if(com.getName().equalsIgnoreCase("wallsArenas"))
	   		{
	   			player.sendMessage(ChatColor.BLUE + "All Walls Arenas :");
	   			player.sendMessage(ChatColor.BLUE + "-------------------------------");
	   			for(String an : plugin.arenaMap.keySet())
	   			{
	   				player.sendMessage(ChatColor.BLUE + "     " + plugin.arenaMap.get(an) + " " + an);
	   			}
	   			player.sendMessage(ChatColor.BLUE + "-------------------------------");
	   			
	   			/*
	   			player.sendMessage(ChatColor.DARK_BLUE + "TMP: ALL ARENEN AUS DER ANDREN LISTE :");
	   			int tmp = 0;
	   			for(WallsArena arena : plugin.arenen)
	   			{
	   				player.sendMessage(ChatColor.DARK_BLUE + "     " + tmp + " " + arena.getName());
	   				tmp++;
	   			}*/
	   			return true;
	   		}
	           
	   			
	   			
	           
	        } else {
	           sender.sendMessage("You must be a player!");
	           return false;
	        }
			
		
		
		return false;
		
		
		
	}

}

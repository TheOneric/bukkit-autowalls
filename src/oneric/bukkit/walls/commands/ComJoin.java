package oneric.bukkit.walls.commands;

import java.util.Random;

import oneric.bukkit.walls.src.WallsPlugin;
import oneric.bukkit.walls.src.WallsRound;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComJoin extends WallsCommand {

	public ComJoin(WallsPlugin plugin) {
		super(plugin, 1, 2, true, WallsPlugin.PERMISSION_PLAY);
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg)
	{
		
		Player player = (Player)sender;
		
		
		if(plugin.isWallsPlayer(player.getName()))
		{
				player.sendMessage(ChatColor.BLACK + "You are already in a Walls game !");
				return false;
		}
			
		if(arg.length == 1)
		{
			if (plugin.arenaMap.containsKey(arg[0])) 
			{
				
				for(WallsRound round : plugin.wallsRounds)
   				{
   					if(round.getArena().getName().equals(arg[0]))
   					{
   						round.addPlayer(player.getName(), new Random());
   						
   						return true;
   					}
   				}
				
				//If NO Round for this Arena exists
				
				int pos = plugin.wallsRounds.size();
				plugin.wallsRounds.add(pos, new WallsRound(plugin.arenen.get(plugin.arenaMap.get(arg[0])), plugin));
				plugin.wallsRounds.get(pos).addPlayer(player.getName(), new Random());
				
				return true;
			}
			else
			{
				player.sendMessage(ChatColor.RED + "There's no Walls-Arena with this name, try another !");
				return false;
			}
				
				
			}
			else if(arg.length == 2)
			{
				if (plugin.arenaMap.containsKey(arg[0])) 
				{
				
					int group = 0;
					try{
						group = Integer.parseInt(arg[1]);
						
					}catch(NumberFormatException ex){
						player.sendMessage(ChatColor.RED + "This is not a valid group Number !");
						return false;
					}
					
				for(WallsRound round : plugin.wallsRounds)
   				{
   					if(round.getArena().getName().equals(arg[0]))
   					{
   						round.addPlayer(player.getName(), group);
   						
   						/*
   						String path = ("spawns." + arg[0].toLowerCase()
								+ ".g" + (String.valueOf(group)));
   						player.teleport(plugin.configManager.getLocation(path));
   						*/
   						
   						return true;
   					}
   				}
				
				//If NO Round for this Arena exists
				
				int pos = plugin.wallsRounds.size();
				plugin.wallsRounds.add(pos, new WallsRound(plugin.arenen.get(plugin.arenaMap.get(arg[0])), plugin));
				plugin.wallsRounds.get(pos).addPlayer(player.getName(), group);
				
				return true;
			}
				else
			{
				player.sendMessage(ChatColor.RED + "There's no Walls-Arena with this name, try another !");
				return false;
			}
		
		}
		
		return false;
		
	}

}

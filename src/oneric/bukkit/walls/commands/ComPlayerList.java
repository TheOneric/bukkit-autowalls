package oneric.bukkit.walls.commands;

import oneric.bukkit.walls.src.WallsPlugin;
import oneric.bukkit.walls.src.WallsRound;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComPlayerList extends WallsCommand {

	public ComPlayerList(WallsPlugin plugin) {
		super(plugin, 0, 1, true);
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg) {
		
		Player player = (Player) sender;
		
		if(arg.length == 0)
		{
			int[] playerIndex = this.plugin.getWallsPlayer(player.getName());
			
			if(playerIndex == null)
			{
				sender.sendMessage(ChatColor.RED + "You aren't in an Walls Game !");
				return false;
			}
				
			for(int i = 0; i < this.plugin.wallsRounds.get(playerIndex[0]).Groups.size(); i++)
			{
				ChatColor colour = (i == playerIndex[1] ? ChatColor.GREEN : ChatColor.BLUE);
				StringBuilder players = new StringBuilder();
				for(String p : this.plugin.wallsRounds.get(playerIndex[0]).Groups.get(i))
				{
					players.append(colour + p);
				}
				sender.sendMessage("Group " + (i+1) + ": " + players.toString());
			}
			return true;
				
		}
		else if(arg.length == 1)
		{
				
			for(WallsRound round : this.plugin.wallsRounds)
			{
				if(round.getArena().arenaName.equals(arg[0]))
				{
					for(int i = 0; i < round.Groups.size(); i++)
		   			{
		   				StringBuilder players = new StringBuilder();
		   				for(String p : round.Groups.get(i))
		   				{
		   					players.append(ChatColor.BLUE + p);
		   				}
		   				sender.sendMessage("Group " + (i+1) + ": " + players.toString());
		   			}
					return true;
				}
			}
				
			sender.sendMessage(ChatColor.BLUE + "At the moment this arena is not in use. Thus there are no players inside.");
				
				
		}
		
		return false;
	}

}

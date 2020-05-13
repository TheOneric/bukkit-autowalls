package oneric.bukkit.walls.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oneric.bukkit.walls.src.WallsPlugin;
import oneric.bukkit.walls.src.WallsRound;

public class ComLeave extends WallsCommand{

	public ComLeave(WallsPlugin plugin) {
		super(plugin, 0, true, WallsPlugin.PERMISSION_PLAY);
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg)
	{
		
		Player player = (Player) sender;
		
		
		int[] indexes = plugin.getWallsPlayer( player.getName() );
		if(indexes != null)
		{
			if(plugin.wallsRounds.get(indexes[0]).state == WallsRound.STATE.COUNTDOWN)
			{
				player.sendMessage(ChatColor.RED + "You can't leave while a Countdown ! Wait till the Countdown is finished and the reenter the command");
				return false;
			}
				
			plugin.wallsRounds.get(indexes[0]).Groups.get(indexes[1]).remove(indexes[2]);
			plugin.wallsRounds.get(indexes[0]).call(
					ChatColor.GOLD + player.getName()
					+ ChatColor.AQUA + " has leaved the Arena.");
				
				
			plugin.wallsRounds.get(indexes[0]).doLeavingStuff(player.getName(), plugin, true, true);
				
			plugin.wallsRounds.get(indexes[0]).checkForWinners();
				
			return true;
		}
		else
		{
			player.sendMessage("You're not in a Game !");
			return false;
		}
		
	}

}

package oneric.bukkit.walls.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oneric.bukkit.walls.src.WallsPlugin;

public class ComCreateSign extends WallsCommand{

	public ComCreateSign(WallsPlugin plugin) {
		super(plugin, 1, true, WallsPlugin.PERMISSION_MANIPULATE);
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg)
	{	
		if(!this.plugin.arenaMap.containsKey(arg[0]))
		{
			sender.sendMessage(ChatColor.RED + "There's no arena with this name !");
			return false;
		}
		
		this.plugin.signCreator.put(((Player)sender).getName(), arg[0]);
		sender.sendMessage(ChatColor.BLUE + "Right Click the Sign you want to be associated with the Arena " + arg[0]);
		
		return true;
		
	}

}

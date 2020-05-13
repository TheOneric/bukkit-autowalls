package oneric.bukkit.walls.commands;

import oneric.bukkit.walls.src.WallsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class WallsCommand implements CommandExecutor {

	
	protected WallsPlugin plugin;
	
	protected final int minArgs;
	protected final int maxArgs;
	protected final boolean needsPlayer;
	protected final String permission;
	
	public WallsCommand(WallsPlugin plugin, int minArg, int maxArg, boolean player, String permission)
	{
		this.plugin = plugin;
		this.minArgs = minArg;
		this.maxArgs = maxArg;
		this.needsPlayer = player;
		this.permission = permission;
	}
	
	public WallsCommand(WallsPlugin plugin, int minArg, int maxArg, boolean player)
	{
		this(plugin, minArg, maxArg, player, WallsPlugin.PERMISSION_PLAY);
	}
	
	public WallsCommand(WallsPlugin plugin, int args, boolean player)
	{
		this(plugin, args, args, player, WallsPlugin.PERMISSION_PLAY);
	}
	
	public WallsCommand(WallsPlugin plugin, int args, boolean player, String permission)
	{
		this(plugin, args, args, player, permission);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command com, String comLabel, String[] arg) 
	{		
		
		if(!sender.hasPermission(permission))
		{
			sender.sendMessage(ChatColor.RED + "Sorry, you do not have the required permission (' " + permission + " ') to use this command. If you suspect this to be an error please contact the responsible administrator.");
			return false;
		}
		
		
		if(this.needsPlayer)
		{
			if(!(sender instanceof Player)){
				sender.sendMessage(ChatColor.RED + "This command can only be used by a Player !");
				return false;
			}
		}
		
		
		
		if(arg == null)
		{
			if(this.minArgs != 0)
			{
				sender.sendMessage(ChatColor.RED + "Too few arguements !");
				return false;
			}
		}
		else if(arg.length < minArgs)
		{
			sender.sendMessage(ChatColor.RED + "Too few arguements !");
			return false;
		}
		else if(arg.length > maxArgs)
		{
			sender.sendMessage(ChatColor.RED + "Too many arguements !");
			return false;
		}
		
		
		return this.onSafeCommand(sender, com, comLabel, arg);
	}
	
	public abstract boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg);

}

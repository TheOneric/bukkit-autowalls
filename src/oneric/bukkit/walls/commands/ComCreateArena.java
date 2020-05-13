package oneric.bukkit.walls.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import oneric.bukkit.walls.enums.EnumArenaManagementType;
import oneric.bukkit.walls.enums.EnumRecreateType;
import oneric.bukkit.walls.src.WallsArena;
import oneric.bukkit.walls.src.WallsPlugin;
import oneric.bukkit.walls.utils.ArenaLoader;

public class ComCreateArena extends WallsCommand {

	public ComCreateArena(WallsPlugin plugin) {
		super(plugin, 5, true, WallsPlugin.PERMISSION_MANIPULATE);
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg) {
		
		Player player = (Player) sender;
		
		// Check if this world has alredy an Arena
		if (plugin.isArenaWorld(player.getWorld().getName())) 
		{
			player.sendMessage(ChatColor.RED + "There's already an Arena in this world ! Please chose (--> go to) another world or create a new one.");
			return false;
		}

		try {

			WallsArena arena = new WallsArena(arg[0], Integer.parseInt(arg[4]),
					Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), player
							.getWorld().getName());
			arena.setRecreateWay(EnumRecreateType.TELEPORT);

			try {
				arena.setManagementType(EnumArenaManagementType.valueOf(arg[3]));
			} catch (IllegalStateException | NullPointerException | IllegalArgumentException ex) 
			{
				player.sendMessage(ChatColor.RED + "Wrong Management-Type");
				return false;
			}

			if (!(plugin.putInMapAndList(arg[0], arena))) {
				player.sendMessage(ChatColor.RED + "An Arena with this name already Exists !");
				return false;
			}

			try {
				ArenaLoader.saveArena(arg[0], arena);
			} catch (IOException ex) {
				player.sendMessage(ChatColor.RED + "Failed to save the Arena File !");
				return false;
			}

			player.sendMessage(ChatColor.GREEN + "Arena: " + ChatColor.GOLD
					+ arg[0] + ChatColor.GREEN + " succesfully created !");

			return true;

		} catch (NumberFormatException ex) {
			player.sendMessage(ChatColor.RED + "Given arguements wasn't Integers");
			return false;
		} catch (IOException e) {
			player.sendMessage(ChatColor.RED + "Index File seems to be damaged, can't register the Arena !");
			plugin.getLogger().warning("!! Index File is probably damaged !!");
			return false;
		}
		
	}

}

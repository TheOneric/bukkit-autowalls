package oneric.bukkit.walls.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import oneric.bukkit.walls.src.WallsPlugin;

public class ComDeleteArena extends WallsCommand{

	public ComDeleteArena(WallsPlugin plugin) {
		super(plugin, 1, false, WallsPlugin.PERMISSION_MANIPULATE);
	}
//TODO Remove SIGNS
	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg) 
	{
		
		if(!this.plugin.arenaMap.containsKey(arg[0]))
		{
			sender.sendMessage(ChatColor.RED + "There is no Arena called ' " + arg[0] + " '. Accordingly you can't delte it.");
			return false;
		}
		
		try{
				File tmpFile = new File(plugin.getDataFolder() + "/" + "arenas" + "/" + "index.indexTMP");
				File orgFile = new File(plugin.getDataFolder() + "/" + "arenas" + "/" + "index.index");
				File arenaFile = new File(plugin.getDataFolder() + "/" + "arenas" + "/" + arg[0] + ".arena");
				
				BufferedReader reader = new BufferedReader(new FileReader(orgFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile));
				
				String currentLine;

				while((currentLine = reader.readLine()) != null) {
				    // trim newline when comparing with lineToRemove
				    String trimmedLine = currentLine.trim();
				    if(trimmedLine.equals(arg[0])) continue;
				    writer.write(currentLine);
				    writer.newLine();
				}
				
				writer.flush();
				writer.close();
				reader.close();
				
				orgFile.delete();
				arenaFile.delete();
				
				this.plugin.arenen.get( this.plugin.arenaMap.get(arg[0]) ).removeAllSign();
				
				boolean successful = tmpFile.renameTo(orgFile);
				if(successful)
				{
					sender.sendMessage(ChatColor.GREEN + "Arena deleted ! But you must reload the Arenas "+ ChatColor.AQUA + "(/wallsReloadArenas)" + ChatColor.GREEN + " to get the Change working !");
					return true;
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "Failed to delete Arena !");
					return false;
				}
				
				
			}catch(IOException ex){
				sender.sendMessage(ChatColor.RED + "Operation failed, check if the arena directory and the Index file is damaged and try it again !");
				return false;
			}
		
	}

}

package oneric.bukkit.walls.commands;

import java.io.IOException;

import oneric.bukkit.walls.enums.EnumArenaManagementType;
import oneric.bukkit.walls.enums.EnumRecreateType;
import oneric.bukkit.walls.src.WallsPlugin;
import oneric.bukkit.walls.utils.ArenaLoader;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ComEditArena extends WallsCommand {

	public ComEditArena(WallsPlugin plugin) {
		super(plugin, 3, false, WallsPlugin.PERMISSION_MANIPULATE);
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg) 
	{
		
		//Check if Arena exists
			if(!this.plugin.arenaMap.containsKey(arg[0]))
			{
				sender.sendMessage(ChatColor.RED + "There's no Arena with this name !");
				return false;
			}
			
			//Check if it's an valid Arguement Name
			int arguementIndex = 0;
			for(int i = 0; i < ArenaLoader.validEditArguements.length; i++)
			{
				if(arg[1].equals(ArenaLoader.validEditArguements[i]))
				{
					arguementIndex = i;
					break;
				}
				else if(i == (ArenaLoader.validEditArguements.length-1))
				{
					sender.sendMessage(ChatColor.RED + arg[1] + " is not a valid Arguement !");
					sender.sendMessage(ChatColor.AQUA + "Valid Arguements are:");
					for(String s : ArenaLoader.validEditArguements)
					{
						sender.sendMessage(ChatColor.AQUA + s);
					}
					return false;
				}
			}
			
			//Check if the Type is a correct one and Edit the Arena Settings
			
			//Wenn es ein Integer ist
			if(arguementIndex <= 2 && arguementIndex >= 0)
			{
				try{
					int arguement = Integer.parseInt(arg[2]);
					
					
					if(arg[1].equals("xWide"))
					{
						this.plugin.arenen.get( this.plugin.arenaMap.get(arg[0]) ).setXWide(arguement);
					}
					else if(arg[1].equals("zWide"))
					{
						this.plugin.arenen.get( this.plugin.arenaMap.get(arg[0]) ).setZWide(arguement);
					}
					else if(arg[1].equals("groupCount"))
					{
						this.plugin.arenen.get( this.plugin.arenaMap.get(arg[0]) ).setAnzahlGroups(arguement);
					}
					
					ArenaLoader.saveArena(arg[0], this.plugin.arenen.get( this.plugin.arenaMap.get(arg[0]) ));
					sender.sendMessage(ChatColor.GOLD + "Successfully saved your edits !");
					
					return true;
					
					
				}
				catch(NumberFormatException ex)
				{
					sender.sendMessage(ChatColor.RED + "This setting requiers an Integer. Please enter an Integer !");
					return false;
				} catch (IOException e) {
				sender.sendMessage(ChatColor.RED + "Couldn't save your Edits for some reason! All Edits will be lost on Reload / Restart !");
					return true;
			}
				
			}
			
			//Wenn es ein Enum ist 
			else if(arguementIndex == 3 || arguementIndex == 4)
			{
				try{
					
					
					
					if(arg[1].equals("mangementType"))
					{
						this.plugin.arenen.get( this.plugin.arenaMap.get(arg[0]) ).setManagementType( EnumArenaManagementType.valueOf(arg[2]) );
					}
					else if(arg[1].equals("recreateWay"))
					{
						this.plugin.arenen.get( this.plugin.arenaMap.get(arg[0]) ).setRecreateWay( EnumRecreateType.valueOf(arg[2]) );
					}
					
					
					ArenaLoader.saveArena(arg[0], this.plugin.arenen.get( this.plugin.arenaMap.get(arg[0]) ));
					
					sender.sendMessage(ChatColor.GOLD + "Successfully saved your edits !");
					
					return true;
					
					
				}
				catch(IllegalStateException | NullPointerException | IllegalArgumentException ex)
				{
					sender.sendMessage(ChatColor.RED + "Not the right EnumName !");
					sender.sendMessage(ChatColor.AQUA + "Valid Name are :");
					
					if(arg[1].equals("mangementType"))
					{
						for(EnumArenaManagementType e : EnumArenaManagementType.values())
						{
							sender.sendMessage(ChatColor.AQUA + e.name());
						}
					}
					
					else if(arg[1].equals("recreateWay"))
					{
						for(EnumRecreateType e : EnumRecreateType.values())
						{
							sender.sendMessage(ChatColor.AQUA + e.name());
						}	
					}
					
					return false;
				} catch (IOException e) {
				sender.sendMessage(ChatColor.RED + "Couldn't save your Edits for some reason! All Edits will be lost on Reload / Restart !");
				return true;
			}
			}
		return false;
	}

}

package oneric.bukkit.walls.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import oneric.bukkit.walls.enums.EnumArenaManagementType;
import oneric.bukkit.walls.enums.EnumRecreateType;
import oneric.bukkit.walls.src.WallsArena;
import oneric.bukkit.walls.src.WallsPlugin;

public class ArenaLoader {
	
	/**Index 0-2 takes Integers, Index 3-4 Enums*/
	public static String[] validEditArguements = {"xWide", "zWide", "groupCount", "mangementType", "recreateWay"};
	
	/** 
	 * PROBABLY Thread Safe
	 * */
	public static void saveArena(String path, WallsArena arena) throws FileNotFoundException, IOException
	{
		
			//FileWriter w = new FileWriter(WallsPlugin.me.getDataFolder() + File.separator + "WallsPlugin" + File.separator + "arenas" + File.separator + path + ".arena");
			FileWriter w = new FileWriter(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + path + ".arena");
			BufferedWriter writer = new BufferedWriter(w);
			
			writer.write("# WALLS ARENA FILE, FOR ARENA : " + arena.getName()); writer.newLine();
			writer.write("# DESIGNED FOR THE WALLS - PLUGIN BY ONERIC"); writer.newLine();
			writer.write("name:" + arena.getName()); writer.newLine();
			writer.write("xWide:" + arena.xWide); writer.newLine();
			writer.write("zWide:" + arena.yWide); writer.newLine();
			writer.write("groupCount:" + arena.AnzahlGruppen); writer.newLine();
			writer.write("world:" + arena.currentWorld); writer.newLine();
			writer.write("mangementType:" + arena.arenaManagementType.name()); writer.newLine();
			writer.write("recreateWay:" + arena.arenaRegeneratingWay.name()); writer.newLine();
			
			String signs = "";
			for(Location loc : arena.getSigns())
			{
				signs += loc.getWorld().getName() + "|" 
						+ loc.getBlockX() + "|" 
						+ loc.getBlockY() + "|" 
						+ loc.getBlockZ() + ":";
			}
			
			if(signs.endsWith(":"))
			{
				signs = signs.substring(0, signs.length() - 1);
			}
			
			writer.write("signs:" + signs); writer.newLine();
			
			writer.flush();
			writer.close();
			
	}
	
	/** 
	 * NOT Thread Safe !!!!
	 * */
	public static WallsArena loadArena(String name) throws FileNotFoundException, IOException, NumberFormatException
	{
		//FileReader r = new FileReader(WallsPlugin.me.getDataFolder() + File.separator + "WallsPlugin" + File.separator + "arenas" + File.separator + path + ".arena");
		FileReader r = new FileReader(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + name + ".arena");
		BufferedReader reader = new BufferedReader(r);
		
		WallsArena arena = new WallsArena();
		
		
		String line;
		while((line = reader.readLine()) != null)
		{
			if(line.startsWith("#"))
			{
				//NOTHING
			}
			else if(line.startsWith("name:"))
			{
				arena.setName(line.split(":")[1]);
			}
			else if(line.startsWith("xWide:"))
			{
				arena.setXWide(Integer.parseInt(line.split(":")[1]));
			}
			else if(line.startsWith("zWide:"))
			{
				arena.setZWide(Integer.parseInt(line.split(":")[1]));
			}
			else if(line.startsWith("groupCount:"))
			{
				arena.setAnzahlGroups(Integer.parseInt(line.split(":")[1]));
			}
			else if(line.startsWith("world:"))
			{
				arena.setWorld(line.split(":")[1]);
			}
			else if(line.startsWith("mangementType:"))
			{
				if(line.split(":")[1].equalsIgnoreCase(EnumArenaManagementType.BASE.name()))
				{
					arena.setManagementType(EnumArenaManagementType.BASE);
				}
				else if(line.split(":")[1].equalsIgnoreCase(EnumArenaManagementType.ADVANCED.name()))
				{
					arena.setManagementType(EnumArenaManagementType.ADVANCED);
				}
			}
			else if(line.startsWith("recreateWay:"))
			{
				try{
					arena.setRecreateWay(EnumRecreateType.valueOf(line.split(":")[1]));
				}catch(IllegalArgumentException ex) {
					WallsPlugin.me.getLogger().warning("Failed to load Recreate Way of Arena : " + name + " - Set the type to TELEPORT !");
					arena.setRecreateWay(EnumRecreateType.TELEPORT);
				}catch(NullPointerException ex){
					WallsPlugin.me.getLogger().warning("Failed to load Recreate Way of Arena : " + name + " - Set the type to TELEPORT !");
					arena.setRecreateWay(EnumRecreateType.TELEPORT);
				}
			}
			else if(line.startsWith("signs:"))
			{
				String[] splitLine = line.split(":");
				for(int i = 1; i < splitLine.length; i++)
				{
					String[] splitLoc = splitLine[i].split(Pattern.quote("|"));
					
					/*System.out.println("----------------  SignLocation !!!   ----------------");
					for(String s : splitLoc)
						System.out.println(s);*/
					
					String world = splitLoc[0];
					int x = -1;
					int y = -1;
					int z = -1;
					try{
						x = Integer.parseInt(splitLoc[1]);
						y = Integer.parseInt(splitLoc[2]);
						z = Integer.parseInt(splitLoc[3]);
					} catch(NumberFormatException ex){
						System.out.println("Damaged Arena File !!");
						ex.printStackTrace();
						continue;
					}
					
					arena.addSigns(new Location(Bukkit.getWorld(world), x, y, z));
				}
			}
		}
		
		reader.close();
		
		return arena;
		
	}

}

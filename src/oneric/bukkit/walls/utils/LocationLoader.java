package oneric.bukkit.walls.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import oneric.bukkit.walls.src.WallsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class LocationLoader {
	
	
	
	
	/**
	 * Saves an Spawn-Location
	 * */
	public static void saveLocation(String arena, String group, Location loc) throws IOException
	{
		File directory;
		if(!((directory = new File(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + "spawns" + "/" + arena)).exists()))
		{
			directory.mkdirs();
		}
		
		//System.out.println(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + "spawns" + "/" + arena + "/" + group + ".location");
		
		FileWriter w = new FileWriter(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + "spawns" + "/" + arena + "/" + group + ".location");
		BufferedWriter writer = new BufferedWriter(w);
		
		writer.write("# SPAWN LOCATION FILE, FOR ARENA : " + arena + " ; AND THE GROUP : " + group); writer.newLine();
		writer.write("# DESIGNED FOR THE WALLS - PLUGIN BY ONERIC"); writer.newLine();
		writer.write("world:" + loc.getWorld().getName()); writer.newLine();
		writer.write("x:" + loc.getX()); writer.newLine();
		writer.write("y:" + loc.getY()); writer.newLine();
		writer.write("z:" + loc.getZ()); writer.newLine();
		writer.write("yaw:" + loc.getYaw()); writer.newLine();
		writer.write("pitch:" + loc.getPitch()); writer.newLine();
		
		writer.flush();
		writer.close();
	}
	
	
	/**
	 * Reads an Location from a file
	 * */
	public static Location loadLocation(String arena, String group) throws IOException, NumberFormatException, IllegalArgumentException
	{
		
		//if(!((new File(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + "spawns" + "/" + arena + "/" + group + ".location")).exists()))
		//{
		//	return null;
		//}
		
		//System.out.println(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + "spawns" + "/" + arena + "/" + group + ".location");
		
		double x = 0,y = 0,z = 0;
		float yaw = 0,pitch = 0;
		String world = null;
		
		FileReader r = new FileReader(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + "spawns" + "/" + arena + "/" + group + ".location");
		BufferedReader reader = new BufferedReader(r);
		
		int loadCount = 0;
		
		String line;
		while((line = reader.readLine()) != null)
		{
			if(line.startsWith("#"))
			{
				//NOTHING
			}
			else if(line.startsWith("world:"))
			{
				world = line.split(":")[1];
				loadCount++;
			}
			else if(line.startsWith("x:"))
			{
				x = Double.parseDouble(line.split(":")[1]);
				loadCount++;
			}
			else if(line.startsWith("y:"))
			{
				y = Double.parseDouble(line.split(":")[1]);
				loadCount++;
			}
			else if(line.startsWith("z:"))
			{
				z = Double.parseDouble(line.split(":")[1]);
				loadCount++;
			}
			else if(line.startsWith("yaw:"))
			{
				yaw = Float.parseFloat(line.split(":")[1]);
				loadCount++;
			}
			else if(line.startsWith("pitch:"))
			{
				pitch = Float.parseFloat(line.split(":")[1]);
				loadCount++;
			}
		}
		
		
		if(loadCount < 6)
		{
			WallsPlugin.me.getLogger().warning(ChatColor.RED + "Location file is damaged ! : Arena " + arena + " ; group :" + group);
		}
		
		reader.close();
		
		return new Location(WallsPlugin.me.getServer().getWorld(world), x, y, z, yaw, pitch);
	}
	
	public static boolean alreadyExists(String arena, String group)
	{
		if(((new File(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + "spawns" + "/" + arena + "/" + group + ".location")).exists()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean delete(String arena, String group)
	{
		File f = (new File(WallsPlugin.me.getDataFolder() + "/" + "arenas" + "/" + "spawns" + "/" + arena + "/" + group + ".location"));
		if((f.exists()))
		{
			return f.delete();
		}
		else
		{
			return false;
		}
	}
	
	

}

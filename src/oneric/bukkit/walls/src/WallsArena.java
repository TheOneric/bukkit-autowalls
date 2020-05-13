package oneric.bukkit.walls.src;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import oneric.bukkit.walls.enums.EnumArenaManagementType;
import oneric.bukkit.walls.enums.EnumRecreateType;

public class WallsArena{
	
	public int xWide, yWide;
	public int AnzahlGruppen;
	
	public final int[] defaultPosition = {999, 999};
	
	//public ArrayList<Location> SpawnPunkte;
	
	public String arenaName;
	
	public Enum<EnumArenaManagementType> arenaManagementType;
	public Enum<EnumRecreateType> arenaRegeneratingWay;
	
	private List<Location> signs = null;
	
	public String currentWorld; 
	
	public WallsArena(String name, int Gruppen, int xW, int yW, String world)
	{
		this.xWide = xW;
		this.yWide = yW;
		this.AnzahlGruppen = Gruppen;
		this.currentWorld = world;
		this.arenaName = name;
		this.signs = new ArrayList<Location>();
	}
	
	/**
	 * Only used by the File Loader
	 * */
	public WallsArena()
	{
		this.signs = new ArrayList<Location>();
	}
	
	
	/**
	 * Loads all aviable Spawns
	 * */
	/*
	private void loadSpawns()
	{
		String pathInConfig = "spawns." + this.getName().toLowerCase() + ".";
		
		for(int i = 1; i <= this.AnzahlGruppen; i++)
		{
			try{
				this.SpawnPunkte.add(i, (Location) WallsPlugin.me.getConfig().get(pathInConfig));
			}catch(Exception ex)
			{
				
			}
		}
	}*/
	
	
	
	
	
	/**
	 * Set the Management Type.
	 * */
	public void setManagementType(Enum<EnumArenaManagementType> aMT)
	{
		arenaManagementType = aMT;
	}
	
	/**
	 * Set the Recreate Way.
	 * */
	public void setRecreateWay(Enum<EnumRecreateType> rW)
	{
		arenaRegeneratingWay = rW;
	}
	
	public void setXWide(int xw)
	{
		this.xWide = xw;
	}
	
	public void setZWide(int zw)
	{
		this.yWide = zw;
	}
	
	public void setAnzahlGroups(int c)
	{
		this.AnzahlGruppen = c;
	}
	
	public void setWorld(String w)
	{
		this.currentWorld = w;
	}
	
	public void setName(String n)
	{
		this.arenaName = n;
	}
	
	public void setSigns(Location... locations)
	{
		this.signs = new ArrayList<Location>();
		for(Location loc : locations)
		{
			if(loc.getWorld().getBlockAt(loc).getType() == Material.SIGN_POST || loc.getWorld().getBlockAt(loc).getType() == Material.WALL_SIGN)
				this.signs.add(loc);
			else
				WallsPlugin.me.getLogger().warning("Sign at" + loc.getX() +" "+ loc.getY() + " " + loc.getZ() + " disappeared. Removing it !" );
		}
	}
	
	/** 
	 * Adds the given Locations to the list, if there's a sign
	 * */
	public void addSigns(Location... locations)
	{
		for(Location loc : locations)
		{
			if(loc.getWorld().getBlockAt(loc).getType() == Material.SIGN_POST || loc.getWorld().getBlockAt(loc).getType() == Material.WALL_SIGN)
				this.signs.add(loc);
			else
				WallsPlugin.me.getLogger().warning("Sign at" + loc.getX() +" "+ loc.getY() + " " + loc.getZ() + " disappeared. Removing it !" );
		}
			
	}
	
	
	/*
	 * 
	 * GETTER
	 * 
	 * */
	
	public String getName()
	{
		return this.arenaName;
	}
	
	/**
	 * Get the Count of Groups in this area.
	 * */
	public int getGroupCount()
	{
		return this.AnzahlGruppen;
	}
	
	public int getXWide()
	{
		return xWide;
	}
	
	public int getZWide()
	{
		return yWide;
	}
	
	public String getWorldName()
	{
		return this.currentWorld;
	}
	
	
	/**
	 * Returns the Management type as Enum.
	 * */
	public Enum<EnumArenaManagementType> getManagemnetType()
	{
		return this.arenaManagementType;
	}
	
	public Enum<EnumRecreateType> getRecreateWay()
	{
		return this.arenaRegeneratingWay;
	}
	
	/** 
	 * Returns the locations of the signs
	 * */
	public List<Location> getSigns()
	{
		return signs;
	}
	
	/** 
	 * Remove the specific Signs
	 * */
	public void removeSign(Location loc)
	{
		this.signs.remove(loc);
		if(loc.getWorld().getBlockAt(loc).getType() == Material.SIGN_POST || loc.getWorld().getBlockAt(loc).getType() == Material.WALL_SIGN)
		{	
			Sign sign = (Sign) loc.getBlock().getState();
			sign.setLine(0, ChatColor.DARK_RED + "REMOVED");
			sign.update();
		}	
	}
	
	/** 
	 * Remove the specific Signs
	 * */
	public void removeAllSign()
	{
		for(Location loc : signs)
		{
			if(loc.getBlock().getType() == Material.SIGN_POST || loc.getBlock().getType() == Material.WALL_SIGN)
			{
				Sign sign = (Sign) loc.getBlock().getState();
				sign.setLine(0, ChatColor.DARK_RED + "REMOVED");
				sign.update();
			}
		}
		
		signs.clear();
	}

}

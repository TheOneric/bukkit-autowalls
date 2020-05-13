package oneric.bukkit.walls.generators;

import java.util.Random;

import oneric.bukkit.walls.worldGen.Block;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class PlainChunkGenerator extends ChunkGenerator {
	
	
	public byte[] generate(World world, Random random, int cx, int cz)
	{
	    byte[] data = new byte[32768];
		
	    if(cx == 0 && cz == 0)
	    {
	    	data[0 << 11 | 0 << 7 | 64] = (byte)Block.stone;
	    	data[0 << 11 | 0 << 7 | 63] = (byte)Block.stone;
	    	data[0 << 11 | 0 << 7 | 62] = (byte)Block.stone;
	    }
	    
	    else if(cx == 62 && cz == 62) //999 and 996
	    {
	    	//999
	    	data[7 << 11 | 7 << 7 | 64] = (byte)Block.slowSand;
	    	data[7 << 11 | 7 << 7 | 63] = (byte)Block.slowSand;
	    	data[7 << 11 | 7 << 7 | 62] = (byte)Block.slowSand;
	    	
	    	//Ecksteine zur Orientierung
	    	data[7 << 11 | 8 << 7 | 64] = (byte)Block.slowSand;
	    	data[7 << 11 | 8 << 7 | 63] = (byte)Block.slowSand;
	    	data[7 << 11 | 8 << 7 | 62] = (byte)Block.slowSand;
	    	
	    	data[8 << 11 | 7 << 7 | 64] = (byte)Block.slowSand;
	    	data[8 << 11 | 7 << 7 | 63] = (byte)Block.slowSand;
	    	data[8 << 11 | 7 << 7 | 62] = (byte)Block.slowSand;
	    	
	    	
	    	
	    	
	    	//996
	    	data[4 << 11 | 4 << 7 | 64] = (byte)Block.planks;
	    	data[4 << 11 | 4 << 7 | 63] = (byte)Block.planks;
	    	data[4 << 11 | 4 << 7 | 62] = (byte)Block.planks;
	    }
	    
	    return data;
		
	  }

	
	
	
	
	
	  public Location getFixedSpawnLocation(World world, Random random)
	  {
	    return new Location(world, 999.5D, (world.getHighestBlockYAt(999, 999) + 0.5D), 999.5D);
	  }
	  
	 
	

}

package oneric.bukkit.walls.src;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import oneric.bukkit.walls.enums.EnumRecreateType;
import oneric.bukkit.walls.worldGen.Block;

public class ArenaManagementBase extends ArenaManagement{
	
	
	
	
	private int[] idsOriginal;
	private byte[] metasOriginal;
	
	private ArrayList<ItemStack[]> chests;
	
	
	
	public ArenaManagementBase(WallsArena wArena, WallsPlugin plugin)
	{
		super(wArena, plugin);
		
		idsOriginal = new int[arena.xWide * arena.yWide * this.currentWorld.getMaxHeight()];
		metasOriginal = new byte[arena.xWide * arena.yWide * this.currentWorld.getMaxHeight()];
		
		chests = new ArrayList<ItemStack[]>();
		
		/*
		this.copyOriginal(arena.xWide, arena.yWide);
		
		for(int i = 1; i <= arena.AnzahlGruppen; i++)
		{
			this.setOthers(this.pos.get(i)[0], this.pos.get(i)[1], arena.xWide, arena.yWide);
		}
		*/
		
		
		
		
		
	}
	
	
	
	
	
	@Override
	public void recreate()
	{
		this.copyOriginal(arena.xWide, arena.yWide);
		
		for(int i = 1; i <= arena.AnzahlGruppen; i++)
		{
			this.setOthers(this.pos.get(i)[0], this.pos.get(i)[1], arena.xWide, arena.yWide);
		}
		
		if(ConfigManager.refreshMiddle)
		{
			this.resetMiddle(arena.xWide, arena.yWide);
		}
		
		List<Entity> entList = this.currentWorld.getEntities();//get all entities in the world
		 
        for(int i = 0; i < entList.toArray().length; i++){//loop through the list
            if (entList.get(i) instanceof Item){//make sure we aren't deleting mobs/players
            	entList.get(i).remove();//remove it
            	
            }
        }
		
	}
	
	
	
	
	public void setOthers(int xDef, int yDef, int xWide, int yWide)
	{
		this.currentWorld.setGameRuleValue("doTileDrops", "false");
		int counter = 0;
		int chestCounter = 0;
		
		for(int x = xDef; x < xWide + xDef; x++)
		{
			for(int z = yDef; z < yWide + yDef; z++)
			{
				for(int y = 0; y < this.currentWorld.getMaxHeight(); y++)
				{
					
					this.currentWorld.getBlockAt(x, y, z).setTypeId(idsOriginal[counter]);
					this.currentWorld.getBlockAt(x, y, z).setData(metasOriginal[counter]);
					
					switch(idsOriginal[counter])
					{
					case Block.chest:
						BlockState var16 = this.currentWorld.getBlockAt(x, y, z).getState();
						((Chest) var16).getInventory().setContents(chests.get(chestCounter));
						chestCounter++;
						break;
					}
					
					counter++;
					
				}	
			}
		}
		
		this.currentWorld.setGameRuleValue("doTileDrops", "true");
		
		
		
		///////////
		
		
		
		
		
		
		
		
	}
	
	
	
	
	public void copyOriginal(int xWide, int yWide)
	{
		
		

		
		if(this.arena.getRecreateWay() != EnumRecreateType.TELEPORT)
		{
			for(int x = 0; x < 999+this.arena.xWide; x += 16)
			{
				for(int z = 0; z < 999+this.arena.xWide; z += 16)
				{
					this.currentWorld.loadChunk((999 + x)/16, (999 + z)/16);
				}
			}
		}
		
		
		
		
		int counter = 0;
		
		for(int x = arena.defaultPosition[0]; x < xWide + arena.defaultPosition[0]; x++)
		{
			for(int z = arena.defaultPosition[1]; z < yWide + arena.defaultPosition[1]; z++)
			{
				for(int y = 0; y < this.currentWorld.getMaxHeight(); y++)
				{
					
					idsOriginal[counter] = this.currentWorld.getBlockTypeIdAt(x, y, z);
					metasOriginal[counter] = this.currentWorld.getBlockAt(x, y, z).getData();
					
					switch(idsOriginal[counter])
					{
					case Block.chest:
						BlockState var16 = this.currentWorld.getBlockAt(x, y, z).getState();
						chests.add(((Chest)var16).getInventory().getContents());
						break;
					}
					
					
					counter++;
					
				}
			}
		}
		
	}
	
	
	
	
	
	
	
	

}

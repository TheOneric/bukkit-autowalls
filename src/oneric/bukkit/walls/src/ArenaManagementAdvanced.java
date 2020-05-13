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
import oneric.bukkit.walls.worldGen.WorldGenDungeon;
import oneric.bukkit.walls.worldGen.WorldGenMinable;

public class ArenaManagementAdvanced extends ArenaManagement{
	
	
	
	
	private int[] idsOriginal;
	private byte[] metasOriginal;
	
	private int[] idsFirst;
	private byte[] metasFirst;
	
	private ArrayList<ItemStack[]> chests;
	
	
	
	public ArenaManagementAdvanced(WallsArena wArena, WallsPlugin plugin)
	{
	
		super(wArena, plugin);
		
		idsOriginal = new int[arena.xWide * arena.yWide * this.currentWorld.getMaxHeight()];
		metasOriginal = new byte[arena.xWide * arena.yWide * this.currentWorld.getMaxHeight()];
		
		idsFirst = new int[arena.xWide * arena.yWide * this.currentWorld.getMaxHeight()];
		metasFirst = new byte[arena.xWide * arena.yWide * this.currentWorld.getMaxHeight()];
		
		chests = new ArrayList<ItemStack[]>();
		
		
	}
	
	
	
	public void recreate()
	{
		this.copyOriginal(arena.xWide, arena.yWide);
		
		this.setFirst(this.pos.get(1)[0], this.pos.get(1)[1], arena.xWide, arena.yWide);
		
		this.copyFirst(arena.xWide, arena.yWide);
		
		for(int i = 2; i <= arena.AnzahlGruppen; i++)
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
	
	
	
	
	public void setFirst(int xDef, int yDef, int xWide, int yWide)
	{
		
		int counter = 0;
		
		for(int x = xDef; x < xWide + xDef; x++)
		{
			for(int z = yDef; z < yWide + yDef; z++)
			{
				for(int y = 0; y < this.currentWorld.getMaxHeight(); y++)
				{
					
					this.currentWorld.getBlockAt(x, y, z).setTypeId(idsOriginal[counter]);
					this.currentWorld.getBlockAt(x, y, z).setData(metasOriginal[counter]);
					counter++;
					
				}	
			}
		}
		
		
		int tmp1 = (int)((double)((double)xWide / 16D) + 0.5D);
		int tmp2 = (int)((double)((double)yWide / 16D) + 0.5D);
		
		int tmp = tmp1 * tmp2;
		plugin.getLogger().info("There a ca. " + tmp + " chunks with : " + tmp1 + " and " + tmp2);
		
		for(int i = 0; i <= tmp; i++)
		{
			if(ConfigManager.regenerateDungeons)
			{
				this.generateDungeons(xDef, yDef, xWide, yWide);
			}
			
			if(ConfigManager.regenerateOres)
			{
				this.generateOres(xDef, yDef, xWide, yWide);
			}
			
			 
		}
		
		
		
		
	}
	
	public void copyFirst(int xWide, int yWide)
	{
		
		int counter = 0;
		
		for(int x = this.pos.get(1)[0]; x < xWide + this.pos.get(1)[0]; x++)
		{
			for(int z = this.pos.get(1)[1]; z < yWide + this.pos.get(1)[1]; z++)
			{
				for(int y = 0; y < this.currentWorld.getMaxHeight(); y++)
				{
					
					idsFirst[counter] = this.currentWorld.getBlockTypeIdAt(x, y, z);
					metasFirst[counter] = this.currentWorld.getBlockAt(x, y, z).getData();					
					
					
					switch(idsFirst[counter])
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
	
	public void setOthers(int xDef, int yDef, int xWide, int yWide)
	{
		
		int counter = 0;
		int chestCounter = 0;
		
		for(int x = xDef; x < xWide + xDef; x++)
		{
			for(int z = yDef; z < yWide + yDef; z++)
			{
				for(int y = 0; y < this.currentWorld.getMaxHeight(); y++)
				{
					
					this.currentWorld.getBlockAt(x, y, z).setTypeId(idsFirst[counter]);
					this.currentWorld.getBlockAt(x, y, z).setData(metasFirst[counter]);
					
					switch(idsFirst[counter])
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
					counter++;
					
				}
			}
		}
		
	}
	
	private void generateDungeons(int xDef, int yDef, int xWide, int yWide)
	{
		for(int i2 = 0; i2 < ConfigManager.dungeon1Rarity; i2++)
	     {
			new WorldGenDungeon(this.currentWorld, xDef+random.nextInt(xWide), random.nextInt(this.currentWorld.getMaxHeight()), yDef+random.nextInt(yWide), 3+random.nextInt(3) ,5+random.nextInt(3));
	     }
		
		 
		for(int i2 = 0; i2 < ConfigManager.dungeon2Rarity; i2++)
		{
			new WorldGenDungeon(0, 1, 2, 3, this.currentWorld, xDef+random.nextInt(xWide), random.nextInt(this.currentWorld.getMaxHeight()), yDef+random.nextInt(yWide), 3+random.nextInt(3) ,5+random.nextInt(3), Block.brickStone);
		}
	}
	
	private void generateOres(int xDef, int yDef, int xWide, int yWide)
	{
		int hoehenVersatz = 0;
		
		for(int i3 = 0; i3 < ConfigManager.oreCoalRarity; i3++)
        {
            int j6 = xDef+random.nextInt(xWide);
            int k9 = random.nextInt(128);
            int i14 = yDef+random.nextInt(yWide);
            (new WorldGenMinable(Block.oreCoal, 16)).generate(this.currentWorld, random, j6, k9, i14);
        }

        for(int j3 = 0; j3 < ConfigManager.oreIronRarity; j3++)
        {
            int k6 = xDef+random.nextInt(xWide);
            int l9 = random.nextInt(64) + hoehenVersatz;
            int j14 = yDef+random.nextInt(yWide);
            (new WorldGenMinable(Block.oreIron, 8)).generate(this.currentWorld, random, k6, l9, j14);
        }

        for(int k3 = 0; k3 < ConfigManager.oreGoldRarity; k3++)
        {
            int l6 = xDef+random.nextInt(xWide);
            int i10 = random.nextInt(32) + hoehenVersatz;
            int k14 = yDef+random.nextInt(yWide);
            (new WorldGenMinable(Block.oreGold, 8)).generate(this.currentWorld, random, l6, i10, k14);
        }

        for(int l3 = 0; l3 < ConfigManager.oreRedstoneRarity; l3++)
        {
            int i7 = xDef+random.nextInt(xWide);
            int j10 = random.nextInt(16) + hoehenVersatz;
            int l14 = yDef+random.nextInt(yWide);
            (new WorldGenMinable(Block.oreRedstone, 7)).generate(this.currentWorld, random, i7, j10, l14);
        }

        for(int i4 = 0; i4 < ConfigManager.oreDiamondRarity; i4++)
        {
            int j7 = xDef+random.nextInt(xWide);
            int k10 = random.nextInt(16) + hoehenVersatz;
            int i15 = yDef+random.nextInt(yWide);
            (new WorldGenMinable(Block.oreDiamond, 7)).generate(this.currentWorld, random, j7, k10, i15);
        }

        for(int j4 = 0; j4 < ConfigManager.oreLapisRarity; j4++)
        {
            int k7 = xDef+random.nextInt(xWide);
            int l10 = random.nextInt(16) + random.nextInt(16) + hoehenVersatz;
            int j15 = yDef+random.nextInt(yWide);
            (new WorldGenMinable(Block.oreLapis, 6)).generate(this.currentWorld, random, k7, l10, j15);
        }
	}
	
	
	
	
	
	

}

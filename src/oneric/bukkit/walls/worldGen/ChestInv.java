package oneric.bukkit.walls.worldGen;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class ChestInv {
	
	public ChestInv()
	{
		
	}
	
	
	
	/**
	 * 
	 * Picks random stuff for the chests
	 * 
	 * */
	/*
	 * 
	 * TileEntityChest var16 = (TileEntityChest)World.getBlockTileEntity(x+1, y, z+2);

                         if (var16 != null)
                         {
                             for (int var17 = 0; var17 < 8; ++var17)
                             {
                                 ItemStack var18 = ChestInv.setInventory(par2Random);

                                 if (var18 != null)
                                 {
                                     var16.setInventorySlotContents(par2Random.nextInt(var16.getSizeInventory()), var18);
                                 }
                             }
                         }
	 * 
	 * 
	 * */
	
	public ItemStack setInventory(Random rand)
	{
		
		int i = rand.nextInt(13);
		
		if(i == 0)
		{
			return new ItemStack(Material.BREAD, rand.nextInt(3) + 1);
		}
		
		if(i == 1)
		{
			return new ItemStack(Material.APPLE, rand.nextInt(4) + 1);
		}
		
		if(i == 2)
		{
			return new ItemStack(Material.RAW_FISH, rand.nextInt(3) + 1);
		}
		
		if(i == 3 && rand.nextInt(2) == 0)
		{
			return new ItemStack(Material.STRING, rand.nextInt(3) + 1);
		}
		
		if(i == 4 && rand.nextInt(4) == 0)
		{
			return new ItemStack(Material.REDSTONE, rand.nextInt(5) + 1);
		}
		
		if(i == 5 && rand.nextInt(2) == 0)
		{
			/**int c = rand.nextInt(5) + 1;
			
			/*if(c == 0 && rand.nextInt(5) == 0)
			{
				return new ItemStack(Item.bootsLeather, 1);
			}*\
			
			if(c == 1 && rand.nextInt(4) == 0)
			{
				return new ItemStack(Item.bootsSteel, 1);
			}
			
			else if(c == 2 && rand.nextInt(6) == 0)
			{
				return new ItemStack(Item.bootsGold, 1);
			}
			
			else if(c == 3 && rand.nextInt(300) == 0)
			{
				return new ItemStack(Item.bootsDiamond, 1);
			}
			
			else if(c == 4 && rand.nextInt(7) == 0)
			{
				return new ItemStack(Item.bootsChain, 1);
			}
			
			else
			{
				return new ItemStack(Item.bootsLeather, 1);
			}*/
			return new ItemStack(Material.BONE, rand.nextInt(6) + 1);
			
		}
		
		if(i == 6 && rand.nextInt(3) == 0)
		{
			/**
			int c = rand.nextInt(5) + 1;
			
			/*if(c == 0 && rand.nextInt(5) == 0)
			{
				return new ItemStack(Item.legsLeather, 1);
			}*\
			
			if(c == 1 && rand.nextInt(4) == 0)
			{
				return new ItemStack(Item.legsSteel, 1);
			}
			
			else if(c == 2 && rand.nextInt(6) == 0)
			{
				return new ItemStack(Item.legsGold, 1);
			}
			
			else if(c == 3 && rand.nextInt(300) == 0)
			{
				return new ItemStack(Item.legsDiamond, 1);
			}
			
			else if(c == 4 && rand.nextInt(7) == 0)
			{
				return new ItemStack(Item.legsChain, 1);
			}
			
			else
			{
				return new ItemStack(Item.legsLeather, 1);
			}*/
			
			try
			{
				return new ItemStack(Material.SAPLING, rand.nextInt(3) + 1, (short)rand.nextInt(5));
			}catch(Exception ex){System.out.println("------------------------------------------FAIL--------------------------");}
			
		}
		
		if(i == 7 && rand.nextInt(4) == 0)
		{
			/***
			int c = rand.nextInt(5) + 1;
			
			/*if(c == 0 && rand.nextInt(5) == 0)
			{
				return new ItemStack(Item.plateLeather, 1);
			}*\
			
			if(c == 1 && rand.nextInt(4) == 0)
			{
				return new ItemStack(Item.plateSteel, 1);
			}
			
			else if(c == 2 && rand.nextInt(6) == 0)
			{
				return new ItemStack(Item.plateGold, 1);
			}
			
			else if(c == 3 && rand.nextInt(300) == 0)
			{
				return new ItemStack(Item.plateDiamond, 1);
			}
			
			else if(c == 4 && rand.nextInt(7) == 0)
			{
				return new ItemStack(Item.plateChain, 1);
			}
			
			else
			{
				return new ItemStack(Item.plateLeather, 1);
			}*/
			
			return new ItemStack(Material.TRIPWIRE_HOOK, (rand.nextInt(2) + 1));
		}
		
		if(i == 8 && rand.nextInt(3) == 0)
		{
			/**
			int c = rand.nextInt(5) + 1;
			
			/*if(c == 0 && rand.nextInt(5) == 0)
			{
				return new ItemStack(Item.helmetLeather, 1);
			}*\
			
			if(c == 1 && rand.nextInt(3) == 0)
			{
				return new ItemStack(Item.helmetSteel, 1);
			}
			
			else if(c == 2 && rand.nextInt(5) == 0)
			{
				return new ItemStack(Item.helmetGold, 1);
			}
			
			else if(c == 3 && rand.nextInt(300) == 0)
			{
				return new ItemStack(Item.helmetDiamond, 1);
			}
			
			else if(c == 4 && rand.nextInt(9) == 0)
			{
				return new ItemStack(Item.helmetChain, 1);
			}
			
			else
			{
				return new ItemStack(Item.helmetLeather, 1);
			}*/
			
			return new ItemStack(Material.ARROW, rand.nextInt(7) + 2);
		}
		
		if(i == 9 /*&& rand.nextInt() == 0*/)
		{
			int c = rand.nextInt(6);
			
			if(c == 0 && rand.nextInt(5) == 0)
			{
				return new ItemStack(Material.ENDER_PEARL, 1);
			}
			
			else if(c == 1 && rand.nextInt(4) == 0)
			{
				return new ItemStack(Material.OBSIDIAN, 1 + rand.nextInt(3));
			}
			
			else if(c == 2 && rand.nextInt(2) == 0)
			{
				return new ItemStack(Material.PISTON_BASE, 1);
			}
			
			else if(c == 3 && rand.nextInt(3) == 0)
			{
				return new ItemStack(Material.PISTON_STICKY_BASE, 1);
			}
			
			else if(c == 4 && rand.nextInt(5) == 0)
			{
				return new ItemStack(Material.DISPENSER, 1);
			}
			
			else
			{
				return new ItemStack(Material.SLIME_BALL, 1 + rand.nextInt(3));
			}
		}
		
		if(i == 10 && rand.nextInt(10) == 0)
		{
			return new ItemStack(Material.TNT, rand.nextInt(3));
		}
		
		if(i == 11)
		{
			if(rand.nextInt(15) == 0)
			{
				int p;
				if(rand.nextInt(40) == 0)
				{
					p = rand.nextInt(4);
				}
				else
				{
					p = 1;
				}
				
				return new ItemStack(Material.GOLD_INGOT, p);
			}
			
			else if(rand.nextInt(3) == 0)
			{
				return new ItemStack(Material.MUSHROOM_SOUP, 1);
			}
			
		}
		
		if(i == 12 && rand.nextInt(36) == 0)
		{
			int p;
			if(rand.nextInt(51) == 0)
			{
				p = rand.nextInt(3);
			}
			else
			{
				p = 1;
			}
			
			
			return new ItemStack(Material.DIAMOND, p);
			
			
			
		}
		
		
		else
		{
			return null;
		}
		
	} 
	
	public ItemStack setInventoryMiddle(Random rand)
	{
		int i = rand.nextInt(13);
		
		if(i == 0)
		{
			return new ItemStack(Material.GOLD_INGOT, rand.nextInt(3) + 1);
		}
		
		if(i == 1)
		{
			return new ItemStack(Material.IRON_INGOT, rand.nextInt(4) + 1);
		}
		
		if(i == 2)
		{
			return new ItemStack(Material.COOKED_BEEF, rand.nextInt(3) + 1);
		}
		
		if(i == 3 && rand.nextInt(2) == 0)
		{
			return new ItemStack(Material.PORK, rand.nextInt(3) + 1);
		}
		
		if(i == 4 && rand.nextInt(4) == 0)
		{
			return new ItemStack(Material.EXP_BOTTLE, rand.nextInt(5) + 1);
		}
		
		if(i == 5 && rand.nextInt(2) == 0)
		{
			return new ItemStack(Material.BONE, rand.nextInt(6) + 1);
		}
		
		if(i == 6 && rand.nextInt(3) == 0)
		{
			try
			{
				return new ItemStack(Material.SAPLING, rand.nextInt(3) + 1, (short)rand.nextInt(5));
			}catch(Exception ex){System.out.println("------------------------------------------FAIL--------------------------");}
			
		}
		
		if(i == 7 && rand.nextInt(4) == 0)
		{
			return new ItemStack(Material.TRIPWIRE_HOOK, (rand.nextInt(2) + 1));
		}
		
		if(i == 8 && rand.nextInt(3) == 0)
		{
			return new ItemStack(Material.ARROW, rand.nextInt(7) + 2);
		}
		
		if(i == 9 && rand.nextInt(5) == 0)
		{
			int c = rand.nextInt(2);
			
			if(c == 0 && rand.nextInt(7) == 0)
			{
				return new ItemStack(Material.ENDER_PEARL, 1);
			}
			
			else if(c == 1 && rand.nextInt(4) == 0)
			{
				return new ItemStack(Material.OBSIDIAN, 1 + rand.nextInt(3));
			}
			
		}
		
		if(i == 10 && rand.nextInt(7) == 0)
		{
			return new ItemStack(Material.TNT, rand.nextInt(3));
		}
		
		if(i == 11)
		{
			if(rand.nextInt(10) == 0)
			{
				int p;
				if(rand.nextInt(40) == 0)
				{
					p = rand.nextInt(4);
				}
				else
				{
					p = 1;
				}
				
				return new ItemStack(Material.ENDER_PEARL, p);
			}
			
			else if(rand.nextInt(3) == 0)
			{
				return new ItemStack(Material.EXP_BOTTLE, 1);
			}
			
		}
		
		if(i == 12 && rand.nextInt(19) == 0)
		{
			int p;
			if(rand.nextInt(51) == 0)
			{
				p = rand.nextInt(3);
			}
			else
			{
				p = 1;
			}
			
			
			return new ItemStack(Material.DIAMOND, p);
			
			
			
		}
		
		else
		{
			return null;
		}
	}

}

package oneric.bukkit.walls.worldGen;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;



public class WorldGenManager {
	
	public static ChestInv chestInv = new ChestInv();
	protected Random rand = new Random();
	

	
	public int normalX;
	public int normalY;
	public int normalZ;
	
	
	protected WorldGenManager(World world, int x, int y, int z)
	{
		this.normalX = x;
		this.normalY = y;
		this.normalZ = z;
		
		
	}
	
	protected int getRandomBlock()
	{
		
		return (rand.nextInt(145) + 1);
		//return Block.blocksList[rand.nextInt(145)].blockID;
		
	}
	
	private void setChestInventory(World world, int x, int y, int z)
	{
		
		
	
		
		
		BlockState var16 = world.getBlockAt(x, y, z).getState();

        if (var16 instanceof Chest)
        {
            for (int var17 = 0; var17 < 10; ++var17)
            {
            	
                ItemStack var18 = chestInv.setInventory(rand);

                
                if (var18 != null){
                	((Chest) var16).getInventory().setItem(rand.nextInt(27), var18);
                }
                
                
            }
        }
		
	}
	
	protected void setRotatedInventory(int rotation, int Invert, World world, int plusX, int plusY, int plusZ)
	{
		if(rotation == 0)
    	{ 
			if(world.getBlockAt(normalX+plusZ+(2*(plusZ*Invert)), normalY+plusY, normalZ+plusX+(2*(plusX*Invert))).getType() == Material.CHEST)
			{
				this.setChestInventory(world, normalX+plusZ+(2*(plusZ*Invert)), normalY+plusY, normalZ+plusX+(2*(plusX*Invert)));
			}
    		
    	}
    	
    	else
    	{
    		if(world.getBlockAt(normalX+plusX+(2*(plusX*Invert)), normalY+plusY, normalZ+plusZ+(2*(plusZ*Invert))).getType() == Material.CHEST)
    		{
    			this.setChestInventory(world, normalX+plusX+(2*(plusX*Invert)), normalY+plusY, normalZ+plusZ+(2*(plusZ*Invert)));
    		}
    		
    	}
	}
	
	
	/**
	 * Set aactual x, y and z position
	 * 
	 * */
	protected void setPosition(int x, int y, int z)
	{
		this.normalX = x;
		this.normalY = y;
		this.normalZ = z;
	}
	
	/**
     * Set a block with Berueksichtigung einer zufaelligen Rotation
     * */
    protected void setWithRotationAndNotify(int rotation ,int Invert ,World world, int plusX, int plusY, int plusZ, int BlockID)
    {
    	this.setWithRotationAndMetadataWithNotify(rotation, Invert, world, plusX, plusY, plusZ, BlockID, 0);
    }
    
    
    
    
    
    protected void setWithRotation(int rotation ,int Invert ,World world, int plusX, int plusY, int plusZ, int BlockID)
    {
    	this.setWithRotationAndMetadata(rotation, Invert, world, plusX, plusY, plusZ, BlockID, 0);
    }
    
    
    
    
    protected void setWithRotationAndMetadata(int rotation ,int Invert ,World world, int plusX, int plusY, int plusZ, int BlockID, int metadata)
    {
    	this.setWithRotationAndMetadataWithNotify(rotation, Invert, world, plusX, plusY, plusZ, BlockID, metadata);
    }
    
    protected void setWithRotationAndMetadataWithNotify(int rotation ,int Invert ,World world, int plusX, int plusY, int plusZ, int BlockID, int metadata)
    {
    	if(rotation == 0)
    	{
    		if(world.getBlockAt(normalX+plusZ+(2*(plusZ*Invert)), normalY+plusY, normalZ+plusX+(2*(plusX*Invert))).getType() != Material.BEDROCK)
    		{
    			world.getBlockAt(normalX+plusZ+(2*(plusZ*Invert)), normalY+plusY, normalZ+plusX+(2*(plusX*Invert))).setTypeId(BlockID);
    			world.getBlockAt(normalX+plusZ+(2*(plusZ*Invert)), normalY+plusY, normalZ+plusX+(2*(plusX*Invert))).setData((byte)metadata);
    		}
    		
    	}
    	
    	else
    	{
    		if(world.getBlockAt(normalX+plusX+(2*(plusX*Invert)), normalY+plusY, normalZ+plusZ+(2*(plusZ*Invert))).getType() != Material.BEDROCK)
    		{
    			world.getBlockAt(normalX+plusX+(2*(plusX*Invert)), normalY+plusY, normalZ+plusZ+(2*(plusZ*Invert))).setTypeId(BlockID);
    			world.getBlockAt(normalX+plusX+(2*(plusX*Invert)), normalY+plusY, normalZ+plusZ+(2*(plusZ*Invert))).setData((byte)metadata);
    		}
    		
    	}
    	
    	
    }
	
	

}

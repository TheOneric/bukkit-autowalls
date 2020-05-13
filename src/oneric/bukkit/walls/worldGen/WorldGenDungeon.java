package oneric.bukkit.walls.worldGen;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;



public class WorldGenDungeon extends WorldGenManager{
	
	private int block1 = Block.stone;
	private int block2 = Block.stone;
	private int block3 = Block.stone;
	private int block4 = Block.stone;
	
	private int metadata1 = 0;
	private int metadata2 = 1;
	private int metadata3 = 2;
	private int metadata4 = 3;
	
	private static int def_width = 10;
	private static int def_height = 10;
	
	
	public WorldGenDungeon(World world, int x, int y, int z)
	{
		super(world, x, y, z);
		this.generate(world, x, y, z, def_width, def_height);
		
	}
	
	public WorldGenDungeon(World world, int x, int y, int z, int width, int lenght, int block1, int block2, int block3, int block4)
	{
		
		super(world, x, y, z);
		
		this.block1 = block1;
		this.block2 = block2;
		this.block3 = block3;
		this.block4 = block4;
		this.generate(world , x, y, z, width, lenght);
		
		
	}
	
	
	public WorldGenDungeon(int met1, int met2, int met3, int met4, World world, int x, int y, int z, int width, int lenght, int block)
	{
		
		super(world, x, y, z);
		
		this.block1 = block;
		this.block2 = block;
		this.block3 = block;
		this.block4 = block;
		
		this.metadata1 = met1;
		this.metadata2 = met2;
		this.metadata3 = met3;
		this.metadata4 = met4;
		
		this.generate(world , x, y, z, width, lenght);
		
		
	}
	
	
	
	
	public WorldGenDungeon(World world, int x, int y, int z, int width, int lenght)
	{
		super(world, x, y, z);
		this.generate(world, x, y, z, width, lenght);
	}
	
	
	private void  generate(World world, int x, int y, int z, int width, int lenght)
	{
		
		
		Random rand = new Random(); 
		
		int height = rand.nextInt(2) + 4;
		
		//Tell if it should be normal or 90 Grad rotate
		int rotation = rand.nextInt(2);
								
		//Invert integer
		int Invert = (rand.nextInt(2) * -1);
		
		
		if(world.getBlockAt(x+width, y, z).getType() != Material.AIR && world.getBlockAt(x+width, y, z+lenght).getType() != Material.AIR && world.getBlockAt(x, y, z).getType() != Material.AIR && world.getBlockAt(x, y, z+lenght).getType() != Material.AIR  &&  world.getBlockAt(x+width, y+height, z).getType() != Material.AIR && world.getBlockAt(x+width, y+height, z+lenght).getType() != Material.AIR && world.getBlockAt(x, y+height, z).getType() != Material.AIR && world.getBlockAt(x, y+height, z+lenght).getType() != Material.AIR)
		{
			if(world.getBlockAt(x, y+height, z).getType() != Material.GRASS)
			{
		
				for(int x1 = 0; x1 <= width; x1++)
				{
					
					for(int z1 = 0; z1 <= lenght; z1++)
					{
						
						for(int y1 = 0; y1 <= height; y1++)
						{
							
							
							/**try{
								this.setWithRotation(rotation, Invert, world, x+x1, y+y1, z+z1, this.getRandomBlock() /*Block.blockDiamond.blockID*\);
							}catch(Exception ex){}*/
							
							
							this.setWithRotation(rotation, Invert, world, x1, y1, z1, 0);
							
						}
						
					}
					
				}
				
				
				for(int x1 = 0; x1 <= width; x1++)
				{
					
					for(int z1 = 0; z1 <= lenght; z1++)
					{
						
						for(int y1 = 0; y1 <= height; y1++)
						{
							
							this.setWithRotationAndMetadata(rotation, Invert, world, x1, height, z1, this.getBlock(rand), this.getMetadata(rand));
							this.setWithRotationAndMetadata(rotation, Invert, world, x1, 0, z1, this.getBlock(rand), this.getMetadata(rand));
							
							this.setWithRotationAndMetadata(rotation, Invert, world, width, y1, z1, this.getBlock(rand), this.getMetadata(rand));
							this.setWithRotationAndMetadata(rotation, Invert, world, 0, y1, z1, this.getBlock(rand), this.getMetadata(rand));
							
							this.setWithRotationAndMetadata(rotation, Invert, world, x1, y1, lenght, this.getBlock(rand), this.getMetadata(rand));
							this.setWithRotationAndMetadata(rotation, Invert, world, x1, y1, 0, this.getBlock(rand), this.getMetadata(rand));
							
						}
						
					}
					
				}
				
				
				this.setWithRotation(rotation, Invert, world, width/2, 1, lenght/2, Block.chest);
				
				this.setRotatedInventory(rotation, Invert, world, width/2, 1, lenght/2);
				
				
				
			}
		}
		
	}
	
	private int getBlock(Random rand)
	{
		int block = block1;
		switch(rand.nextInt(4))
		{
		
		case 0:
			block = block4;
			break;
			
		case 1:
			block = block3;
			break;
			
		case 2:
			block = block2;
			break;
			
			default:
				block = block1;
				break;
		
		}
		
		return block;
	}
	
	private int getMetadata(Random rand)
	{
		int met = metadata1;
		switch(rand.nextInt(4))
		{
		
		case 0:
			met = metadata4;
			break;
			
		case 1:
			met = metadata3;
			break;
			
		case 2:
			met = metadata2;
			break;
			
			default:
				met = metadata1;
				break;
		
		}
		
		return met;
	}
	
	
	
	
	
	
	

}

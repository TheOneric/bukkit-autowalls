package oneric.bukkit.walls.src;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import oneric.bukkit.walls.worldGen.Block;
import oneric.bukkit.walls.worldGen.ChestInv;



public class ArenaManagement /*implements Runnable*/{
	
	
	protected WallsArena arena;
	protected WallsPlugin plugin;
	protected World currentWorld;
	protected Random random;
	
	protected ArrayList<int[]> pos;
	
	
	public ArenaManagement(WallsArena wArena, WallsPlugin plugin)
	{
		this.arena = wArena;
		this.plugin = plugin;
		this.currentWorld = this.plugin.getServer().getWorld(arena.currentWorld);
		random = new Random();
		
		this.pos = new ArrayList<int[]>();
		
		int[] pos0 = {0, arena.yWide};
		int[] pos1 = {0, 0};
		int[] pos2 = {0, (2*arena.yWide)};
		int[] pos3 = {(0-arena.xWide), arena.yWide};
		int[] pos4 = {arena.xWide, arena.yWide};
		int[] pos5 = {(0-arena.xWide), (2*arena.yWide)};
		int[] pos6 = {arena.xWide, (2*arena.yWide)};
		int[] pos7 = {arena.xWide, 0};
		int[] pos8 = {(0-arena.xWide), 0};
		
		
		
		this.pos.add(0, pos0);
		this.pos.add(1, pos1);
		this.pos.add(2, pos2);
		this.pos.add(3, pos3);
		this.pos.add(4, pos4);
		this.pos.add(5, pos5);
		this.pos.add(6, pos6);
		this.pos.add(7, pos7);
		this.pos.add(8, pos8);
	}


	
	public void recreate() {
	}
	
	
	
	public void resetMiddle(int xWide, int zWide)
	{
		
		for(int x = this.pos.get(0)[0]; x < this.pos.get(0)[0] + xWide; x++)
		{
			for(int z = this.pos.get(0)[1]; z < this.pos.get(0)[1] + zWide; z++)
			{
				for(int y = 0; y < this.currentWorld.getMaxHeight(); y++)
				{
					
					switch(this.currentWorld.getBlockTypeIdAt(x, y, z))
					{
					case Block.chest:
						
						BlockState var16 = currentWorld.getBlockAt(x, y, z).getState();

				        if (var16 instanceof Chest)
				        {
				        	ChestInv chestInv = new ChestInv();
				        	Random rand = new Random();
				            for (int var17 = 0; var17 < 10; ++var17)
				            {
				            	
				                ItemStack var18 = chestInv.setInventoryMiddle(rand);

				                
				                if (var18 != null){
				                	((Chest) var16).getInventory().setItem(rand.nextInt(27), var18);
				                }
				                
				                
				            }
				        }
						
						break;
					}
					
				}
			}
		}
	}
	
	
	
	
	public void removeWalls(int xWide, int zWide)
	{
		int gruppen = arena.AnzahlGruppen;
		
		for(int counter = 1; counter <= gruppen; counter++)
		{
			switch(counter)
			{
			
			case 1:
				
				int z1 = pos.get(1)[1] + zWide - 1;
				
				for(int x = pos.get(1)[0] + 1; x <= (pos.get(1)[0] + xWide - 2); x++)
				{
					
						for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z1); y++)
						{
							
							switch(this.currentWorld.getBlockTypeIdAt(x, y, z1))
							{
							case Block.slowSand:
								this.currentWorld.getBlockAt(x, y, z1).setTypeId(0);
								break;
							}
							
						}
					
				}
				
				break;
				
				
				
			case 2:
				int z2 = pos.get(2)[1];
				
				for(int x = pos.get(2)[0] + 1; x <= (pos.get(2)[0] + xWide - 2); x++)
				{
					
						for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z2); y++)
						{
							
							switch(this.currentWorld.getBlockTypeIdAt(x, y, z2))
							{
							case Block.slowSand:
								this.currentWorld.getBlockAt(x, y, z2).setTypeId(0);
								break;
							}
							
						}
					
				}
				
				
			case 3:
				
				int x1 = pos.get(3)[0] + xWide - 1;
				
				for(int z = pos.get(3)[1] + 1; z < (pos.get(3)[1] + zWide - 1); z++)
				{
					
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x1, z); y++)
					{
						
						switch(this.currentWorld.getBlockTypeIdAt(x1, y, z))
						{
						case Block.slowSand:
							this.currentWorld.getBlockAt(x1, y, z).setTypeId(0);
							break;
						}
						
					}
					
				}
				
				
				break;
				
				
				
				
			case 4:
				
				int x2 = pos.get(4)[0];
				
				for(int z = pos.get(4)[1] + 1; z < (pos.get(4)[1] + zWide - 1); z++)
				{
					
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x2, z); y++)
					{
						
						switch(this.currentWorld.getBlockTypeIdAt(x2, y, z))
						{
						case Block.slowSand:
							this.currentWorld.getBlockAt(x2, y, z).setTypeId(0);
							break;
						}
						
					}
					
				}
				
				break;
				
				
				
				
			case 5:
				
				/// 3
				int x5 = pos.get(5)[0] + xWide - 1;
				
				for(int z = pos.get(5)[1]; z < (pos.get(5)[1] + zWide - 1); z++)
				{
					
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x5, z); y++)
					{
						
						switch(this.currentWorld.getBlockTypeIdAt(x5, y, z))
						{
						case Block.slowSand:
							this.currentWorld.getBlockAt(x5, y, z).setTypeId(0);
							break;
						}
						
					}
					
				}
				
				///2
				
				int z5 = pos.get(5)[1];
				
				for(int x = pos.get(5)[0] + 1; x <= (pos.get(5)[0] + xWide - 2); x++)
				{
					
						for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z5); y++)
						{
							
							switch(this.currentWorld.getBlockTypeIdAt(x, y, z5))
							{
							case Block.slowSand:
								this.currentWorld.getBlockAt(x, y, z5).setTypeId(0);
								break;
							}
							
						}
					
				}
				
				
				
				//4
				
				
				int x52 = pos.get(2)[0];
				
				for(int z = pos.get(2)[1]; z < (pos.get(2)[1] + zWide - 1); z++)
				{
					
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x52, z); y++)
					{
						
						switch(this.currentWorld.getBlockTypeIdAt(x52, y, z))
						{
						case Block.slowSand:
							this.currentWorld.getBlockAt(x52, y, z).setTypeId(0);
							break;
						}
						
					}
					
				}
				
				////1
				
				int z51 = pos.get(3)[1] + zWide - 1;
				
				for(int x = pos.get(3)[0] + 1; x <= (pos.get(3)[0] + xWide - 1); x++)
				{
					
						for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z51); y++)
						{
							
							switch(this.currentWorld.getBlockTypeIdAt(x, y, z51))
							{
							case Block.slowSand:
								this.currentWorld.getBlockAt(x, y, z51).setTypeId(0);
								break;
							}
							
						}
					
				}
				
				
				break;
				
				
				
				
			case 6:
				
///2
				
				int z61 = pos.get(6)[1];
				
				for(int x = pos.get(6)[0] + 1; x <= (pos.get(6)[0] + xWide - 2); x++)
				{
					
						for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z61); y++)
						{
							
							switch(this.currentWorld.getBlockTypeIdAt(x, y, z61))
							{
							case Block.slowSand:
								this.currentWorld.getBlockAt(x, y, z61).setTypeId(0);
								break;
							}
							
						}
					
				}
				
//4
				
				
				int x61 = pos.get(6)[0];
				
				for(int z = pos.get(6)[1]; z < (pos.get(6)[1] + zWide - 1); z++)
				{
					
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x61, z); y++)
					{
						
						switch(this.currentWorld.getBlockTypeIdAt(x61, y, z))
						{
						case Block.slowSand:
							this.currentWorld.getBlockAt(x61, y, z).setTypeId(0);
							break;
						}
						
					}
					
				}
				
				
			
////1
				
			int z62 = pos.get(4)[1] + zWide - 1;
							
			for(int x = pos.get(4)[0]; x <= (pos.get(4)[0] + xWide - 2); x++)
			{
						
				for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z62); y++)
				{
										
					switch(this.currentWorld.getBlockTypeIdAt(x, y, z62))
					{
						case Block.slowSand:
						this.currentWorld.getBlockAt(x, y, z62).setTypeId(0);
						break;
					}
										
				}
								
			}	
			
			/// 3
			int x62 = pos.get(2)[0] + xWide - 1;
			
			for(int z = pos.get(2)[1]; z < (pos.get(2)[1] + zWide - 1); z++)
			{
				
				for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x62, z); y++)
				{
					
					switch(this.currentWorld.getBlockTypeIdAt(x62, y, z))
					{
					case Block.slowSand:
						this.currentWorld.getBlockAt(x62, y, z).setTypeId(0);
						break;
					}
					
				}
				
			}
				
				
				
				
				
				
				
				break;
				
				
				
			case 7:
				
//4
				
				
				int x71 = pos.get(7)[0];
				
				for(int z = pos.get(7)[1] + 1; z < (pos.get(7)[1] + zWide - 1); z++)
				{
					
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x71, z); y++)
					{
						
						switch(this.currentWorld.getBlockTypeIdAt(x71, y, z))
						{
						case Block.slowSand:
							this.currentWorld.getBlockAt(x71, y, z).setTypeId(0);
							break;
						}
						
					}
					
				}
				
				
			
////1
				
			int z71 = pos.get(7)[1] + zWide - 1;
							
			for(int x = pos.get(7)[0]; x <= (pos.get(7)[0] + xWide - 2); x++)
			{
						
				for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z71); y++)
				{
										
					switch(this.currentWorld.getBlockTypeIdAt(x, y, z71))
					{
						case Block.slowSand:
						this.currentWorld.getBlockAt(x, y, z71).setTypeId(0);
						break;
					}
										
				}
								
			}	
			
			
			
/// 3
			int x72 = pos.get(1)[0] + xWide - 1;
			
			for(int z = pos.get(1)[1] + 1; z <= (pos.get(1)[1] + zWide - 1); z++)
			{
				
				for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x72, z); y++)
				{
					
					switch(this.currentWorld.getBlockTypeIdAt(x72, y, z))
					{
					case Block.slowSand:
						this.currentWorld.getBlockAt(x72, y, z).setTypeId(0);
						break;
					}
					
				}
				
			}
			
///2
			
			int z72 = pos.get(4)[1];
			
			for(int x = pos.get(4)[0]; x <= (pos.get(4)[0] + xWide - 2); x++)
			{
				
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z72); y++)
					{
						
						switch(this.currentWorld.getBlockTypeIdAt(x, y, z72))
						{
						case Block.slowSand:
							this.currentWorld.getBlockAt(x, y, z72).setTypeId(0);
							break;
						}
						
					}
				
			}
			
				
				break;
				
				
				
			case 8:
				
			////1
				
				int z81 = pos.get(8)[1] + zWide - 1;
								
				for(int x = pos.get(8)[0] + 1; x <= (pos.get(8)[0] + xWide - 1); x++)
				{
							
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z81); y++)
					{
											
						switch(this.currentWorld.getBlockTypeIdAt(x, y, z81))
						{
							case Block.slowSand:
							this.currentWorld.getBlockAt(x, y, z81).setTypeId(0);
							break;
						}
											
					}
									
				}	
				
				
				
	/// 3
				int x81 = pos.get(8)[0] + xWide - 1;
				
				for(int z = pos.get(8)[1] + 1; z <= (pos.get(8)[1] + zWide - 1); z++)
				{
					
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x81, z); y++)
					{
						
						switch(this.currentWorld.getBlockTypeIdAt(x81, y, z))
						{
						case Block.slowSand:
							this.currentWorld.getBlockAt(x81, y, z).setTypeId(0);
							break;
						}
						
					}
					
				}
				
				
				/**
				 * 
				 * */
				
				///2
				
				int z82 = pos.get(3)[1];
				
				for(int x = pos.get(3)[0] + 1; x <= (pos.get(3)[0] + xWide - 1); x++)
				{
					
						for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x, z82); y++)
						{
							
							switch(this.currentWorld.getBlockTypeIdAt(x, y, z82))
							{
							case Block.slowSand:
								this.currentWorld.getBlockAt(x, y, z82).setTypeId(0);
								break;
							}
							
						}
					
				}
				
				
				//4
				
				
				int x82 = pos.get(1)[0];
				
				for(int z = pos.get(1)[1] + 1; z <= (pos.get(1)[1] + zWide - 1); z++)
				{
					
					for(int y = 0; y <= this.currentWorld.getHighestBlockYAt(x82, z); y++)
					{
						
						switch(this.currentWorld.getBlockTypeIdAt(x82, y, z))
						{
						case Block.slowSand:
							this.currentWorld.getBlockAt(x82, y, z).setTypeId(0);
							break;
						}
						
					}
					
				}
				
				
				
				break;
			
			}
		}
		
		
	}

}

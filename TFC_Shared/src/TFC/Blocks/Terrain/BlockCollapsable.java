package TFC.Blocks.Terrain;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import TFC.TFCBlocks;
import TFC.API.TFCOptions;
import TFC.API.Enums.TFCDirection;
import TFC.API.Util.ByteCoord;
import TFC.API.Util.CollapseData;
import TFC.API.Util.CollapseList;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Sounds;
import TFC.Entities.EntityFallingStone;
import TFC.TileEntities.TileEntityPartial;

public class BlockCollapsable extends BlockTerraContainer
{
	public int dropBlock;

	protected BlockCollapsable(int par1, Material material, int d)
	{
		super(par1, material);
		dropBlock = d;
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	protected BlockCollapsable(int par1, Material material)
	{
		super(par1, material);
		dropBlock = 0;
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public int[] getDropBlock(World world, int i, int j, int k)
	{
		int[] data = new int[2];
		data[0] = dropBlock;
		data[1] = world.getBlockMetadata(i, j, k);
		return data;
	}

	public static boolean canFallBelow(World world, int i, int j, int k)
	{		
		int l = world.getBlockId(i, j, k);

		if (l == 0)
			return true;
		if (l == Block.bedrock.blockID)
			return false;
		if (l == Block.fire.blockID)
			return true;
		if (l == Block.tallGrass.blockID)
			return true;
		if (l == Block.torchWood.blockID)
			return true;
		Material material = Block.blocksList[l].blockMaterial;
		if (material == Material.water || material == Material.lava)
			return true;
		return false;
	}

	public void DropCarvedStone(World world, int i, int j, int k)
	{
		if(world.isBlockOpaqueCube(i+1, j, k))
			return;
		else if(world.isBlockOpaqueCube(i-1, j, k))
			return;
		else if(world.isBlockOpaqueCube(i, j, k+1))
			return;
		else if(world.isBlockOpaqueCube(i, j, k-1))
			return;
		else if(world.isBlockOpaqueCube(i, j+1, k))
			return;
		else if(world.isBlockOpaqueCube(i, j-1, k))
			return;

		dropBlockAsItem_do(world, i, j, k, new ItemStack(blockID, 1, world.getBlockMetadata(i, j, k)));
		world.setBlockToAir(i, j, k);

	}

	public Boolean hasNaturalSupport(World world, int i, int j, int k)
	{
		//Make sure that the block beneath the one we're checking is not a solid, if it is then return true and don't waste time here.
		if(world.getBlockId(i, j-1, k) != 0)
			return true;

		if(world.isBlockOpaqueCube(i+1, j, k))
			if(world.isBlockOpaqueCube(i+1, j-1, k) && world.isBlockOpaqueCube(i+1, j-2, k))
				return true;

		if(world.isBlockOpaqueCube(i-1, j, k))
			if(world.isBlockOpaqueCube(i-1, j-1, k) && world.isBlockOpaqueCube(i-1, j-2, k))
				return true;

		if(world.isBlockOpaqueCube(i, j, k+1))
			if(world.isBlockOpaqueCube(i, j-1, k+1) && world.isBlockOpaqueCube(i, j-2, k+1))
				return true;

		if(world.isBlockOpaqueCube(i, j, k-1))
			if(world.isBlockOpaqueCube(i, j-1, k-1) && world.isBlockOpaqueCube(i, j-2, k-1))
				return true;

		//Diagonals		
		if(world.isBlockOpaqueCube(i+1, j, k-1))
			if(world.isBlockOpaqueCube(i+1, j-1, k-1))
				return true;

		if(world.isBlockOpaqueCube(i-1, j, k-1))
			if(world.isBlockOpaqueCube(i-1, j-1, k-1))
				return true;

		if(world.isBlockOpaqueCube(i+1, j, k+1))
			if(world.isBlockOpaqueCube(i+1, j-1, k+1))
				return true;

		if(world.isBlockOpaqueCube(i-1, j, k+1))
			if(world.isBlockOpaqueCube(i-1, j-1, k+1))
				return true;

		return false;
	}

	public static Boolean isNearSupport(World world, int i, int j, int k, int range, float collapseChance)
	{
		for(int y = -1; y < 1; y++)
			for(int x = -range; x < range+1; x++)
				for(int z = -range; z < range+1; z++)
					if(world.getBlockId(i+x, j+y, k+z) == TFCBlocks.WoodSupportH.blockID)
						if(world.rand.nextFloat() < collapseChance/100f/2f)
							world.setBlock(i+x, j+y, k+z, 0);
						else return true;
		return false;
	}

	public Boolean isUnderLoad(World world, int i, int j, int k)
	{
		for(int x = 1; x <= TFCOptions.minimumRockLoad; x++)
			if(!world.isBlockOpaqueCube(i, j+x, k))
				return false;
		return true;
	}

	public Boolean tryToFall(World world, int i, int j, int k, float collapseChance)
	{
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		int[] drop = getDropBlock(world, i, j, k);
		int fallingBlockID = drop[0];
		int fallingBlockMeta = drop[1];

		if(world.getBlockId(xCoord, yCoord, zCoord) == Block.bedrock.blockID || world.getBlockId(xCoord, yCoord, zCoord) == fallingBlockID)
			return false;

		if (canFallBelow(world, xCoord, yCoord - 1, zCoord)  && !isNearSupport(world, i, j, k, 4, collapseChance)  && isUnderLoad(world, i, j, k))
			if (!world.isRemote && fallingBlockID != -1)
			{
				if(fallingBlockID != -1)
				{
					EntityFallingStone ent = new EntityFallingStone(world, i + 0.5F, j + 0.5F, k + 0.5F, fallingBlockID, fallingBlockMeta+8);
					ent.fallTime = -5000;
					world.spawnEntityInWorld(ent);

					Random R = new Random(i*j+k);
					if(R.nextInt(100) > 90)
						world.playSoundAtEntity(ent, TFC_Sounds.FALLININGROCKLONG, 1.0F, 0.8F + (R.nextFloat()/2));
				}

				world.setBlockToAir(i, j, k);

				if(world.getBlockId(i, j-1, k) == TFCBlocks.stoneSlabs.blockID && ((TileEntityPartial)world.getBlockTileEntity(i, j-1, k)).TypeID == this.blockID && 
						((TileEntityPartial)world.getBlockTileEntity(i, j-1, k)).MetaID == fallingBlockMeta)
				{
					world.setBlockToAir(i, j-1, k);

					if(world.getBlockId(i, j-2, k) == TFCBlocks.stoneSlabs.blockID && ((TileEntityPartial)world.getBlockTileEntity(i, j-2, k)).TypeID == this.blockID && 
							((TileEntityPartial)world.getBlockTileEntity(i, j-2, k)).MetaID == fallingBlockMeta)
					{
						world.setBlockToAir(i, j-2, k);

						if(world.getBlockId(i, j-3, k) == TFCBlocks.stoneSlabs.blockID && ((TileEntityPartial)world.getBlockTileEntity(i, j-3, k)).TypeID == this.blockID && 
								((TileEntityPartial)world.getBlockTileEntity(i, j-3, k)).MetaID == fallingBlockMeta)
							world.setBlockToAir(i, j-3, k);
					}
				}

				return true;
			}
		return false;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int meta)
	{   
		float seismicModifier = 0.2f;
		float softModifier = 0.1f;
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		int finalCollapseRatio = TFCOptions.initialCollapseRatio;

		//Make sure that the player gets exhausted from harvesting this block since we override the vanilla method
		if(entityplayer != null)
		{
			entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
			entityplayer.addExhaustion(0.075F);
		}	

		//If we are in a soft sedimentary rock layer then we increase the chance of a collapse by 10%
		if(blockID == TFCBlocks.StoneSed.blockID)
			finalCollapseRatio -= finalCollapseRatio * softModifier;
		//If we are in what is considered to be a seismically active zone, then we increase the chance by 20%
		if(biome.biomeName.contains("Seismic"))
			finalCollapseRatio -= finalCollapseRatio * seismicModifier;

		//First we check the rng to see if a collapse is going to occur
		if(world.rand.nextInt(finalCollapseRatio) == 0)
		{
			boolean found = false;
			//Now we look for a suitable block nearby to act as the epicenter
			for(int x1 = -1; x1 < 2 && !found; x1++)
				for(int z1 = -1; z1 < 2 && !found; z1++)
					if(Block.blocksList[world.getBlockId(i+x1, j, k+z1)] instanceof BlockCollapsable && 
							((BlockCollapsable)Block.blocksList[world.getBlockId(i+x1, j, k+z1)]).tryToFall(world, i+x1, j, k+z1, 0))
					{
						found = true;
						triggerCollapse(world, entityplayer, i, j, k, meta);
					}
		}
	}

	/**
	 * This is called when a collapse is definitely happening on a block.
	 * @param world
	 * @param entityplayer
	 * @param i
	 * @param j
	 * @param k
	 * @param meta
	 */
	public void triggerCollapse(World world, EntityPlayer entityplayer, int i, int j, int k, int meta)
	{  
		ArrayList<ByteCoord> collapseMap = getCollapseMap(world, i, j, k);
		/*int height = 4;
		int range = 5 + world.rand.nextInt(30);
		for(int y = -4; y <= 1; y++)
		{
			for(int x = -range; x <= range; x++)
			{
				for(int z = -range; z <= range; z++)
				{
					//double distance = Math.sqrt(Math.pow(i-(i+x),2) + Math.pow(j-(j+y),2) + Math.pow(k-(k+z),2));

					if(world.rand.nextInt(100) < TFCOptions.propogateCollapseChance && distance < 35)
					{
						if(Block.blocksList[world.getBlockId(i+x, j+y, k+z)] instanceof BlockCollapsable && 
								((BlockCollapsable)Block.blocksList[world.getBlockId(i+x, j+y, k+z)]).tryToFall(world, i+x, j+y, k+z, world.getBlockMetadata( i+x, j+y, k+z)))
						{
							int done = 0;
							while(done < height)
							{
								done++;
								if(Block.blocksList[world.getBlockId(i+x, j+y+done, k+z)] instanceof BlockCollapsable && world.rand.nextInt(100) < TFCOptions.propogateCollapseChance) {
									((BlockCollapsable)Block.blocksList[world.getBlockId(i+x, j+y+done, k+z)]).tryToFall(world, i+x, j+y+done, k+z,world.getBlockMetadata( i+x, j+y+done, k+z));
								} else {
									done = height;
								}
							}
						}
					}
				}
			}
		}*/
	}

	/**
	 * The coordinates given are the coordinates of the epicenter of the collapse
	 * @return This is a list of all coordinates which should collapse vertically, radiating outward from the epicenter
	 */
	public ArrayList<ByteCoord> getCollapseMap(World world, int i, int j, int k)
	{
		int checks = 0;
		ArrayList<ByteCoord> map = new ArrayList<ByteCoord>();
		ArrayList<ByteCoord> checkedmap = new ArrayList<ByteCoord>();
		CollapseList<CollapseData> checkQueue = new CollapseList<CollapseData>();
		final float incrementChance = 2.5f;
		final float incrementChanceDiag = 3.5f;

		int worldX;
		int worldY;
		int worldZ;
		int localX;
		int localY;
		int localZ;
		//We already know that the epicenter is going to collapse so we add it to our final map
		map.add(new ByteCoord(0,0,0));
		//Now we add each of the blocks around it so that the initial collapse tries to propogate in each direction
		checkQueue.add(new CollapseData(new ByteCoord(1,0,0), TFCOptions.propogateCollapseChance, TFCDirection.EAST));
		checkQueue.add(new CollapseData(new ByteCoord(-1,0,0), TFCOptions.propogateCollapseChance, TFCDirection.WEST));
		checkQueue.add(new CollapseData(new ByteCoord(1,0,1), TFCOptions.propogateCollapseChance, TFCDirection.NORTHEAST));
		checkQueue.add(new CollapseData(new ByteCoord(1,0,-1), TFCOptions.propogateCollapseChance, TFCDirection.SOUTHEAST));
		checkQueue.add(new CollapseData(new ByteCoord(-1,0,1), TFCOptions.propogateCollapseChance, TFCDirection.NORTHWEST));
		checkQueue.add(new CollapseData(new ByteCoord(-1,0,-1), TFCOptions.propogateCollapseChance, TFCDirection.SOUTHWEST));
		checkQueue.add(new CollapseData(new ByteCoord(0,0,1), TFCOptions.propogateCollapseChance, TFCDirection.SOUTH));
		checkQueue.add(new CollapseData(new ByteCoord(0,0,-1), TFCOptions.propogateCollapseChance, TFCDirection.NORTH));

		while(checkQueue.peek() != null)
		{
			CollapseData block = checkQueue.peek();	
			if(!checkedmap.contains(block) && world.rand.nextFloat() < block.collapseChance/100f)
			{
				checks++;
				//System.out.println("Number of block checks: " + checks + " | Queue Length: " + checkQueue.size());
				worldX = block.coords.x + i;
				worldY = block.coords.y + j;
				worldZ = block.coords.z + k;
				localX = block.coords.x;
				localY = block.coords.y;
				localZ = block.coords.z;
				if((world.getBlockId(worldX, worldY, worldZ) == 0 || Block.blocksList[world.getBlockId(worldX, worldY, worldZ)].isAirBlock(world, worldX, worldY, worldZ)) /*&& localY < 4*/)
					checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 1, localZ + 0), block.collapseChance - incrementChance*4, TFCDirection.UP));
				else if(Block.blocksList[world.getBlockId(worldX, worldY, worldZ)] instanceof BlockCollapsable && 
						((BlockCollapsable)Block.blocksList[world.getBlockId(worldX, worldY, worldZ)]).tryToFall(world, worldX, worldY, worldZ, block.collapseChance))
				{
					map.add(block.coords);

					switch(block.direction)
					{
					case NORTH:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						break;
					}
					case SOUTH:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						break;
					}
					case EAST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						break;
					}
					case WEST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						break;
					}
					case SOUTHEAST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ - 1), block.collapseChance - incrementChanceDiag, TFCDirection.SOUTHEAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						break;
					}
					case SOUTHWEST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ - 1), block.collapseChance - incrementChanceDiag, TFCDirection.SOUTHWEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						break;
					}
					case NORTHEAST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 1), block.collapseChance - incrementChanceDiag, TFCDirection.NORTHEAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						break;
					}
					case NORTHWEST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 1), block.collapseChance - incrementChanceDiag, TFCDirection.NORTHWEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						break;
					}
					default:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 1), block.collapseChance - incrementChanceDiag, TFCDirection.NORTHEAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ - 1), block.collapseChance - incrementChanceDiag, TFCDirection.SOUTHEAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 1), block.collapseChance - incrementChanceDiag, TFCDirection.NORTHWEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ - 1), block.collapseChance - incrementChanceDiag, TFCDirection.SOUTHWEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
					}
					}
				}

			}
			checkedmap.add(block.coords);
			checkQueue.removeFirst();
		}


		return map;
	}

	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion ex) 
	{
		harvestBlock(par1World, null, par2,par3,par4,par1World.getBlockMetadata(par2, par3, par4));
	}

	@Override
	public boolean canBeReplacedByLeaves(World w, int x, int y, int z)
	{
		return false;
	}

}

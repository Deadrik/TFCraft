package com.bioxx.tfc.Blocks.Terrain;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Entities.EntityFallingBlockTFC;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.TFCDirection;
import com.bioxx.tfc.api.Util.ByteCoord;
import com.bioxx.tfc.api.Util.CollapseData;
import com.bioxx.tfc.api.Util.CollapseList;

public class BlockCollapsable extends BlockTerraContainer
{
	public Block dropBlock;
	public static boolean fallInstantly = false;

	protected BlockCollapsable(Material material, Block block)
	{
		super(material);
		this.dropBlock = block;
		this.setCreativeTab(TFCTabs.TFCBuilding);
	}

	protected BlockCollapsable(Material material)
	{
		super(material);
		this.dropBlock = Blocks.air;
		this.setCreativeTab(TFCTabs.TFCBuilding);
	}

	public int[] getDropBlock(World world, int x, int y, int z)
	{
		int[] data = new int[2];
		data[0] = Block.getIdFromBlock(dropBlock);
		data[1] = world.getBlockMetadata(x, y, z);
		return data;
	}

	public static boolean canFallBelow(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		if (world.isAirBlock(x, y, z))
			return true;
		if (block == Blocks.bedrock)
			return false;
		if (block == Blocks.fire)
			return true;
		if (block == TFCBlocks.TallGrass)
			return true;
		if (block == TFCBlocks.Torch)
			return true;
		if (block == TFCBlocks.SmokeRack)
			return true;
		if (block == TFCBlocks.ToolRack)
			return true;
		if(block == TFCBlocks.Charcoal)
			return false;
		if(block == TFCBlocks.LogPile)
			return !block.isSideSolid(world, x, y, z, ForgeDirection.UP);
		if(!block.isNormalCube())
			return true;
		Material material = block.getMaterial();
		if (material == Material.water || material == Material.lava)
			return true;
		return false;
	}

	public void DropCarvedStone(World world, int x, int y, int z)
	{
		if(world.getBlock(x + 1, y, z).isOpaqueCube())
			return;
		else if(world.getBlock(x - 1, y, z).isOpaqueCube())
			return;
		else if(world.getBlock(x, y, z + 1).isOpaqueCube())
			return;
		else if(world.getBlock(x, y, z - 1).isOpaqueCube())
			return;
		else if(world.getBlock(x, y + 1, z).isOpaqueCube())
			return;
		else if(world.getBlock(x, y - 1, z).isOpaqueCube())
			return;

		dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
		world.setBlockToAir(x, y, z);
	}

	public Boolean hasNaturalSupport(World world, int x, int y, int z)
	{
		//Make sure that the block beneath the one we're checking is not a solid, if it is then return true and don't waste time here.
		if(!world.isAirBlock(x, y - 1, z))
			return true;

		if(world.getBlock(x + 1, y, z).isOpaqueCube())
		{
			if(world.getBlock(x + 1, y - 1, z).isOpaqueCube() && world.getBlock(x + 1, y - 2, z).isOpaqueCube())
				return true;
		}

		if(world.getBlock(x - 1, y, z).isOpaqueCube())
		{
			if(world.getBlock(x - 1, y - 1, z).isOpaqueCube() && world.getBlock(x - 1, y - 2, z).isOpaqueCube())
				return true;
		}

		if(world.getBlock(x, y, z + 1).isOpaqueCube())
		{
			if(world.getBlock(x, y - 1, z + 1).isOpaqueCube() && world.getBlock(x, y - 2, z + 1).isOpaqueCube())
				return true;
		}

		if(world.getBlock(x, y, z - 1).isOpaqueCube())
		{
			if(world.getBlock(x, y - 1, z - 1).isOpaqueCube() && world.getBlock(x, y - 2, z - 1).isOpaqueCube())
				return true;
		}

		//Diagonals
		if(world.getBlock(x + 1, y, z - 1).isOpaqueCube())
		{
			if(world.getBlock(x + 1, y - 1, z - 1).isOpaqueCube())
				return true;
		}

		if(world.getBlock(x - 1, y, z - 1).isOpaqueCube())
		{
			if(world.getBlock(x - 1, y - 1, z - 1).isOpaqueCube())
				return true;
		}

		if(world.getBlock(x + 1, y, z + 1).isOpaqueCube())
		{
			if(world.getBlock(x + 1, y - 1, z + 1).isOpaqueCube())
				return true;
		}

		if(world.getBlock(x - 1, y, z + 1).isOpaqueCube())
		{
			if(world.getBlock(x - 1, y - 1, z + 1).isOpaqueCube())
				return true;
		}

		return false;
	}

	public static Boolean isNearSupport(World world, int i, int j, int k, int range, float collapseChance)
	{
		for(int y = -1; y <= 1; y++)
		{
			for(int x = -range; x <= range; x++)
			{
				for(int z = -range; z <= range; z++)
				{
					if(x == 0 && z == 0 && TFC_Core.isVertSupport(world.getBlock(i + x, j + y, k + z)))
					{
						return true;
					}
					if(TFC_Core.isHorizSupport(world.getBlock(i + x, j + y, k + z)))
					{
						if(world.rand.nextFloat() < collapseChance / 100f)
							world.setBlockToAir(i + x, j + y, k + z);
						else return true;
					}
				}
			}
		}
		return false;
	}

	public Boolean isUnderLoad(World world, int i, int j, int k)
	{
		for(int x = 1; x <= TFCOptions.minimumRockLoad; x++)
		{
			if(!world.getBlock(i, j + x, k).isOpaqueCube())
				return false;
		}
		return true;
	}

	public Boolean tryToFall(World world, int i, int j, int k, float collapseChance)
	{
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		int[] drop = getDropBlock(world, i, j, k);
		Block fallingBlock = Block.getBlockById(drop[0]);
		int fallingBlockMeta = drop[1];

		if(world.getBlock(xCoord, yCoord, zCoord) == Blocks.bedrock || world.getBlock(xCoord, yCoord, zCoord) == fallingBlock)
			return false;

		if (canFallBelow(world, xCoord, yCoord - 1, zCoord)  && !isNearSupport(world, i, j, k, 4, collapseChance)  && isUnderLoad(world, i, j, k))
		{
			if (!world.isRemote && fallingBlock != Blocks.air)
			{
				if(fallingBlock != null)
				{
					EntityFallingBlockTFC ent = new EntityFallingBlockTFC(world, (double)(i + 0.5F), (double)(j + 0.5F), (double)(k + 0.5F), fallingBlock, fallingBlockMeta+8);
					ent.aliveTimer/*fallTime*/ = -5000;
					world.spawnEntityInWorld(ent);
					Random R = new Random(i*j+k);
					if(R.nextInt(100) > 90)
						world.playSoundAtEntity(ent, TFC_Sounds.FALLININGROCKLONG, 1.0F, 0.8F + (R.nextFloat()/2));
				}

				world.setBlockToAir(i, j, k);

				if(world.getBlock(i, j-1, k) == TFCBlocks.stoneSlabs && ((TEPartial)world.getTileEntity(i, j-1, k)).blockType == this && 
						((TEPartial)world.getTileEntity(i, j-1, k)).MetaID == fallingBlockMeta)
				{
					world.setBlockToAir(i, j-1, k);

					if(world.getBlock(i, j-2, k) == TFCBlocks.stoneSlabs && ((TEPartial)world.getTileEntity(i, j-2, k)).blockType == this && 
							((TEPartial)world.getTileEntity(i, j-2, k)).MetaID == fallingBlockMeta)
					{
						world.setBlockToAir(i, j-2, k);

						if(world.getBlock(i, j-3, k) == TFCBlocks.stoneSlabs && ((TEPartial)world.getTileEntity(i, j-3, k)).blockType == this && 
								((TEPartial)world.getTileEntity(i, j-3, k)).MetaID == fallingBlockMeta)
							world.setBlockToAir(i, j-3, k);
					}
				}

				return true;
			}
		}
		return false;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		float seismicModifier = 0.2f;
		float softModifier = 0.1f;
		TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(x, z);
		int finalCollapseRatio = TFCOptions.initialCollapseRatio > 0 ? TFCOptions.initialCollapseRatio : 10; //Set to default if invalid value is entered in config.

		//Make sure that the player gets exhausted from harvesting this block since we override the vanilla method
		if(entityplayer != null)
		{
			entityplayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
			entityplayer.addExhaustion(0.075F);
		}

		//If we are in a soft sedimentary rock layer then we increase the chance of a collapse by 10%
		if(this == TFCBlocks.StoneSed)
			finalCollapseRatio -= finalCollapseRatio * softModifier;

		//We do a scan for supports. if we find one close by then we dont collapse.
		/*for(int scanY = -2; scanY < 2; scanY++)
		{
			for(int scanX = -5; scanX < 6; scanX++)
			{
				for(int scanZ = -5; scanZ < 6; scanZ++)
				{
					Block b = world.getBlock(x+scanX, y+scanY, z+scanZ);
					if(b == TFCBlocks.WoodSupportH || b == TFCBlocks.WoodSupportH2)
						return;
				}
			}
		}*/

		//First we check the rng to see if a collapse is going to occur
		if (TFCOptions.enableCaveIns && world.rand.nextInt(finalCollapseRatio) == 0)
		{
			//Now we look for a suitable block nearby to act as the epicenter
			int counter = 0;
			while(counter < 100)
			{
				int scanX = -4 + world.rand.nextInt(8);
				int scanY = -2 + world.rand.nextInt(4);
				int scanZ = -4 + world.rand.nextInt(8);
				if(world.getBlock(x+scanX, y+scanY, z+scanZ) instanceof BlockCollapsable && 
						((BlockCollapsable)world.getBlock(x+scanX, y+scanY, z+scanZ)).tryToFall(world, x+scanX, y+scanY, z+scanZ, 0))
				{
					triggerCollapse(world, entityplayer, x+scanX, y+scanY, z+scanZ, meta);
					return;
				}
				counter++;
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
		int height = 4;
		int range = 5 + world.rand.nextInt(30);
		for(int y = -4; y <= 1; y++)
		{
			for(int x = -range; x <= range; x++)
			{
				for(int z = -range; z <= range; z++)
				{
					double distSqrd = Math.pow(i-(i+x),2) + Math.pow(j-(j+y),2) + Math.pow(k-(k+z),2);

					if(world.rand.nextInt(100) < TFCOptions.propogateCollapseChance && distSqrd < 1225)
					{
						if(world.getBlock(i+x, j+y, k+z) instanceof BlockCollapsable && 
								((BlockCollapsable)world.getBlock(i+x, j+y, k+z)).tryToFall(world, i+x, j+y, k+z, 1))
						{
							int done = 0;
							while(done < height)
							{
								done++;
								if(world.getBlock(i+x, j+y, k+z) instanceof BlockCollapsable && world.rand.nextInt(100) < TFCOptions.propogateCollapseChance) {
									((BlockCollapsable)world.getBlock(i+x, j+y, k+z)).tryToFall(world, i+x, j+y+done, k+z, 1);
								} else {
									done = height;
								}
							}
						}
					}
				}
			}
		}
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
				if((world.isAirBlock(worldX, worldY, worldZ)) /*&& localY < 4*/)
					checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 1, localZ + 0), block.collapseChance - incrementChance*4, TFCDirection.UP));
				else if(world.getBlock(worldX, worldY, worldZ) instanceof BlockCollapsable && 
						((BlockCollapsable)world.getBlock(worldX, worldY, worldZ)).tryToFall(world, worldX, worldY, worldZ, block.collapseChance))
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
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex)
	{
		harvestBlock(world, null, x, y, z, world.getBlockMetadata(x, y, z));
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess w, int x, int y, int z)
	{
		return false;
	}
}

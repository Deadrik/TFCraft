package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;

public class BlockSmoke extends BlockTerra
{
	public BlockSmoke()
	{
		super(new Material(MapColor.snowColor).setReplaceable());
		this.setCreativeTab(null);
		this.setBlockBounds(0f, 0, 0f, 1f, 1, 1f);
		this.setTickRandomly(true);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "Smoke");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
	{
		return 0x666666;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side)
	{
		return access.getBlock(x, y, z) != this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 0;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) 
	{
		verify(world, x, y, z);
	}

	/*@Override
	public int getRenderType()
	{
		return TFCBlocks.smokeRenderId;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}*/

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public boolean isCollidable()
	{
		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) 
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			boolean isOdd = (meta & 1) > 0;
			if(meta < 15)
			{
				if((isSurrounded(world, x, y+1, z) || world.rand.nextInt(5) != 0 || meta < 8) && addSmoke(world, x, y+1, z, meta))
				{
					if(world.rand.nextInt((16-meta)/2) != 0) addSmoke(world, x, y+1, z+1, meta);
					if(world.rand.nextInt((16-meta)/2) != 0) addSmoke(world, x, y+1, z-1, meta);
					if(!isOdd)
					{
						if(world.rand.nextBoolean() && addSmoke(world, x+1, y+1, z, meta))
						{
							if(world.rand.nextInt((16-meta)/2) != 0) addSmoke(world, x+1, y+1, z+1, meta);
							if(world.rand.nextInt((16-meta)/2) != 0) addSmoke(world, x+1, y+1, z-1, meta);
						}
					}
					else
					{
						if(world.rand.nextBoolean() && addSmoke(world, x-1, y+1, z, meta))
						{
							if(world.rand.nextInt((16-meta)/2) != 0) addSmoke(world, x-1, y+1, z+1, meta);
							if(world.rand.nextInt((16-meta)/2) != 0) addSmoke(world, x-1, y+1, z-1, meta);
						}
					}
				}

			}
		}
	}

	private boolean isSurrounded(World world, int x, int y, int z)
	{
		return world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) && world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) &&
				world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) && world.isSideSolid(x + 1, y, z, ForgeDirection.WEST);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborType) 
	{
		verify(world, x, y, z);
	}

	private void verify(World world, int x, int y, int z) {
		if(!world.isRemote)
		{
			int thisMeta = world.getBlockMetadata(x, y, z);
			if(thisMeta == 0)
				return;
			boolean hasBase = false;
			if(world.getBlock(x, y-1, z) == this && world.getBlockMetadata(x, y-1, z) == thisMeta-1)
			{
				hasBase = true;
			}
			else if(world.getBlock(x, y-1, z-1) == this && world.getBlockMetadata(x, y-1, z-1) == thisMeta-1)
			{
				hasBase = true;
			}
			else if(world.getBlock(x, y-1, z+1) == this && world.getBlockMetadata(x, y-1, z+1) == thisMeta-1)
			{
				hasBase = true;
			}
			else if(world.getBlock(x-1, y-1, z) == this && world.getBlockMetadata(x-1, y-1, z) == thisMeta-1)
			{
				hasBase = true;
			}
			else if(world.getBlock(x-1, y-1, z-1) == this && world.getBlockMetadata(x-1, y-1, z-1) == thisMeta-1)
			{
				hasBase = true;
			}
			else if(world.getBlock(x-1, y-1, z+1) == this && world.getBlockMetadata(x-1, y-1, z+1) == thisMeta-1)
			{
				hasBase = true;
			}
			else if(world.getBlock(x+1, y-1, z) == this && world.getBlockMetadata(x+1, y-1, z) == thisMeta-1)
			{
				hasBase = true;
			}
			else if(world.getBlock(x+1, y-1, z-1) == this && world.getBlockMetadata(x+1, y-1, z-1) == thisMeta-1)
			{
				hasBase = true;
			}
			else if(world.getBlock(x+1, y-1, z+1) == this && world.getBlockMetadata(x+1, y-1, z+1) == thisMeta-1)
			{
				hasBase = true;
			}


			if(!hasBase)
				world.setBlockToAir(x, y, z);
		}
	}



	private boolean addSmoke(World world, int x, int y, int z, int meta)
	{
		if(world.isAirBlock(x, y, z))
		{
			return world.setBlock(x, y, z, this, meta+1, 0x2);
		}
		return false;
	}
}

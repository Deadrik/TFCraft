package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockSlab extends BlockPartial
{
	public BlockSlab()
	{
		super(Material.rock);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.slabRenderId;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
	}

	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity)
	{
		TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
		if(8 - (getTopChiselLevel(te.extraData) + getBottomChiselLevel(te.extraData)) < 3)
		{
			if (8 - (getSouthChiselLevel(te.extraData) + getNorthChiselLevel(te.extraData)) < 3 || 
					8 - (getEastChiselLevel(te.extraData) + getWestChiselLevel(te.extraData)) < 3)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
		if(te != null)
			return Block.getBlockById(te.typeID).getBlockHardness(world, x, y, z);
		return this.blockHardness;
	}

	public static int getTopChiselLevel(long data)
	{
		return (int) ((data >> 16) & 0xf);
	}

	public static int getBottomChiselLevel(long data)
	{
		return (int) ((data >> 4) & 0xf);
	}

	public static int getEastChiselLevel(long data)
	{
		return (int) ((data >> 12) & 0xf);
	}

	public static int getWestChiselLevel(long data)
	{
		return (int) ((data) & 0xf);
	}

	public static int getNorthChiselLevel(long data)
	{
		return (int) ((data >> 8) & 0xf);
	}

	public static int getSouthChiselLevel(long data)
	{
		return (int) ((data >> 20) & 0xf);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		TEPartial te = (TEPartial) world.getTileEntity(i, j, k);
		//int md = world.getBlockMetadata(i, j, k);
		if(te != null)
		{
			short type = te.typeID;

			if (type <= 0) {
				return super.getCollisionBoundingBoxFromPool(world, i, j, k);
			}

			byte extraX = (byte) ((te.extraData) & 0xf);
			byte extraY = (byte) ((te.extraData >> 4) & 0xf);
			byte extraZ = (byte) ((te.extraData >> 8) & 0xf);
			byte extraX2 = (byte) ((te.extraData >> 12) & 0xf);
			byte extraY2 = (byte) ((te.extraData >> 16) & 0xf);
			byte extraZ2 = (byte) ((te.extraData >> 20) & 0xf);

			float div = 1f / 8;

			return AxisAlignedBB.getBoundingBox(i + (div * extraX), j + (div * extraY),  k + (div * extraZ), i + (1 - (div * extraX2)), j + (1 - (div * extraY2)), k + (1 - (div * extraZ2)));
		}
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return getCollisionBoundingBoxFromPool(world,i,j,k);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int i, int j, int k) 
	{
		TEPartial te = (TEPartial) bAccess.getTileEntity(i, j, k);

		long extraX = (te.extraData) & 0xf;
		long extraY = (te.extraData >> 4) & 0xf;
		long extraZ = (te.extraData >> 8) & 0xf;
		long extraX2 = (te.extraData >> 12) & 0xf;
		long extraY2 = (te.extraData >> 16) & 0xf;
		long extraZ2 = (te.extraData >> 20) & 0xf;

		float div = 1f / 8;

		setBlockBounds(0.0F+ (div * extraX), 0.0F+ (div * extraY), 0.0F+ (div * extraZ), 1.0F-(div * extraX2), 1-(div * extraY2), 1.0F-(div * extraZ2));
	}

	public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
	{
		// Do Nothing
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		TEPartial te = null;

		if (world.getTileEntity(x, y, z) instanceof TEPartial)
			te = (TEPartial) world.getTileEntity(x, y, z);

		if(te == null)
			return false;

		long data = te.extraData;

		switch(side)
		{
		case DOWN/*DOWN*/:
			return getBottomChiselLevel(data) == 0 && getNorthChiselLevel(data) == 0 && 
			getSouthChiselLevel(data) == 0 && getEastChiselLevel(data) == 0 && getWestChiselLevel(data) == 0;
		case UP/*UP*/:
			return getTopChiselLevel(data) == 0 && getNorthChiselLevel(data) == 0 && 
			getSouthChiselLevel(data) == 0 && getEastChiselLevel(data) == 0 && getWestChiselLevel(data) == 0;
		case NORTH/*NORTH*/:
			return getNorthChiselLevel(data) == 0 && getEastChiselLevel(data) == 0 && getWestChiselLevel(data) == 0 &&
			getTopChiselLevel(data) == 0 && getBottomChiselLevel(data) == 0;
		case SOUTH/*SOUTH*/:
			return getSouthChiselLevel(data) == 0 && getEastChiselLevel(data) == 0 && getWestChiselLevel(data) == 0 &&
			getTopChiselLevel(data) == 0 && getBottomChiselLevel(data) == 0;
		case EAST/*EAST*/:
			return getEastChiselLevel(data) == 0 && getNorthChiselLevel(data) == 0 && getSouthChiselLevel(data) == 0 &&
			getTopChiselLevel(data) == 0 && getBottomChiselLevel(data) == 0;
		case WEST/*WEST*/:
			return getWestChiselLevel(data) == 0 && getNorthChiselLevel(data) == 0 && getSouthChiselLevel(data) == 0 &&
			getTopChiselLevel(data) == 0 && getBottomChiselLevel(data) == 0;
		default: 
			return false;
		}
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}
}

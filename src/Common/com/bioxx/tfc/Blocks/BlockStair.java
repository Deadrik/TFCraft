package com.bioxx.tfc.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Core.CollisionRayTraceStandard;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Interfaces.ICustomCollision;

public class BlockStair extends BlockPartial implements ICustomCollision
{

	public BlockStair(Material m)
	{
		super(m);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.stairRenderId;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public boolean canDropFromExplosion(Explosion ex)
	{
		return false;
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int i, int j, int k)
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
		long rvmeta = te.extraData;

		// Left byte bottom, Right byte top
		// Bits are SE, NW, NE, SW - 1 means square is missing
		switch (side)
		{
		case DOWN:
			return (rvmeta & 240) == 0; // 0b1111_0000
		case UP:
			return (rvmeta & 15) == 0; // 0b0000_1111
		case NORTH:
			return (rvmeta & 102) == 0; // 0b0110_0110
		case SOUTH:
			return (rvmeta & 153) == 0; // 0b1001_1001
		case EAST:
			return (rvmeta & 170) == 0; // 0b1010_1010
		case WEST:
			return (rvmeta & 85) == 0; // 0b0101_0101
		default:
			return false;
		}
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, List list)
	{
		TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
		long rvmeta = te.extraData;

		if ((rvmeta & 1) == 0)
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F)});
		}
		if ((rvmeta & 2) == 0)
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F)});
		}
		if ((rvmeta & 4) == 0)
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F)});
		}
		if ((rvmeta & 8) == 0)
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F)});
		}
		if ((rvmeta & 16) == 0)
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 1.0F)});
		}
		if ((rvmeta & 32) == 0)
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F)});
		}
		if ((rvmeta & 64) == 0)
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F)});
		}
		if ((rvmeta & 128) == 0)
		{
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.5F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F)});
		}
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity)
	{
		ArrayList<Object[]> l = new ArrayList<Object[]>();
		addCollisionBoxesToList(world,i,j,k,l);
		for(Object[] o : l)
		{
			AxisAlignedBB a = ((AxisAlignedBB)o[0]).getOffsetBoundingBox(i, j, k);
			if (a != null && aabb.intersectsWith(a))
				list.add(a);
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		return CollisionRayTraceStandard.collisionRayTrace(this, world, x, y, z, player, view);
	}
}

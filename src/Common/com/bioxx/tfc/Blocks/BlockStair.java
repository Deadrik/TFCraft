package com.bioxx.tfc.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;

public class BlockStair extends BlockPartial
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
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB AABB, List list, Entity entity)
	{
		int var7 = world.getBlockMetadata(x, y, z);
		int var8 = var7 & 3;
		float var9 = 0.0F;
		float var10 = 0.5F;
		float var11 = 0.5F;
		float var12 = 1.0F;

		if ((var7 & 4) != 0)
		{
			var9 = 0.5F;
			var10 = 1.0F;
			var11 = 0.0F;
			var12 = 0.5F;
		}

		this.setBlockBounds(0.0F, var9, 0.0F, 1.0F, var10, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);

		if (var8 == 0)
		{
			this.setBlockBounds(0.5F, var11, 0.0F, 1.0F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (var8 == 1)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 0.5F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (var8 == 2)
		{
			this.setBlockBounds(0.0F, var11, 0.5F, 1.0F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (var8 == 3)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 1.0F, var12, 0.5F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
	{
		boolean solidSides[][] = {
				{ true, false, false, false, false, true },
				{ true, false, false, false, true, false },
				{ true, false, false, true, false, false },
				{ true, false, true, false, false, false },
				{ false, true, false, false, false, true },
				{ false, true, false, false, true, false },
				{ false, true, false, true, false, false },
				{ false, true, true, false, false, false }
		};

		int meta = world.getBlockMetadata(x, y, z);
		return solidSides[meta][side];
	}
}

package com.bioxx.tfc.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
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
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemHammer;

public class BlockStair extends BlockPartial
{

	public BlockStair(Material m)
	{
		super(m);
		this.setCreativeTab(TFCTabs.TFCBuilding);
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
		int meta = world.getBlockMetadata(x, y, z);
		int rvmeta = meta & 7;
		float var9 = 0.0F;
		float var10 = 0.5F;
		float var11 = 0.5F;
		float var12 = 1.0F;

		if ((meta & 8) != 0)
		{
			var9 = 0.5F;
			var10 = 1.0F;
			var11 = 0.0F;
			var12 = 0.5F;
		}

		this.setBlockBounds(0.0F, var9, 0.0F, 1.0F, var10, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);

		if (rvmeta == 0 || rvmeta == 4)
		{
			this.setBlockBounds(0.5F, var11, 0.0F, 1.0F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 1 || rvmeta == 5)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 0.5F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 2 || rvmeta == 6)
		{
			this.setBlockBounds(0.0F, var11, 0.5F, 1.0F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 3 || rvmeta == 7)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 1.0F, var12, 0.5F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		
		if (rvmeta == 4)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 0.5F, var12, 0.5F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 5)
		{
			this.setBlockBounds(0.5F, var11, 0.5F, 1.0F, var12, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 6)
		{
			this.setBlockBounds(0.5F, var11, 0.0F, 1.0F, var12, 0.5F);
			super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
		}
		else if (rvmeta == 7)
		{
			this.setBlockBounds(0.0F, var11, 0.5F, 0.5F, var12, 1.0F);
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

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)  
	{
		boolean hasHammer = false;
		for(int i = 0; i < 9;i++)
		{
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer) {
				hasHammer = true;
			}
		}
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && hasHammer && !world.isRemote)
		{

			int mode = 0;
			if(!world.isRemote)
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);

				if(pi!=null) {
					mode = pi.ChiselMode;
				}
			} else {
				mode = PlayerManagerTFC.getInstance().getClientPlayer().ChiselMode;
			}

			if(mode == 1)
			{
				ItemChisel.ChangeStairs(world, x, y, z, hitX, hitY, hitZ);
				entityplayer.getCurrentEquippedItem().damageItem(1, entityplayer);
				return true;
			}
		}
		return false;
	}
}

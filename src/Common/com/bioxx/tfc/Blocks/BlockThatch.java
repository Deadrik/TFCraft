package com.bioxx.tfc.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.Terrain.BlockCobble;
import com.bioxx.tfc.Blocks.Terrain.BlockSand;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;

public class BlockThatch extends BlockTerra
{
	public BlockThatch()
	{
		super(Material.grass);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
		this.setBlockBounds(0f, 0, 0f, 1f, 1, 1f);
		this.lightOpacity = 255;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Thatch");
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//Random R = new Random();
		//dropBlockAsItem(world, i, j, k, new ItemStack(idDropped(0,R,l), 1, l+13));

		//super.harvestBlock(world, entityplayer, i, j, k, l);
		dropBlockAsItem(world, i, j, k, new ItemStack(this, 1, l));
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			Block b = world.getBlock(x, y+1, z);
			if(TFC_Core.isSoilOrGravel(b) || b instanceof BlockCobble || b instanceof BlockSand)
			{
				TFC_Core.setBlockToAirWithDrops(world, x, y, z);
			}
		}
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		entity.motionX *= 0.1D;
		entity.motionZ *= 0.1D;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		Block block = world.getBlock(x, y, z);
		if (side == 0 && this.minY > 0.0D)
			return true;
		else if (side == 1 && this.maxY < 1.0D)
			return true;
		else if (side == 2 && this.minZ > 0.0D)
			return true;
		else if (side == 3 && this.maxZ < 1.0D)
			return true;
		else if (side == 4 && this.minX > 0.0D)
			return true;
		else if (side == 5 && this.maxX < 1.0D)
			return true;
		else
			return !block.isOpaqueCube();
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return canBlockStay(world, x, y, z) && super.canPlaceBlockAt(world, x, y, z);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
}

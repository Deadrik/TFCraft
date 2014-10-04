package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockThatch extends BlockTerra
{
	private final Random random = new Random();

	public BlockThatch()
	{
		super(Material.grass);
		this.setCreativeTab(TFCTabs.TFCBuilding);
		this.setBlockBounds(0f, 0, 0f, 1f, 1, 1f);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "plants/Thatch");
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
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		entity.motionX *= 0.8D;
		entity.motionZ *= 0.8D;
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
		return side == 0 && this.minY > 0.0D ? true : (side == 1 && this.maxY < 1.0D ? true : (side == 2 && this.minZ > 0.0D ? true : (side == 3 && this.maxZ < 1.0D ? true : (side == 4 && this.minX > 0.0D ? true : (side == 5 && this.maxX < 1.0D ? true : !world.getBlock(x, y, z).isOpaqueCube())))));
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return canBlockStay(world, x, y, z) && super.canPlaceBlockAt(world, x, y, z);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		Block id = world.getBlock(x, y - 1, z);
		return y > 0 && y < 256 ? !TFC_Core.isGround(id) && id != this : false;
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

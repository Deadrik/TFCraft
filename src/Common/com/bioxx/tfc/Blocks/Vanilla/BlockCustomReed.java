package com.bioxx.tfc.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import net.minecraftforge.common.IPlantable;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCItems;

public class BlockCustomReed extends BlockReed implements IPlantable
{
	public BlockCustomReed()
	{
		super();
		float var3 = 0.375F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
		this.setTickRandomly(true);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		Block var5 = par1World.getBlock(par2, par3 - 1, par4);
		boolean correctSoil = TFC_Core.isSoil(var5) || TFC_Core.isSand(var5) || TFC_Core.isFarmland(var5);
		return var5 == this ? true : !correctSoil ? false : par1World.getBlock(par2 - 1, par3 - 1, par4).getMaterial() == Material.water ? true : par1World.getBlock(par2 + 1, par3 - 1, par4).getMaterial() == Material.water ? true : par1World.getBlock(par2, par3 - 1, par4 - 1).getMaterial() == Material.water ? true : par1World.getBlock(par2, par3 - 1, par4 + 1).getMaterial() == Material.water;
	}

	@Override
	public Item getItemDropped(int par1, Random par2, int par3)
	{
		return TFCItems.reeds;
	}

	@Override
	public Item getItem(World world, int x, int y, int z)
	{
		return TFCItems.reeds;
	}
}

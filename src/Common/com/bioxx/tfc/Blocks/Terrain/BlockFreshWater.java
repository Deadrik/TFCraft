package com.bioxx.tfc.Blocks.Terrain;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLiquid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFreshWater extends BlockCustomLiquid
{
	public BlockFreshWater(Fluid fluid)
	{
		super(fluid, Material.water);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (this.blockMaterial != Material.water)
			return 16777215;
		else
			return TerraFirmaCraft.proxy.waterColorMultiplier(par1IBlockAccess, par2, par3, par4);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int l)
	{
		//TODO break a frozen block back into fresh water flowing block
		super.breakBlock(world, i, j, k, block, l);
	}
}

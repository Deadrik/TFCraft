package com.bioxx.tfc.Blocks.Vanilla;

import net.minecraft.block.BlockVine;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.ColorizerFoliageTFC;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomVine extends BlockVine implements IShearable
{
	public BlockCustomVine()
	{
		super();
	}

	@Override
	public int getBlockColor()
	{
		return ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@Override
	public int getRenderColor(int par1)
	{
		return par1 == 0 ? 16777215 : ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		return TerraFirmaCraft.proxy.foliageColorMultiplier(bAccess, x, y, z);
	}
}

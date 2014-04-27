package com.bioxx.tfc.Blocks.Vanilla;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.ColorizerFoliageTFC;

import net.minecraft.block.BlockVine;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;

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
        return ColorizerFoliageTFC.getFoliageColorBasic();
    }

    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return TerraFirmaCraft.proxy.foliageColorMultiplier(par1IBlockAccess, par2, par3, par4);
    }
}

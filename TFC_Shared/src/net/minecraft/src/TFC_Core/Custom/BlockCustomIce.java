package net.minecraft.src.TFC_Core.Custom;

import java.util.Random;

import net.minecraft.src.*;

public class BlockCustomIce extends BlockIce
{
    public BlockCustomIce(int par1, int par2)
    {
        super(par1, par2);
        this.slipperiness = 0.98F;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11 - Block.lightOpacity[this.blockID])
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, Block.waterStill.blockID);
        }
        
        
    }
}

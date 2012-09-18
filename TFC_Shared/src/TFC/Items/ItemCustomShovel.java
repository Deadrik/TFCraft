package TFC.Items;

import java.util.List;

import TFC.Core.TFC_Settings;
import net.minecraft.src.*;

public class ItemCustomShovel extends ItemTerraTool
{
    /** an array of the blocks this spade is effective against */
    private static Block[] blocksEffectiveAgainst = new Block[] {Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium, 
        TFCBlocks.Dirt, TFCBlocks.Dirt2, TFCBlocks.Grass, TFCBlocks.Grass2, TFCBlocks.ClayGrass, TFCBlocks.ClayGrass2, TFCBlocks.PeatGrass, TFCBlocks.Peat, 
        TFCBlocks.Clay, TFCBlocks.Clay2};

    public ItemCustomShovel(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, 1, par2EnumToolMaterial, blocksEffectiveAgainst);
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block == Block.snow ? true : par1Block == Block.blockSnow;
    }

}
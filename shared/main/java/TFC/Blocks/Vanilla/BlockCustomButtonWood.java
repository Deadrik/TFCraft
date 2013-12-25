package TFC.Blocks.Vanilla;

import TFC.TFCBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockButtonWood;
import net.minecraft.util.Icon;

public class BlockCustomButtonWood extends BlockButtonWood
{
    public BlockCustomButtonWood(int par1)
    {
        super(par1);
    }
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return TFCBlocks.Planks.getBlockTextureFromSide(0);
    }
}

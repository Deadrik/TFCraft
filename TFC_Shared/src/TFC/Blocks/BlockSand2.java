package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import TFC.Entities.EntityFallingDirt;
import TFC.Entities.EntityFallingStone;

import net.minecraft.src.*;

public class BlockSand2 extends TFC.Blocks.BlockSand
{
    public BlockSand2(int i, int j)
    {
        super(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < 7; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }


}

package TFC.Blocks;

import java.util.Random;

import TFC.*;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.TFC_Settings;

import net.minecraft.src.ItemStack;

public class BlockPeatGrass extends BlockGrass
{
	
    public BlockPeatGrass(int par1, int par2)
    {
        super(par1, par2);

    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return TFCBlocks.Peat.blockID;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
    
    public void addCreativeItems(java.util.ArrayList list)
	{
	    list.add(new ItemStack(this,1,0));
	}
}

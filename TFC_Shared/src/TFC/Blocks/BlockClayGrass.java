package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import TFC.Core.ColorizerGrassTFC;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Settings;

import net.minecraft.src.*;

public class BlockClayGrass extends BlockGrass
{

    public BlockClayGrass(int par1, int par2)
    {
        super(par1, par2);
    }


    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.clay.shiftedIndex;
    }

}

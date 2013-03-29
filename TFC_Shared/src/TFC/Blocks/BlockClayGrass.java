package TFC.Blocks;

import java.util.Random;

import net.minecraft.item.Item;
import TFC.TFCBlocks;

public class BlockClayGrass extends BlockGrass
{

    public BlockClayGrass(int par1, int par2)
    {
        super(par1, par2);
    }

    @Override
	public int getRenderType()
	{
		return TFCBlocks.clayGrassRenderId;
	}
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.clay.itemID;
    }

}

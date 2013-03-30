package TFC.Blocks.Terrain;

import java.util.Random;

import TFC.TFCBlocks;

public class BlockPeatGrass extends BlockGrass
{
    public BlockPeatGrass(int par1)
    {
        super(par1);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public int idDropped(int par1, Random par2Random, int par3)
    {
        return TFCBlocks.Peat.blockID;
    }
    
    @Override
	public int getRenderType()
	{
		return TFCBlocks.peatGrassRenderId;
	}

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
	public int quantityDropped(Random par1Random)
    {
        return 1;
    }
}

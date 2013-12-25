package TFC.Blocks.Terrain;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;

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
	
    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(!world.blockExists(i, j-1, k))
        {
            int meta = world.getBlockMetadata(i, j, k);
            world.setBlock(i, j, k, TFCBlocks.Peat.blockID);
        }
    }
    
	@Override
    public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (world.getBlockLightValue(i, j + 1, k) < 4 && Block.lightOpacity[world.getBlockId(i, j + 1, k)] > 2)
        {
			world.setBlock(i, j, k, TFCBlocks.Peat.blockID);
        }
	}

}

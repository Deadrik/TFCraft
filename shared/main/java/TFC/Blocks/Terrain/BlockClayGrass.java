package TFC.Blocks.Terrain;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;

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
    public int damageDropped(int i) {
        return 0;
    }
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.clay.itemID;
    }

	@Override
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(4);
	}
	
    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(!world.blockExists(i, j-1, k))
        {
            int meta = world.getBlockMetadata(i, j, k);
            world.setBlock(i, j, k, TFC_Core.getTypeForClay(meta), meta, 0x2);
        }
    }
    
	@Override
    public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (world.getBlockLightValue(i, j + 1, k) < 4 && Block.lightOpacity[world.getBlockId(i, j + 1, k)] > 2)
        {
            world.setBlock(i, j, k, TFC_Core.getTypeForClay(world.getBlockMetadata(i, j, k) + textureOffset), world.getBlockMetadata(i, j, k), 0x2);
        }
	}
}

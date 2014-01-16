package TFC.Blocks.Terrain;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Blocks.Vanilla.BlockCustomFlowing;

public class BlockFreshWaterFlowing extends BlockCustomFlowing
{
	public BlockFreshWaterFlowing(int par1) 
	{
		super(par1, Material.water);
	}
	
	@Override
	public void breakBlock(World world, int i, int j, int k, int id, int l)
	{
		Material m = world.getBlockMaterial(i, j, k);
		int blockID = world.getBlockId(i,j,k);
		super.breakBlock(world, i, j, k, id, l);
		if(blockID == Block.ice.blockID){
				world.setBlockMetadataWithNotify(i,j,k,1,1);
				//world.setBlockToAir(i, j, k);
		}

	}
}

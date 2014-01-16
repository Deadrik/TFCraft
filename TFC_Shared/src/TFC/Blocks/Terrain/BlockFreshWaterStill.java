package TFC.Blocks.Terrain;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Blocks.Vanilla.BlockCustomStationary;

public class BlockFreshWaterStill extends BlockCustomStationary 
{
	public BlockFreshWaterStill(int par1) 
	{
		super(par1, Material.water);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int id, int l)
	{
		Material m = world.getBlockMaterial(i, j, k);
		int blockID = world.getBlockId(i,j,k);
		if(blockID == Block.ice.blockID){
			world.setBlockMetadataWithNotify(i,j,k,1,1);
		}
		super.breakBlock(world, i, j, k, id, l);
		
	}

}

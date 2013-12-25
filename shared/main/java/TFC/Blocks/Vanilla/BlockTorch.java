package TFC.Blocks.Vanilla;

import net.minecraft.world.World;

public class BlockTorch extends net.minecraft.block.BlockTorch
{

	public BlockTorch(int par1) 
	{
		super(par1);

	}

	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z)
	{
		return true;
	}

}

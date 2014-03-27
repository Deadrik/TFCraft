package TFC.Blocks;

import java.util.Random;

import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TileEntities.TileEntityPartial;

public class BlockStalactite extends BlockSlab
{
	public BlockStalactite()
	{
		super();
	}

	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if ((world.getBlock(i, j+1, k).isNormalCube() || world.getBlock(i, j+2, k).isNormalCube()) && ((((TileEntityPartial)world.getTileEntity(i, j, k)).extraData >> 24) & 1) == 1 && random.nextInt(80) == 0)
		{
			float f = (float)i + 0.5F;
			float f1 = (float)j-0.08f;
			float f2 = (float)k + 0.5F;
			float f3 = 0.52F;

			float f4 = random.nextFloat() * 0.6F;
			float f5 = random.nextFloat() * -0.6F;
			float f6 = random.nextFloat() * -0.6F;
			world.spawnParticle("dripWater", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public boolean canDropFromExplosion(Explosion ex)
	{
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
	{
		return false;
	}
}

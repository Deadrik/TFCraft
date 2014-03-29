package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomTallGrass extends WorldGenerator
{
	/** Stores ID for WorldGenTallGrass */
	private Block tallGrass;
	private int tallGrassMetadata;

	public WorldGenCustomTallGrass(Block par1, int par2)
	{
		this.tallGrass = par1;
		this.tallGrassMetadata = par2;
	}

	public boolean generate(World w, Random r, int x, int y, int z)
	{
		Block block;

		do
		{
			block = w.getBlock(x, y, z);
			if(!(w.isAirBlock(x, y, z) || block.isLeaves(w, x, y, z)))
				break;
			--y;
		} while (y > 0);

		for (int var7 = 0; var7 < 128; ++var7)
		{
			int x1 = x + r.nextInt(8) - r.nextInt(8);
			int y1 = y + r.nextInt(4) - r.nextInt(4);
			int z1 = z + r.nextInt(8) - r.nextInt(8);

			if (w.isAirBlock(x1, y1, z1) && this.tallGrass.canBlockStay(w, x1, y1, z1))
				w.setBlock(x1, y1, z1, this.tallGrass, this.tallGrassMetadata, 0x2);
		}
		return true;
	}
}

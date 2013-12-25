package TFC.WorldGen;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class ChunkTFC extends Chunk
{
	public ChunkTFC(World world, short[] ids, byte[] metadata, int chunkX, int chunkZ)
	{
		super(world, chunkX, chunkZ);
		int height = ids.length / 256;
		ExtendedBlockStorage[] EBS = getBlockStorageArray();
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				for (int y = 0; y < height; ++y)
				{
					int index = (x << 12 | z << 8 | y);
					int id    = ids[index];
					int meta  = metadata[index];

					if (id != 0)
					{
						int chunkY = (y >> 4);

						if (EBS[chunkY] == null)
						{
							EBS[chunkY] = new ExtendedBlockStorage(chunkY << 4, !world.provider.hasNoSky);
						}

						EBS[chunkY].setExtBlockID(x, y & 15, z, id);
						EBS[chunkY].setExtBlockMetadata(x, y & 15, z, meta);
					}
				}
			}
		}
		setStorageArrays(EBS);
	}
}

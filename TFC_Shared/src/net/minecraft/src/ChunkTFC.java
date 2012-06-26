package net.minecraft.src;

public class ChunkTFC extends Chunk
{

    public ChunkTFC(World world, byte[] ids, byte[] metadata, byte[] ids2, byte[] metadata2, int chunkX, int chunkZ)
    {
        super(world, chunkX, chunkZ);
        int height = ids.length / 256;

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                for (int y = 0; y < height; ++y)
                {
                    int index = x << 11 | z << 7 | y;
                    int id    = ids[index] & 0xFF;
                    int meta  = metadata[index] & 0x0F;

                    if (id != 0)
                    {
                        int chunkY = (y >> 4);
                        if (getBlockStorageArray()[chunkY] == null)
                        {
                            getBlockStorageArray()[chunkY] = new ExtendedBlockStorage(chunkY << 4);
                        }

                        getBlockStorageArray()[chunkY].setExtBlockID(x, y & 15, z, id);
                        getBlockStorageArray()[chunkY].setExtBlockMetadata(x, y & 15, z, meta);
                    }
                }
            }
        }

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                for (int y = 0; y < height; ++y)
                {
                    int index = x << 11 | z << 7 | y;
                    int id    = ids2[index] & 0xFF;
                    int meta  = metadata2[index] & 0x0F;

                    if (id != 0)
                    {
                        int chunkY = 8+(y >> 4);

                        if (getBlockStorageArray()[chunkY] == null)
                        {
                            int ind = (chunkY << 4);
                            getBlockStorageArray()[chunkY] = new ExtendedBlockStorage(ind);
                        }

                        getBlockStorageArray()[chunkY].setExtBlockID(x, y & 15, z, id);
                        getBlockStorageArray()[chunkY].setExtBlockMetadata(x, y & 15, z, meta);
                    }
                }
            }
        }
    }

    public ChunkTFC(World world, byte[] ids, byte[] metadata, int chunkX, int chunkZ)
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
                    //int index = ((x * 16 + z) * 255 + y);
                    int index = (x << 12 | z << 8 | y);
                    int id    = ids[index] & 0xff;
                    int meta  = metadata[index] & 0x0f;

                    if (id != 0)
                    {
                        int chunkY = (y >> 4);

                        if (EBS[chunkY] == null)
                        {
                            EBS[chunkY] = new ExtendedBlockStorage(chunkY << 4);
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

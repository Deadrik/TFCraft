package TFC.WorldGen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.feature.*;

public class ChunkTFC extends Chunk
{
    public ChunkTFC(World world, int[] ids, int[] metadata, int chunkX, int chunkZ)
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

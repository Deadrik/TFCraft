package TFC.WorldGen.Generators;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

import TFC.*;
import TFC.TileEntities.TileEntityPartial;
import TFC.WorldGen.TFCBiome;
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
import net.minecraft.world.gen.feature.*;

public class WorldGenCaveDecor implements IWorldGenerator
{
	public WorldGenCaveDecor()
	{

	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;
		for (int xCoord = 0; xCoord < 16; ++xCoord)
        {
            for (int zCoord = 0; zCoord < 16; ++zCoord)
            {
                for (int y = 127; y >= 0; --y)
                {
                    int x = chunkX + xCoord;
                    int z = chunkZ + zCoord;

                    int id = world.getBlockId(x, y, z);

                    if(y > 8 && id == 0 && (world.getBlockId(x, y+1, z) == TFCBlocks.StoneIgEx.blockID || world.getBlockId(x, y+1, z) == TFCBlocks.StoneIgIn.blockID || 
                            world.getBlockId(x, y+1, z) == TFCBlocks.StoneSed.blockID || world.getBlockId(x, y+1, z) == TFCBlocks.StoneMM.blockID))
                    {
                        if(world.getBlockId(x, y-1, z) == 0 && world.getBlockId(x, y-2, z) == 0 && world.getBlockId(x, y-3, z) == 0)
                        {

                            if(rand.nextInt(25) == 0)
                            {
                                int type = rand.nextInt(4);

                                switch(type)
                                {
                                    case 0:
                                    {
                                        world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1052929 + 16777216L; 
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlockAndMetadataWithNotify(x, y-1, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 2105858 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlockAndMetadataWithNotify(x, y-2, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).extraData = 3158787 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        break;
                                    }
                                    case 1:
                                    {
                                        world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1052929 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlockAndMetadataWithNotify(x, y-1, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 3158851 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        break;
                                    }
                                    case 2:
                                    {
                                        world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 3158785 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlockAndMetadataWithNotify(x, y-1, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 4211779 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        break;
                                    }
                                    case 3:
                                    {
                                        world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 2101505 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlockAndMetadataWithNotify(x, y-1, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 4203010 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlockAndMetadataWithNotify(x, y-2, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).extraData = 5255683 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        break;
                                    }
                                    case 4:
                                    {
                                        world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 3158785 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlockAndMetadataWithNotify(x, y-2, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).extraData = 5255683 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        break;
                                    }
                                    case 5:
                                    {
                                        world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1057026 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlockAndMetadataWithNotify(x, y-1, z, TFCBlocks.stoneStalac.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 3158531L + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else if(y <128 && id == 0 && world.getBlockLightValue(x, y-1, z) < 10 && (world.getBlockId(x, y-1, z) == TFCBlocks.StoneIgEx.blockID || world.getBlockId(x, y-1, z) == TFCBlocks.StoneIgIn.blockID || 
                            world.getBlockId(x, y-1, z) == TFCBlocks.StoneSed.blockID || world.getBlockId(x, y-1, z) == TFCBlocks.StoneMM.blockID))
                    {
                        if(world.getBlockId(x, y+1, z) == 0 && world.getBlockId(x, y+2, z) == 0 && world.getBlockId(x, y+3, z) == 0)
                        {
                            if(rand.nextInt(25) == 0)
                            {
                                int type = rand.nextInt(3);

                                switch(type)
                                {
                                    case 0:
                                    {
                                        world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneSlabs.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1057026; 
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.stoneSlabs.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).extraData = 3158531;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlockAndMetadataWithNotify(x, y+2, z, TFCBlocks.stoneSlabs.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).extraData = 4207363;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        break;
                                    }
                                    case 1:
                                    {
                                        world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneSlabs.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1052930; 
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.stoneSlabs.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).extraData = 3158274;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlockAndMetadataWithNotify(x, y+2, z, TFCBlocks.stoneSlabs.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).extraData = 4407811;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        break;
                                    }
                                    case 2:
                                    {
                                        world.setBlockAndMetadataWithNotify(x, y, z, TFCBlocks.stoneSlabs.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1052929; 
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlockAndMetadataWithNotify(x, y+1, z, TFCBlocks.stoneSlabs.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).extraData = 2105858;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlockAndMetadataWithNotify(x, y+2, z, TFCBlocks.stoneSlabs.blockID, 0, 0);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).extraData = 3552003;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
	}
	
}

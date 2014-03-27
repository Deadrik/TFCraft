package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.TileEntities.TileEntityPartial;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenCaveDecor implements IWorldGenerator
{
	public WorldGenCaveDecor()
	{
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
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

					Block id = world.getBlock(x, y, z);

					if(!world.isRemote && y > 8 && id == Blocks.air && (world.getBlock(x, y+1, z) == TFCBlocks.StoneIgEx || world.getBlock(x, y+1, z) == TFCBlocks.StoneIgIn || 
							world.getBlock(x, y+1, z) == TFCBlocks.StoneSed || world.getBlock(x, y+1, z) == TFCBlocks.StoneMM))
					{
						if(world.isAirBlock(x, y-1, z) && world.isAirBlock(x, y-2, z) && world.isAirBlock(x, y-3, z))
						{
							if(rand.nextInt(25) == 0)
							{
								int type = rand.nextInt(4);

								switch(type)
								{
								case 0:
								{
									if(world.setBlock(x, y, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y, z)).extraData = 1052929 + 16777216L; 
										((TileEntityPartial)world.getTileEntity(x, y, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									if(world.setBlock(x, y-1, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y-1, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).extraData = 2105858 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									if(world.setBlock(x, y-2, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y-2, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y-2, z)).extraData = 3158787 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y-2, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y-2, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									break;
								}
								case 1:
								{

									if(world.setBlock(x, y, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y, z)).extraData = 1052929 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									if(world.setBlock(x, y-1, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y-1, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).extraData = 3158851 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									break;
								}
								case 2:
								{

									if(world.setBlock(x, y, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y, z)).extraData = 3158785 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									if(world.setBlock(x, y-1, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y-1, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).extraData = 4211779 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									break;
								}
								case 3:
								{
									if(world.setBlock(x, y, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y, z)).extraData = 2101505 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									if(world.setBlock(x, y-1, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y-1, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).extraData = 4203010 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									if(world.setBlock(x, y-2, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y-2, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y-2, z)).extraData = 5255683 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y-2, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y-2, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									break;
								}
								case 4:
								{
									if(world.setBlock(x, y, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y, z)).extraData = 3158785 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									if(world.setBlock(x, y-1, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y-1, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).extraData = 5255683 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									break;
								}
								case 5:
								{
									if(world.setBlock(x, y, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y, z)).extraData = 1057026 + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									if(world.setBlock(x, y-1, z, TFCBlocks.stoneStalac) && world.getTileEntity(x, y-1, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).extraData = 3158531L + 16777216L;
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).blockType = world.getBlock(x, y+1, z);
										((TileEntityPartial)world.getTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
									}
									break;
								}
								}
							}
						}
					}
					else if(!world.isRemote && y <128 && id == Blocks.air && (world.getBlock(x, y-1, z) == TFCBlocks.StoneIgEx || world.getBlock(x, y-1, z) == TFCBlocks.StoneIgIn || 
							world.getBlock(x, y-1, z) == TFCBlocks.StoneSed || world.getBlock(x, y-1, z) == TFCBlocks.StoneMM))
					{
						if(world.isAirBlock(x, y+1, z) && world.isAirBlock(x, y+2, z) && world.isAirBlock(x, y+3, z))
						{
							if(rand.nextInt(25) == 0)
							{
								int type = rand.nextInt(3);

								switch(type)
								{
								case 0:
								{
									if(world.setBlock(x, y, z, TFCBlocks.stoneSlabs) && world.getTileEntity(x, y, z) != null)
									{
										world.setBlock(x, y, z, TFCBlocks.stoneSlabs);
										((TileEntityPartial)world.getTileEntity(x, y, z)).extraData = 1057026; 
										((TileEntityPartial)world.getTileEntity(x, y, z)).blockType = world.getBlock(x, y-1, z);
										((TileEntityPartial)world.getTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
									}
									if(world.setBlock(x, y+1, z, TFCBlocks.stoneSlabs) && world.getTileEntity(x, y+1, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y+1, z)).extraData = 3158531;
										((TileEntityPartial)world.getTileEntity(x, y+1, z)).blockType = world.getBlock(x, y-1, z);
										((TileEntityPartial)world.getTileEntity(x, y+1, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
									}
									if(world.setBlock(x, y+2, z, TFCBlocks.stoneSlabs) && world.getTileEntity(x, y+2, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y+2, z)).extraData = 4207363;
										((TileEntityPartial)world.getTileEntity(x, y+2, z)).blockType = world.getBlock(x, y-1, z);
										((TileEntityPartial)world.getTileEntity(x, y+2, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
									}
									break;
								}
								case 1:
								{
									if(world.setBlock(x, y, z, TFCBlocks.stoneSlabs) && world.getTileEntity(x, y, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y, z)).extraData = 1052930; 
										((TileEntityPartial)world.getTileEntity(x, y, z)).blockType = world.getBlock(x, y-1, z);
										((TileEntityPartial)world.getTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
									}
									if(world.setBlock(x, y+1, z, TFCBlocks.stoneSlabs) && world.getTileEntity(x, y+1, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y+1, z)).extraData = 3158274;
										((TileEntityPartial)world.getTileEntity(x, y+1, z)).blockType = world.getBlock(x, y-1, z);
										((TileEntityPartial)world.getTileEntity(x, y+1, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
									}
									if(world.setBlock(x, y+2, z, TFCBlocks.stoneSlabs) && world.getTileEntity(x, y+2, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y+2, z)).extraData = 4407811;
										((TileEntityPartial)world.getTileEntity(x, y+2, z)).blockType = world.getBlock(x, y-1, z);
										((TileEntityPartial)world.getTileEntity(x, y+2, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
									}
									break;
								}
								case 2:
								{
									if(world.setBlock(x, y, z, TFCBlocks.stoneSlabs) && world.getTileEntity(x, y, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y, z)).extraData = 1052929; 
										((TileEntityPartial)world.getTileEntity(x, y, z)).blockType = world.getBlock(x, y-1, z);
										((TileEntityPartial)world.getTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
									}
									if(world.setBlock(x, y+1, z, TFCBlocks.stoneSlabs) && world.getTileEntity(x, y+1, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y+1, z)).extraData = 2105858;
										((TileEntityPartial)world.getTileEntity(x, y+1, z)).blockType = world.getBlock(x, y-1, z);
										((TileEntityPartial)world.getTileEntity(x, y+1, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
									}
									if(world.setBlock(x, y+2, z, TFCBlocks.stoneSlabs) && world.getTileEntity(x, y+2, z) != null)
									{
										((TileEntityPartial)world.getTileEntity(x, y+2, z)).extraData = 3552003;
										((TileEntityPartial)world.getTileEntity(x, y+2, z)).blockType = world.getBlock(x, y-1, z);
										((TileEntityPartial)world.getTileEntity(x, y+2, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
									}
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

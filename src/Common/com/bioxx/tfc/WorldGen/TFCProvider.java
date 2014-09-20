package com.bioxx.tfc.WorldGen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCProvider extends WorldProvider
{
	@Override
	protected void registerWorldChunkManager()
	{
		super.registerWorldChunkManager();
		TFC_Climate.worldPair.put(worldObj.provider.dimensionId, new WorldLayerManager(worldObj));
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new TFCChunkProviderGenerate(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled());
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		int y = worldObj.getTopSolidOrLiquidBlock(x, z)-1;
		Block b = worldObj.getBlock(x, y, z);
		return y > 144 && y < 170 && (TFC_Core.isSand(b) || TFC_Core.isGrass(b));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMoonPhase(long par1)
	{
		return (int)(par1 / TFC_Time.dayLength) % 8;
	}

	@Override
	public float getCloudHeight()
	{
		return 256.0F;
	}

	/*@Override
	public TFCBiome getBiomeGenForCoords(int x, int z)
	{
		TFCBiome biome = TFCBiome.ocean;
		try
		{
			biome = (TFCBiome) worldObj.getBiomeGenForCoordsBody(x, z);
			if(canSnowAtTemp(x, 145, z))
				biome.temperature = 0;
			else
				biome.temperature = 0.21f;
}
catch(Exception Ex)
{
	Ex.printStackTrace();
}
return biome;
}*/

	@Override
	public ChunkCoordinates getSpawnPoint()
	{
		WorldInfo info = worldObj.getWorldInfo();
		if(info.getSpawnZ() > -2999)
			return createSpawn();
		return super.getSpawnPoint();
	}

	private ChunkCoordinates createSpawn()
	{
		List biomeList = worldChunkMgr.getBiomesToSpawnIn();
		long seed = worldObj.getWorldInfo().getSeed();
		Random rand = new Random(seed);

		ChunkPosition chunkCoord = null;
		int xOffset = 0;
		int xCoord = 0;
		int yCoord = 145;
		int zCoord = 10000;
		int startingZ = 3000 + rand.nextInt(12000);

		while(chunkCoord == null)
		{
			chunkCoord = worldChunkMgr.findBiomePosition(xOffset, -startingZ, 64, biomeList, rand);
			if (chunkCoord != null)
			{
				xCoord = chunkCoord.chunkPosX;
				zCoord = chunkCoord.chunkPosZ;
			}
			else
			{
				xOffset += 64;
				//System.out.println("Unable to find spawn biome");
			}
		}

		int var9 = 0;
		while (!canCoordinateBeSpawn(xCoord, zCoord))
		{
			xCoord += rand.nextInt(16) - rand.nextInt(16);
			zCoord += rand.nextInt(16) - rand.nextInt(16);
			++var9;
			if (var9 == 1000)
				break;
		}

		WorldInfo info = worldObj.getWorldInfo();
		info.setSpawnPosition(xCoord, worldObj.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
		return new ChunkCoordinates(xCoord, worldObj.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
	}

	private boolean isNextToShoreOrIce(int x, int y, int z)
	{
		if(worldObj.getBlock(x+1, y, z) == TFCBlocks.Ice || TFC_Core.isGround(worldObj.getBlock(x+1, y, z)))
			return true;
		if(worldObj.getBlock(x-1, y, z) == TFCBlocks.Ice || TFC_Core.isGround(worldObj.getBlock(x-1, y, z)))
			return true;
		if(worldObj.getBlock(x, y, z+1) == TFCBlocks.Ice || TFC_Core.isGround(worldObj.getBlock(x, y, z+1)))
			return true;
		if(worldObj.getBlock(x, y, z-1) == TFCBlocks.Ice || TFC_Core.isGround(worldObj.getBlock(x, y, z-1)))
			return true;
		return false;
	}

	@Override
	public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
	{
		Block id = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);


		if (TFC_Core.isWater(id) && worldObj.isAirBlock(x, y+1, z))
		{
			if(!isNextToShoreOrIce(x,y,z))
				return false;
			float temp = TFC_Climate.getHeightAdjustedTemp(worldObj, x, y, z);
			if(temp <= 0)
			{
				Material mat = worldObj.getBlock(x, y, z).getMaterial();
				boolean salty = TFC_Core.isSaltWaterIncludeIce(id, meta, mat);

				if(temp <= -2)
				{
					salty = false;
				}

				if((mat == Material.water || mat == Material.ice) && !salty)
				{
					if(id == TFCBlocks.FreshWater && meta == 0/* || id == TFCBlocks.FreshWaterFlowing.blockID*/)
					{
						worldObj.setBlock(x, y, z, TFCBlocks.Ice, 1, 2);
					}
					else if(id == TFCBlocks.SaltWater && meta == 0/* || id == Block.waterMoving.blockID*/)
					{
						worldObj.setBlock(x, y, z, TFCBlocks.Ice, 0, 2);
					}
				}
				return false;//(mat == Material.water) && !salty;
			}
		}
		else if(id == TFCBlocks.Ice)
		{
			float temp = TFC_Climate.getHeightAdjustedTemp(worldObj, x, y, z);
			int chance = (int)Math.floor(Math.max(1, 6f-temp));
			if(id == TFCBlocks.Ice && worldObj.rand.nextInt(chance) == 0)
			{
				if (worldObj.getBlock(x, y + 1, z) == Blocks.snow)
				{
					int m = worldObj.getBlockMetadata(x, y + 1, z);
					if (m > 0) {
						worldObj.setBlockMetadataWithNotify(x, y + 1, z, m - 1, 2);
					} else {
						worldObj.setBlockToAir(x, y + 1, z);
					}
				}
				else
				{
					int flag = 2;
					/*BiomeGenBase b = worldObj.getBiomeGenForCoords(x, z);
					if((b == TFCBiome.ocean || b == TFCBiome.lake || b == TFCBiome.river || b == TFCBiome.river) && y == 143)
						flag = 2;*/

					if((meta & 1) == 0)
					{
						worldObj.setBlock(x, y, z, TFCBlocks.SaltWater, 0, flag);
					}
					else if((meta & 1) == 1)
					{
						worldObj.setBlock(x, y, z, TFCBlocks.FreshWater, 0, flag);
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk)
	{
		return true;
	}

	@Override
	public boolean canSnowAt(int x, int y, int z, boolean checkLight)
	{
		if(TFC_Climate.getHeightAdjustedTemp(worldObj,x, y, z) <= 0 &&
				TFCBlocks.Snow.canPlaceBlockAt(worldObj, x, y, z) &&
				worldObj.getBlock(x, y, z).getMaterial().isReplaceable())
		{
			return true;
		}
		return false;
	}

	private boolean canSnowAtTemp(int x, int y, int z)
	{
		if(TFC_Climate.getHeightAdjustedTemp(worldObj,x, y, z) <= 0)
			return true;
		return false;
	}

	@Override
	public String getDimensionName()
	{
		return "DEFAULT";
	}

}

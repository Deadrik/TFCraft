package com.bioxx.tfc.WorldGen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCProvider extends WorldProvider
{
	@Override
	protected void registerWorldChunkManager()
	{
		super.registerWorldChunkManager();
		if(worldObj.isRemote)
			TFC_Climate.worldPair.put(worldObj.provider.dimensionId+"-Client", new WorldCacheManager(worldObj));
		else
			TFC_Climate.worldPair.put(worldObj.provider.dimensionId+"-Server", new WorldCacheManager(worldObj));
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
		return y > Global.SEALEVEL && y < 170 && (TFC_Core.isSand(b) || TFC_Core.isGrass(b));
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
		int yCoord = Global.SEALEVEL+1;
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
		if(worldObj.checkChunksExist(x+1, y, z, x+1, y, z))
			if(worldObj.getBlock(x+1, y, z) == TFCBlocks.Ice || TFC_Core.isGround(worldObj.getBlock(x+1, y, z)))
				return true;
		if(worldObj.checkChunksExist(x-1, y, z, x-1, y, z))
			if(worldObj.getBlock(x-1, y, z) == TFCBlocks.Ice || TFC_Core.isGround(worldObj.getBlock(x-1, y, z)))
				return true;
		if(worldObj.checkChunksExist(x, y, z+1, x, y, z+1))
			if(worldObj.getBlock(x, y, z+1) == TFCBlocks.Ice || TFC_Core.isGround(worldObj.getBlock(x, y, z+1)))
				return true;
		if(worldObj.checkChunksExist(x, y, z-1, x, y, z-1))
			if(worldObj.getBlock(x, y, z-1) == TFCBlocks.Ice || TFC_Core.isGround(worldObj.getBlock(x, y, z-1)))
				return true;
		return false;
	}

	@Override
	public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
	{
		Block id = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		float temp = TFC_Climate.getHeightAdjustedTemp(worldObj, x, y, z);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x, z);

		if (temp <= 0 && biome != TFCBiome.DeepOcean)
		{
			if (worldObj.isAirBlock(x, y+1, z) && TFC_Core.isWater(id) && worldObj.rand.nextInt(4) == 0 && isNextToShoreOrIce(x,y,z))
			{
				Material mat = worldObj.getBlock(x, y, z).getMaterial();
				boolean salty = TFC_Core.isSaltWaterIncludeIce(id, meta, mat);

				if(temp <= -2)
					salty = false;

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
		else
		{
			if(id == TFCBlocks.Ice)
			{
				int chance = (int)Math.floor(Math.max(1, 6f-temp));
				if(id == TFCBlocks.Ice && worldObj.rand.nextInt(chance) == 0)
				{
					if (worldObj.getBlock(x, y + 1, z) == Blocks.snow)
					{
						int m = worldObj.getBlockMetadata(x, y + 1, z);
						if (m > 0)
						{
							worldObj.setBlockMetadataWithNotify(x, y + 1, z, m - 1, 2);
						}
						else
						{
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
			//worldObj.setBlock(x, y, z, TFCBlocks.Snow);
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

	/*@Override
	public void updateWeather()
	{
		if (!worldObj.isRemote)
		{
			int thunderTime = worldObj.getWorldInfo().getThunderTime();

			if (thunderTime <= 0)
			{
				if (worldObj.getWorldInfo().isThundering())
				{
					worldObj.getWorldInfo().setThunderTime(worldObj.rand.nextInt(12000) + 3600);
				}
				else
				{
					worldObj.getWorldInfo().setThunderTime(worldObj.rand.nextInt(168000) + 12000);
				}
			}
			else
			{
				--thunderTime;
				worldObj.getWorldInfo().setThunderTime(thunderTime);

				if (thunderTime <= 0)
				{
					worldObj.getWorldInfo().setThundering(!worldObj.getWorldInfo().isThundering());
				}
			}

			worldObj.prevThunderingStrength = worldObj.thunderingStrength;

			if (worldObj.getWorldInfo().isThundering())
			{
				worldObj.thunderingStrength = (float)((double)worldObj.thunderingStrength + 0.01D);
			}
			else
			{
				worldObj.thunderingStrength = (float)((double)worldObj.thunderingStrength - 0.01D);
			}

			worldObj.thunderingStrength = MathHelper.clamp_float(worldObj.thunderingStrength, 0.0F, 1.0F);
			int rainTime = worldObj.getWorldInfo().getRainTime();

			if (rainTime <= 0)
			{
				if (worldObj.getWorldInfo().isRaining())
				{
					worldObj.getWorldInfo().setRainTime(worldObj.rand.nextInt(12000) + 12000);
				}
				else
				{
					worldObj.getWorldInfo().setRainTime(worldObj.rand.nextInt(168000) + 12000);
				}
			}
			else
			{
				--rainTime;
				worldObj.getWorldInfo().setRainTime(rainTime);

				if (rainTime <= 0)
				{
					worldObj.getWorldInfo().setRaining(!worldObj.getWorldInfo().isRaining());
				}
			}

			worldObj.prevRainingStrength = worldObj.rainingStrength;

			if (worldObj.getWorldInfo().isRaining())
			{
				worldObj.rainingStrength = (float)((double)worldObj.rainingStrength + 0.01D);
			}
			else
			{
				worldObj.rainingStrength = (float)((double)worldObj.rainingStrength - 0.01D);
			}

			worldObj.rainingStrength = MathHelper.clamp_float(worldObj.rainingStrength, 0.0F, 1.0F);
		}
	}*/

}

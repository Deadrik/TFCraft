package com.bioxx.tfc.WorldGen;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class TFCProvider extends WorldProvider
{
	private int moonPhase;
	private int moonPhaseLastCalculated;
	
	@Override
	protected void registerWorldChunkManager()
	{
		/**
		 * ChunkEventHandler.onLoadWorld gets called after the NEW World gen stuff.
		 * Trying to make a NEW World will produce a crash because the cache is empty.
		 * ..maybe this is not the best place for this, but it works :)
		 */
		TFC_Climate.worldPair.put(worldObj, new WorldCacheManager(worldObj));
		TFC_Core.addCDM(worldObj);

		super.registerWorldChunkManager();
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
		if(y < Global.SEALEVEL || y > Global.SEALEVEL + 25) return false;
		Block b = worldObj.getBlock(x, y, z);
		return TFC_Core.isSand(b) || TFC_Core.isGrass(b);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMoonPhase(long par1)
	{
		//Only calculate if we haven't already calculated today
		//so we don't needlessly recalculate
		if(TFC_Time.getDayFromTotalHours(TFC_Time.getTotalHours()) != moonPhaseLastCalculated)
		{
			int daysPassed = (int)(par1 / TFC_Time.DAY_LENGTH);
			int dayOfMonth = daysPassed % TFC_Time.daysInMonth;
			float dayToLunarDayMultiplier = (float)8/TFC_Time.daysInMonth;
			//Round rather than just cast to ensure that the full moon
			//only lasts one night
			int lunarDay = Math.round(dayOfMonth*dayToLunarDayMultiplier);
			
			moonPhase = lunarDay % 8;
			
			moonPhaseLastCalculated = TFC_Time.getDayFromTotalHours(TFC_Time.getTotalHours());
		}
		
		return moonPhase;
	}

	@Override
	public float getCloudHeight()
	{
		return 256.0F;
	}

	/*@Override
	public ChunkCoordinates getSpawnPoint()
	{
		return super.getSpawnPoint();
	}*/

	private boolean isNextToShoreOrIce(int x, int y, int z)
	{
		if(worldObj.checkChunksExist(x+1, y, z, x+1, y, z))
			if(worldObj.getBlock(x+1, y, z) == TFCBlocks.ice || TFC_Core.isGround(worldObj.getBlock(x+1, y, z)))
				return true;
		if(worldObj.checkChunksExist(x-1, y, z, x-1, y, z))
			if(worldObj.getBlock(x-1, y, z) == TFCBlocks.ice || TFC_Core.isGround(worldObj.getBlock(x-1, y, z)))
				return true;
		if(worldObj.checkChunksExist(x, y, z+1, x, y, z+1))
			if(worldObj.getBlock(x, y, z+1) == TFCBlocks.ice || TFC_Core.isGround(worldObj.getBlock(x, y, z+1)))
				return true;
		if(worldObj.checkChunksExist(x, y, z-1, x, y, z-1))
			if(worldObj.getBlock(x, y, z-1) == TFCBlocks.ice || TFC_Core.isGround(worldObj.getBlock(x, y, z-1)))
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

		if (temp <= 0 && biome != TFCBiome.DEEP_OCEAN)
		{
			if (worldObj.isAirBlock(x, y+1, z) && TFC_Core.isWater(id) && worldObj.rand.nextInt(4) == 0 && isNextToShoreOrIce(x,y,z))
			{
				Material mat = worldObj.getBlock(x, y, z).getMaterial();
				boolean salty = TFC_Core.isSaltWaterIncludeIce(id, meta, mat);

				if(temp <= -2)
					salty = false;

				if((mat == Material.water || mat == Material.ice) && !salty)
				{
					if(id == TFCBlocks.freshWaterStationary && meta == 0/* || id == TFCBlocks.FreshWaterFlowing.blockID*/)
					{
						worldObj.setBlock(x, y, z, TFCBlocks.ice, 1, 2);
					}
					else if(id == TFCBlocks.saltWaterStationary && meta == 0/* || id == Block.waterMoving.blockID*/)
					{
						worldObj.setBlock(x, y, z, TFCBlocks.ice, 0, 2);
					}
				}
				return false;//(mat == Material.water) && !salty;
			}
		}
		else
		{
			if(id == TFCBlocks.ice)
			{
				int chance = (int)Math.floor(Math.max(1, 6f-temp));
				if(id == TFCBlocks.ice && worldObj.rand.nextInt(chance) == 0)
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
							worldObj.setBlock(x, y, z, TFCBlocks.saltWaterStationary, 0, flag);
						}
						else if((meta & 1) == 1)
						{
							worldObj.setBlock(x, y, z, TFCBlocks.freshWaterStationary, 0, flag);
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
		if (TFC_Climate.getHeightAdjustedTemp(worldObj,x, y, z) > 0)
			return false;
		Material material = worldObj.getBlock(x, y, z).getMaterial();
		if (material == Material.snow)  // avoid vanilla MC to replace snow
			return false;
		else
			return TFCBlocks.snow.canPlaceBlockAt(worldObj, x, y, z) && material.isReplaceable();
	}

	/*private boolean canSnowAtTemp(int x, int y, int z)
	{
		if(TFC_Climate.getHeightAdjustedTemp(worldObj,x, y, z) <= 0)
			return true;
		return false;
	}*/

	@Override
	public String getDimensionName()
	{
		return "DEFAULT";
	}

	/**
	 * Gets the hard-coded portal location to use when entering this dimension.
	 */
	@Override
	public ChunkCoordinates getEntrancePortalLocation()
	{
		return getSpawnPoint();
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

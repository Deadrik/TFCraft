package com.bioxx.tfc.Core;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.WorldCacheManager;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Util.Helper;

public class TFC_Climate
{
	public static Map<World, WorldCacheManager> worldPair = new HashMap<World, WorldCacheManager>();

	private static final float[] Y_FACTOR_CACHE = new float[441];
	private static final float[] Z_FACTOR_CACHE = new float[30001];
	private static final float[][] MONTH_TEMP_CACHE = new float[12][30001];

	//private static int[][] insolationMap;

	/**
	 * All Temperature related code
	 */

	public static void initCache()
	{
		//internationally accepted average lapse time is 6.49 K / 1000 m, for the first 11 km of the atmosphere. I suggest graphing our temperature
		//across the 110 m against 2750 m, so that gives us a change of 1.6225 / 10 blocks, which isn't /terrible/
		//Now going to attemp exonential growth. calculations but change in temperature at 17.8475 for our system, so that should be the drop at 255.
		//therefore, change should be temp - f(x), where f(x) is an exp function roughly equal to f(x) = (x^2)/ 677.966.
		//This seems to work nicely. I like this. Since creative allows players to travel above 255, I'll see if I can't code in the rest of it.
		//The upper troposhere has no lapse rate, so we'll just use that.
		//The equation looks rather complicated, but you can see it here:
		// http://www.wolframalpha.com/input/?i=%28%28%28x%5E2+%2F+677.966%29+*+%280.5%29*%28%28%28110+-+x%29+%2B+%7C110+-+x%7C%29%2F%28110+-
		// +x%29%29%29+%2B+%28%280.5%29*%28%28%28x+-+110%29+%2B+%7Cx+-+110%7C%29%2F%28x+-+110%29%29+*+x+*+0.16225%29%29+0+to+440

		for (int y = 0; y < Y_FACTOR_CACHE.length; y += 1) {
		    // temp = temp - (ySq / 677.966f) * (((110.01f - y) + Math.abs(110.01f - y)) / (2 * (110.01f - y)));
		    // temp -= (0.16225 * y * (((y - 110.01f) + Math.abs(y - 110.01f)) / (2 * (y - 110.01f))));
			
			// float ySq = y * y;
			// float diff = 110.01f - y;
			// float factor = (ySq / 677.966f) * ((diff + Math.abs(diff)) / (2 * diff))
			// 		+ 0.16225f * y * ((diff - Math.abs(diff)) / (2 * diff));

			//more optimization: using an if should be more efficient (and simpler)
			float factor;
			if (y < 110) {  
				// diff > 0
				factor = y * y / 677.966f;  // 17.85 for y=110
			} else {
				// diff <= 0
				factor = 0.16225f * y;  // 17.85 for y=110
			}
			Y_FACTOR_CACHE[y] = factor;
		}
		
		for(int zCoord = 0; zCoord < getMaxZPos() + 1; ++zCoord)
		{
			float factor = 0;
			float z = zCoord;

			factor = (getMaxZPos()-z)/(getMaxZPos());

			Z_FACTOR_CACHE[zCoord] = factor;

			for(int month = 0; month < 12; ++month)
			{
				final float MAXTEMP = 35F;

				double angle = factor * (Math.PI / 2);
				double latitudeFactor = Math.cos(angle);

				switch(month)
				{
				case 10:
				{
					MONTH_TEMP_CACHE[month][zCoord] = (float)(MAXTEMP-13.5*latitudeFactor - (latitudeFactor*55));
					break;
				}
				case 9:
				case 11:
				{
					MONTH_TEMP_CACHE[month][zCoord] = (float)(MAXTEMP -12.5*latitudeFactor- (latitudeFactor*53));
					break;
				}
				case 0:
				case 8:
				{
					MONTH_TEMP_CACHE[month][zCoord] = (float)(MAXTEMP -10*latitudeFactor- (latitudeFactor*46));
					break;
				}
				case 1:
				case 7:
				{
					MONTH_TEMP_CACHE[month][zCoord] = (float)(MAXTEMP -7.5*latitudeFactor- (latitudeFactor*40));
					break;
				}
				case 2:
				case 6:
				{
					MONTH_TEMP_CACHE[month][zCoord] = (float)(MAXTEMP - 5*latitudeFactor- (latitudeFactor*33));
					break;
				}
				case 3:
				case 5:
				{
					MONTH_TEMP_CACHE[month][zCoord] = (float)(MAXTEMP -2.5*latitudeFactor- (latitudeFactor*27)); 
					break;
				}
				case 4:
				{
					MONTH_TEMP_CACHE[month][zCoord] = (float)(MAXTEMP -1.5*latitudeFactor- (latitudeFactor*27));
					break;
				}
				}
			}
		}
	}

	protected static float getZFactor(int zCoord)
	{
		if(zCoord < 0)
			zCoord = -zCoord;

		if (zCoord > getMaxZPos())
			zCoord = getMaxZPos();

		return Z_FACTOR_CACHE[zCoord];
	}

	protected static float getTemp(World world,int x, int z)
	{
		return getTemp0(world, TFC_Time.currentDay,TFC_Time.getHour(), x, z, false);
	}

	protected static float getTemp(World world, int day, int hour, int x, int z)
	{
		return getTemp0(world, day, hour, x, z, false);
	}
	
	protected static float getBioTemp(World world,int day, int x, int z)
	{
		return getTemp0(world, day, 0, x, z, true);
	}
	
	private static float getTemp0(World world, int day, int hour, int x, int z, boolean bio)
	{
		if(TFC_Climate.getCacheManager(world) != null)
		{
			float zMod = getZFactor(z);
			float zTemp = zMod * getMaxTemperature() - 20 + ((zMod - 0.5f) * 10);

			float rain = getRainfall(world, x, Global.SEALEVEL, z);
			float rainMod = (1-(rain/4000))*zMod;

			int month = TFC_Time.getSeasonFromDayOfYear(day,z);
			int lastMonth = TFC_Time.getSeasonFromDayOfYear(day-TFC_Time.daysInMonth,z);
			
			float monthTemp = getMonthTemp(month, z);
			float lastMonthTemp = getMonthTemp(lastMonth, z);

			int dayOfMonth = TFC_Time.getDayOfMonthFromDayOfYear(day);

			float hourMod;
			float dailyTemp;
			if (bio) {
				hourMod = 0.2f;
				dailyTemp = 0;
			} else {
				int h = (hour - 6) % TFC_Time.HOURS_IN_DAY;
				if (h < 0) {
					h += TFC_Time.HOURS_IN_DAY;
				}

				if(h < 12)
					hourMod = ((float)h / 11) * 0.3F;
				else
					hourMod = 0.3F - ((((float)h-12) / 11) * 0.3F);
				
				dailyTemp = WeatherManager.getInstance().getDailyTemp(day);
			}

			float monthDelta = ((monthTemp-lastMonthTemp) * dayOfMonth) / TFC_Time.daysInMonth;
			float temp = lastMonthTemp + monthDelta;

			temp += dailyTemp + (hourMod*(zTemp + dailyTemp));

			if(temp >= 12)
				temp += (8*rainMod)*zMod;
			else
				temp -= (8*rainMod)*zMod;
				
			return temp;
		}
		return -10;
	}

	protected static float getMonthTemp(int season, int z)
	{
		if(z < 0)
			z = -z;
		if (z > getMaxZPos())
			z = getMaxZPos();
		return MONTH_TEMP_CACHE[season][z];
	}

	protected static float getTempSpecificDay(World world,int day, int x, int z)
	{
		return getTemp(world, day, 12, x, z);
	}

	public static float getHeightAdjustedTemp(World world, int x, int y, int z)
	{
		float temp = getTemp(world, x, z);
		temp += getTemp(world, x+1, z);
		temp += getTemp(world, x-1, z);
		temp += getTemp(world, x, z+1);
		temp += getTemp(world, x, z-1);
		temp /= 5;
		temp = adjustHeightToTemp(y,temp);
		float light = 1;

		if(world.getChunkProvider() != null /*&& worldObj.blockExists(x, y, z)*/)
		{
			//If this block can see the sky then we jsut want it to be ambient temp. 
			//Shadows should only matter for darkness, not night time.
			if(world.canBlockSeeTheSky(x, y, z))
			{
				light = 0;
			}
			else
			{
				float bl = world.getBlockLightValue(x, y, z);
				light = 0.25f*(1-(bl/15f));
			}
		}

		if(temp > 0)
			return temp - (temp * light);
		else
			return temp;
	}

	public static float adjustHeightToTemp(int y, float temp)
	{
		//internationally accepted average lapse time is 6.49 K / 1000 m, for the first 11 km of the atmosphere. I suggest graphing our temperature
		//across the 110 m against 2750 m, so that gives us a change of 1.6225 / 10 blocks, which isn't /terrible/
		//Now going to attemp exonential growth. calculations but change in temperature at 17.8475 for our system, so that should be the drop at 255.
		//therefore, change should be temp - f(x), where f(x) is an exp function roughly equal to f(x) = (x^2)/ 677.966.
		//This seems to work nicely. I like this. Since creative allows players to travel above 255, I'll see if I can't code in the rest of it.
		//The upper troposhere has no lapse rate, so we'll just use that.
		//The equation looks rather complicated, but you can see it here:
		// http://www.wolframalpha.com/input/?i=%28%28%28x%5E2+%2F+677.966%29+*+%280.5%29*%28%28%28110+-+x%29+%2B+%7C110+-+x%7C%29%2F%28110+-
		// +x%29%29%29+%2B+%28%280.5%29*%28%28%28x+-+110%29+%2B+%7Cx+-+110%7C%29%2F%28x+-+110%29%29+*+x+*+0.16225%29%29+0+to+440
		if(y > Global.SEALEVEL)
		{
			int i = y - Global.SEALEVEL;
			if (i >= Y_FACTOR_CACHE.length) {
				i = Y_FACTOR_CACHE.length - 1;
			}
			temp -= Y_FACTOR_CACHE[i];
		}
		return temp;
	}

	public static float getHeightAdjustedTempSpecificDay(World world,int day, int x, int y, int z)
	{
		float temp = getTempSpecificDay(world, day, x, z);
		temp = adjustHeightToTemp(y,temp);
		return temp;
	}

	public static float getHeightAdjustedTempSpecificDay(World world,int day, int hour, int x, int y, int z)
	{
		float temp = getTemp(world, day, hour, x, z);
		temp = adjustHeightToTemp(y,temp);
		return temp;
	}

	public static float getHeightAdjustedBioTemp(World world,int day, int x, int y, int z)
	{
		float temp = getBioTemp(world, day, x, z);
		temp = adjustHeightToTemp(y,temp);
		return temp;
	}

	public static float getMaxTemperature()
	{
		return 52;
	}

	public static float getBioTemperatureHeight(World world,int x, int y, int z)
	{
		float temp = 0;
		for(int i = 0; i < 12; i++)
		{
			float t = getHeightAdjustedBioTemp(world, i*TFC_Time.daysInMonth, x, y, z);
			//if(t < 0)
			//	t = 0;
			temp += t;
		}
		return temp / 12;
	}

	public static float getBioTemperature(World world,int x, int z)
	{
		float temp = 0;
		for(int i = 0; i < 24; i++)
		{
			float t = getBioTemp(world, i*TFC_Time.daysInMonth/2, x, z);
			//if(t < 0)
			//	t = 0;
			temp += t;
		}
		return temp / 24;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic grass color based on the biome temperature and rainfall
	 */
	public static int getGrassColor(World world, int x, int y, int z)
	{
		float temp = (getTemp(world, x, z) + getMaxTemperature()) / (getMaxTemperature() * 2);

		float rain = getRainfall(world, x, y, z) / 8000;

		double var1 = Helper.clampFloat(temp, 0.0F, 1.0F);
		double var3 = Helper.clampFloat(rain, 0.0F, 1.0F);

		return ColorizerGrassTFC.getGrassColor(var1, var3);
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic foliage color based on the biome temperature and rainfall
	 */
	public static int getFoliageColor(World world, int x, int y, int z)
	{
		float temperature = getHeightAdjustedTempSpecificDay(world, TFC_Time.getDayOfYear(), x, y, z);
		float rainfall = getRainfall(world, x, y, z);
		if(temperature > 5 && rainfall > 100)
		{
			float temp = (temperature + 35) / (getMaxTemperature() + 35);
			float rain = rainfall / 8000;

			double var1 = Helper.clampFloat(temp, 0.0F, 1.0F);
			double var3 = Helper.clampFloat(rain, 0.0F, 1.0F);
			return ColorizerFoliageTFC.getFoliageColor(var1, var3);
		}
		else
		{
			return ColorizerFoliageTFC.getFoliageDead();
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic foliage color based on the biome temperature and rainfall
	 */
	public static int getFoliageColorEvergreen(World world, int x, int y, int z)
	{
		//int month = TFC_Time.getSeasonAdjustedMonth(z);
		float rainfall = getRainfall(world, x, y, z);
		if(rainfall > 100)
		{
			float temp = (getTemp(world, x, z)+35)/(getMaxTemperature()+35);
			float rain = rainfall / 8000;

			double var1 = Helper.clampFloat(temp, 0.0F, 1.0F);
			double var3 = Helper.clampFloat(rain, 0.0F, 1.0F);
			return ColorizerFoliageTFC.getFoliageColor(var1, var3);
		}
		else
		{
			return ColorizerFoliageTFC.getFoliageDead();
		}
	}

	/**
	 * All Rainfall related code
	 */

	public static float getRainfall(World world, int x, int y, int z)
	{
		if (world.isRemote && TFC_Core.getCDM(world) != null)
		{
			ChunkData cd = TFC_Core.getCDM(world).getData(x >> 4, z >> 4);
			if(cd!= null)
				return cd.getRainfall(x & 15, z & 15);
		}

		if (getCacheManager(world) != null)
		{
			DataLayer dl = getCacheManager(world).getRainfallLayerAt(x, z);
			return dl != null ? dl.floatdata1 : DataLayer.RAIN_500.floatdata1;
		}

		return DataLayer.RAIN_500.floatdata1;
	}

	public static int getTreeLayer(World world,int x, int y, int z, int index)
	{
		return getCacheManager(world).getTreeLayerAt(x, z, index).data1;
	}

	public static DataLayer getRockLayer(World world,int x, int y, int z, int index)
	{
		return getCacheManager(world).getRockLayerAt(x, z, index);
	}

	public static int getMaxZPos()
	{
		return 30000;
	}

	public static boolean isSwamp(World world, int x, int y, int z)
	{
		float rain = getRainfall(world, x, y, z);
		float evt = getCacheManager(world).getEVTLayerAt(x, z).floatdata1;
		return rain >= 1000 && evt <= 0.25 && world.getBiomeGenForCoords(x, z).heightVariation < 0.15;
	}

	public static int getStability(World world, int x, int z)
	{
		if (getCacheManager(world) != null)
			return getCacheManager(world).getStabilityLayerAt(x, z).data1;
		else
			return 0;
	}

	public static WorldCacheManager getCacheManager(World world)
	{
		return worldPair.get(world);
	}

	public static void removeCacheManager(World world)
	{
		if(worldPair.containsKey(world))
			worldPair.remove(world);
	}
}

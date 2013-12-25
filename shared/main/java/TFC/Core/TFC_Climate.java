package TFC.Core;

import java.util.ArrayList;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import TFC.API.Util.Helper;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFC_Climate
{
	public static World worldObj;
	public static TFCWorldChunkManager manager;
	private static final float[] zFactorCache = new float[30001];
	private static final float[][] monthTempCache = new float[12][30001];
	private static final float[][] monthTempFactorCache = new float[12][30001];
	private static int[][] insolationMap;

	/**
	 * All Temperature related code
	 */

	public static void initCache()
	{
		for(int zCoord = 0; zCoord < getMaxZPos() + 1; ++zCoord)
		{
			float factor = 0;
			float z = zCoord;

			factor = (getMaxZPos()-z)/(getMaxZPos());

			zFactorCache[zCoord] = factor;

			for(int month = 0; month < 12; ++month)
			{
				final float MAXTEMP = 35F;

				double angle = factor * (Math.PI / 2);
				double latitudeFactor = Math.cos(angle);

				switch(month)
				{
				case 9:
				case 11:
				{
					monthTempCache[month][zCoord] = (float)(MAXTEMP -12.5*latitudeFactor- (latitudeFactor*53));
					break;
				}
				case 0:
				case 8:
				{
					monthTempCache[month][zCoord] = (float)(MAXTEMP -10*latitudeFactor- (latitudeFactor*46));
					break;
				}
				case 1:
				case 7:
				{
					monthTempCache[month][zCoord] = (float)(MAXTEMP -7.5*latitudeFactor- (latitudeFactor*40));
					break;
				}
				case 2:
				case 6:
				{
					monthTempCache[month][zCoord] = (float)(MAXTEMP - 5*latitudeFactor- (latitudeFactor*33));
					break;
				}
				case 3:
				case 5:
				{
					monthTempCache[month][zCoord] = (float)(MAXTEMP -2.5*latitudeFactor- (latitudeFactor*27)); 
					break;
				}
				case 4:
				{
					monthTempCache[month][zCoord] = (float)(MAXTEMP -1.5*latitudeFactor- (latitudeFactor*27));
					break;
				}
				case 10:
				{
					monthTempCache[month][zCoord] = (float)(MAXTEMP-15*latitudeFactor - (latitudeFactor*60));
					break;
				}
				}

				float diff = (1-factor) / 6;

				switch(month)
				{
				case 11:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff*4);
					break;
				}
				case 0:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff*3);
					break;
				}
				case 1:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff*2);
					break;
				}
				case 2:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff*1);
					break;
				}
				case 3:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff);
					break;
				}
				case 4:
				{
					monthTempFactorCache[month][zCoord] = 1F;
					break;
				}
				case 5:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff*1.2f);
					break;
				}
				case 6:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff*2.4f);
					break;
				}
				case 7:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff*3.6f);
					break;
				}
				case 8:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff*4.8f);
					break;
				}
				case 9:
				{
					monthTempFactorCache[month][zCoord] = factor;
					break;
				}
				case 10:
				{
					monthTempFactorCache[month][zCoord] = 1-(diff*5);
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

		if(zCoord > getMaxZPos()) zCoord = (getMaxZPos());

		return zFactorCache[zCoord];
	}

	protected static float getTemp(int x, int z)
	{
		return getTemp(TFC_Time.currentDay, x, z);
	}

	protected static float getTemp(int day, int x, int z)
	{
		if(manager!= null)
		{

			float zMod = getZFactor(z);
			float zTemp = (zMod * getMaxTemperature())-20;

			float rainMod = 1-manager.getRainfallLayerAt(x, z).floatdata1/16000;

			int _month = TFC_Time.getMonthFromDayOfYear(day);
			int _lastmonth = TFC_Time.getMonthFromDayOfYear(day-TFC_Time.daysInMonth);

			float mod = getMonthTempFactor(_month, z);
			float modLast = getMonthTempFactor(_lastmonth, z);
			int day2 = day - ((day/TFC_Time.daysInMonth)*TFC_Time.daysInMonth);
			int hour = (int) TFC_Time.getHour();

			if(hour < 0)
				hour = 23 + hour;
			float hourMod = 0;

			if(hour < 12)
				hourMod = ((float)hour/11) * 0.3F;
			else
				hourMod = 0.3F - (((float)(hour-12)/11) * 0.3F);

			float monthMod = 0;
			float temp = 0;

			float dailyTemp = WeatherManager.getInstance().getDailyTemp(day);

			if(modLast > mod)
			{
				monthMod = ((modLast-mod)/TFC_Time.daysInMonth)*day2;
				monthMod = (modLast - monthMod);

				temp += getMonthTemp(_month,z) + dailyTemp;//((zTemp + dailyTemp));
				if(temp < 0)monthMod = 1 - monthMod;
				temp *= monthMod;
				temp += (hourMod*(zTemp + dailyTemp));
				temp += (8*rainMod)*zMod;
			}
			else
			{
				monthMod = ((modLast-mod)/TFC_Time.daysInMonth)*day2;
				monthMod = (modLast + monthMod);

				temp += getMonthTemp(_month,z) + dailyTemp;//((zTemp + dailyTemp));
				if(temp < 0)monthMod = 1 - monthMod;
				temp *= monthMod;
				temp += (hourMod*(zTemp + dailyTemp));
				temp += (8*rainMod)*zMod;
			}

			return temp;
		}
		return -10;
	}

	protected static float getBioTemp(int day, int x, int z)
	{
		if(manager!= null)
		{
			float zMod = getZFactor(z);
			float zTemp = (zMod * getMaxTemperature())-20;


			float rain = manager.getRainfallLayerAt(x, z).floatdata1;
			float rainMod = 1-(rain/4000);

			int _month = TFC_Time.getMonthFromDayOfYear(day);
			int _lastmonth = TFC_Time.getMonthFromDayOfYear(day-TFC_Time.daysInMonth);

			float monthModifier = getMonthTempFactor(_month, z);
			float lastMonthModifier = getMonthTempFactor(_lastmonth, z);

			int dayOfMonth =  TFC_Time.getDayOfMonthFromDayOfYear(day);

			float hourMod = 0.2f;

			float monthMod = 0;
			float temp = 0;

			if(lastMonthModifier > monthModifier)
			{
				monthMod = ((lastMonthModifier-monthModifier)/TFC_Time.daysInMonth)*dayOfMonth;
				monthMod = (lastMonthModifier - monthMod);

				temp += getMonthTemp(_month,z);//((zTemp));
				if(temp < 0)monthMod = 1 - monthMod;
				temp *= monthMod;
				temp += (hourMod*(zTemp));
				temp += (8*rainMod)*zMod;
			}
			else
			{
				monthMod = ((lastMonthModifier-monthModifier)/TFC_Time.daysInMonth)*dayOfMonth;
				monthMod = (lastMonthModifier + monthMod);

				temp += getMonthTemp(_month,z);//((zTemp));
				if(temp < 0)monthMod = 1 - monthMod;
				temp *= monthMod;
				temp += (hourMod*(zTemp));
				temp += (8*rainMod)*zMod;
			}

			return temp;
		}
		return -10;
	}

	protected static float getMonthTemp(int month, int z)
	{
		if(z < 0)
			z = -z;

		if(z > getMaxZPos()) z = (getMaxZPos());

		return monthTempCache[month][z];
	}

	protected static float getMonthTempFactor(int month, int z)
	{
		if(z < 0)
			z = -z;

		if(z > getMaxZPos()) z = (getMaxZPos());

		return monthTempFactorCache[month][z];
	}

	protected static float getTempSpecificDay(int day, int x, int z)
	{
		return getTemp(day, x, z);
	}

	public static float getHeightAdjustedTemp(int x, int y, int z)
	{
		float temp = getTemp(x, z);

		temp += getTemp(x+1, z);
		temp += getTemp(x-1, z);
		temp += getTemp(x, z+1);
		temp += getTemp(x, z-1);

		temp/= 5;

		if(y > 180)
			temp -= Math.abs(temp) * (y-180)/90;

		return temp;
	}

	public static float adjustHeightToTemp(int y, float temp)
	{
		if(y > 180)
			temp -= Math.abs(temp) * (y-180)/90;

		return temp;
	}

	public static float getHeightAdjustedTempSpecificDay(int day, int x, int y, int z)
	{
		float temp = getTempSpecificDay(day, x, z);

		if(y > 180)
			temp -= Math.abs(temp) * (y-180)/90;

		return temp;
	}

	public static float getHeightAdjustedBioTemp(int day, int x, int y, int z)
	{
		float temp = getBioTemp(day, x, z);

		if(y > 180)
			temp -= Math.abs(temp) * (y-180)/90;

		return temp;
	}

	public static float getMaxTemperature()
	{
		return 52;
	}

	public static float getBioTemperatureHeight(int x, int y, int z)
	{
		float temp = 0;
		for(int i = 0; i < 12; i++)
		{
			float t = getHeightAdjustedBioTemp(i*TFC_Time.daysInMonth, x, y, z);
			if(t < 0)
				t = 0;

			temp += t;
		}
		return temp/12;
	}

	public static float getBioTemperature(int x, int z)
	{
		float temp = 0;
		for(int i = 0; i < 24; i++)
		{
			float t = getBioTemp(i*TFC_Time.daysInMonth/2, x, z);
			if(t < 0)
				t = 0;

			temp += t;
		}
		return temp/24;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic grass color based on the biome temperature and rainfall
	 */
	public static int getGrassColor(World world, int x, int y, int z)
	{
		float temp = (getTemp(x, z)/getMaxTemperature());

		float rain = (TFC_Climate.getRainfall(x, y, z) / 8000);

		double var1 = Helper.clamp_float(temp, 0.0F, 1.0F);
		double var3 = Helper.clamp_float(rain, 0.0F, 1.0F);

		return ColorizerGrassTFC.getGrassColor(var1, var3);
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic foliage color based on the biome temperature and rainfall
	 */
	public static int getFoliageColor(World world, int x, int y, int z)
	{
		if(TFC_Time.currentMonth < 9)
		{
			float temp = (getTemp(x, z)+35)/(getMaxTemperature()+35);
			//float evt = (1 - (((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z).floatdata1 / 16))*0.5f;
			float rain = (TFC_Climate.getRainfall(x, y, z) / 8000);

			double var1 = Helper.clamp_float(temp, 0.0F, 1.0F);
			double var3 = Helper.clamp_float(rain, 0.0F, 1.0F);
			return ColorizerFoliageTFC.getFoliageColor(var1, var3);
		}
		else
			return ColorizerFoliageTFC.getFoliageDead();
	}

	/**
	 * All Rainfall related code
	 */

	public static float getRainfall(int x, int y, int z)
	{
		return manager.getRainfallLayerAt(x, z).floatdata1;
	}

	public static float getTerrainAdjustedRainfall(int x, int y, int z)
	{
		float rain = manager.getRainfallLayerAt(x, z).floatdata1;
		ArrayList biomes = new ArrayList<TFCBiome>();
		biomes.add(TFCBiome.river);
		biomes.add(TFCBiome.ocean);
		biomes.add(TFCBiome.swampland);

		float rainModWest = 1;
		float rainModNorth = 1;
		float rainModSouth = 1;
		float rainModEast = 1;

		BiomeGenBase biome = null;


		for(int i = 0; i < 8; i++)
		{
			biome = worldObj.getBiomeGenForCoords((x-512)+(64*i), z);

			if(biome.biomeID == TFCBiome.Mountains.biomeID)
				rainModWest = 1 - (i * 0.0625f);
			else if(biome.biomeID == TFCBiome.ocean.biomeID)
				rainModWest = 1 + (i * 0.125f);

		}
		for(int i = 0; i < 8; i++)
		{
			biome =  worldObj.getBiomeGenForCoords(x, (z+512)-(64*i));

			if(biome.biomeID == TFCBiome.Mountains.biomeID)
				rainModSouth = 1 - (i * 0.0625f);
			else if(biome.biomeID == TFCBiome.ocean.biomeID)
				rainModSouth = 1 + (i * 0.125f);

		}
		for(int i = 0; i < 2; i++)
		{
			biome = worldObj.getBiomeGenForCoords(x, (z-128)+(64*i));

			if(biome.biomeID == TFCBiome.ocean.biomeID)
				rainModNorth +=  0.35f;

		}
		for(int i = 0; i < 2; i++)
		{
			biome = worldObj.getBiomeGenForCoords((x+128)-(64*i), z);

			if(biome.biomeID == TFCBiome.ocean.biomeID)
				rainModEast += 0.35f;

		}

		float addMoisture = 1;
		for(int i = -2; i <= 2 && addMoisture == 1; i++)
			for(int k = -2; k <= 2 && addMoisture == 1; k++)
			{
				biome = worldObj.getBiomeGenForCoords(x+(i * 8), z+(k * 8));
				if(biome.biomeID == TFCBiome.ocean.biomeID || biome.biomeID == TFCBiome.river.biomeID || biome.biomeID == TFCBiome.swampland.biomeID)
					addMoisture = 2f;
			}


		return rain*((rainModEast + rainModWest + rainModNorth + rainModSouth + addMoisture)/5);
	}

	public static int getTreeLayer(int x, int y, int z, int index)
	{
		return manager.getTreeLayerAt(x, z, index).data1;
	}

	public static int[] getRockLayer(int x, int y, int z, int index)
	{
		return new int[] {manager.getRockLayerAt(x, z, index).data1, manager.getRockLayerAt(x, z, index).data2};
	}

	public static int getMaxZPos()
	{
		return 30000;
	}

	public static boolean isSwamp(int x, int y, int z)
	{
		float rain = getRainfall(x,y,z);
		float evt = manager.getEVTLayerAt(x, z).floatdata1;
		if(rain >= 1000 && evt < 0.25 && manager.getBiomeGenAt(x, z).maxHeight < 0.15)
			return true;
		return false;
	}

	public static int getStability(int x, int z)
	{
		return manager.getStabilityLayerAt(x, z).data1;
	}
}

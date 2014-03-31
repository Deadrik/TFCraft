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
		return getTemp(TFC_Time.currentDay,TFC_Time.getHour(), x, z);
	}

	protected static float getTemp(int day, int th, int x, int z)
	{
		if(manager!= null)
		{

			float zMod = getZFactor(z);
			float zTemp = (zMod * getMaxTemperature())-20 + ((zMod - 0.5f)*10);

			float rainMod = (1-((getRainfall(x, 144, z))/4000))*zMod;

			int _month = TFC_Time.getSeasonFromDayOfYear(day,z);
			int _lastmonth = TFC_Time.getSeasonFromDayOfYear(day-TFC_Time.daysInMonth,z);

			float mod = getMonthTempFactor(_month, z);
			float modLast = getMonthTempFactor(_lastmonth, z);
			int day2 = day - ((day/TFC_Time.daysInMonth)*TFC_Time.daysInMonth);
			int hour = TFC_Time.getHourOfDayFromTotalHours(th);

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
				if(temp >= 12){
					temp += (8*rainMod)*zMod;
				}
				else{
					temp -= (8*rainMod)*zMod;
				}
			}
			else
			{
				monthMod = ((modLast-mod)/TFC_Time.daysInMonth)*day2;
				monthMod = (modLast + monthMod);

				temp += getMonthTemp(_month,z) + dailyTemp;//((zTemp + dailyTemp));
				if(temp < 0)monthMod = 1 - monthMod;
				temp *= monthMod;
				temp += (hourMod*(zTemp + dailyTemp));
				if(temp >= 12){
					temp += (8*rainMod)*zMod;
				}
				else{
					temp -= (8*rainMod)*zMod;
				}
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
			float zTemp = (zMod * getMaxTemperature())-20 + ((zMod - 0.5f)*10);


			float rain = getRainfall(x, 144, z);
			float rainMod = (1-(rain/4000))*zMod;

			int _season = TFC_Time.getSeasonFromDayOfYear(day,z);
			int _lastseason = TFC_Time.getSeasonFromDayOfYear(day-TFC_Time.daysInMonth,z);

			float monthModifier = getMonthTempFactor(_season, z);
			float lastMonthModifier = getMonthTempFactor(_lastseason, z);

			int dayOfMonth =  TFC_Time.getDayOfMonthFromDayOfYear(day);

			float hourMod = 0.2f;

			float monthMod = 0;
			float temp = 0;

			if(lastMonthModifier > monthModifier)
			{
				monthMod = ((lastMonthModifier-monthModifier)/TFC_Time.daysInMonth)*dayOfMonth;
				monthMod = (lastMonthModifier - monthMod);

				temp += getMonthTemp(_season,z);//((zTemp));
				if(temp < 0)monthMod = 1 - monthMod;
				temp *= monthMod;
				temp += (hourMod*(zTemp));
				if(temp >= 12){
					temp += (8*rainMod)*zMod;
				}
				else{
					temp -= (8*rainMod)*zMod;
				}
			}
			else
			{
				monthMod = ((lastMonthModifier-monthModifier)/TFC_Time.daysInMonth)*dayOfMonth;
				monthMod = (lastMonthModifier + monthMod);

				temp += getMonthTemp(_season,z);//((zTemp));
				if(temp < 0)monthMod = 1 - monthMod;
				temp *= monthMod;
				temp += (hourMod*(zTemp));
				if(temp >= 12){
					temp += (8*rainMod)*zMod;
				}
				else{
					temp -= (8*rainMod)*zMod;
				}
			}

			return temp;
		}
		return -10;
	}

	protected static float getMonthTemp(int season, int z)
	{
		if(z < 0){
			z = -z;
		}

		if(z > getMaxZPos()) z = (getMaxZPos());

		return monthTempCache[season][z];
	}

	protected static float getMonthTempFactor(int season, int z)
	{
		if(z < 0){
			z = -z;
		}

		if(z > getMaxZPos()) z = (getMaxZPos());

		return monthTempFactorCache[season][z];
	}

	protected static float getTempSpecificDay(int day, int x, int z)
	{
		return getTemp(day,12, x, z);
	}

	public static float getHeightAdjustedTemp(int x, int y, int z)
	{
		float temp = getTemp(x, z);

		temp += getTemp(x+1, z);
		temp += getTemp(x-1, z);
		temp += getTemp(x, z+1);
		temp += getTemp(x, z-1);

		temp/= 5;

		temp = adjustHeightToTemp(y,temp);
		float light = 1;

		if(worldObj.blockExists(x, y, z)){
			float bl = worldObj.getBlockLightValue(x, y, z);
			light = 0.25f*(1-(bl/15f));
		}
		//If this block can see the sky then we jsut want it to be ambient temp. 
		//Shadows should only matter for darkness, not night time.
		if(worldObj.canBlockSeeTheSky(x, y, z))
			light = 0;

		if(temp > 0) return temp-(temp*light);
		else return temp;
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
		if(y > 144){
			y-=144;
			y = Math.min(y, 440);
			float ySq = y * y;
			temp = temp - (ySq / 677.966f) * (((110 - y) + Math.abs(110 - y))/(2*(110.01f - y)));
			temp -= (0.16225 * y * (((y - 110) + Math.abs(y - 110))/(2*(y - 110.01f))));
		}
		return temp;
	}

	public static float getHeightAdjustedTempSpecificDay(int day, int x, int y, int z)
	{
		float temp = getTempSpecificDay(day, x, z);
		temp = adjustHeightToTemp(y,temp);

		return temp;
	}
	
	public static float getHeightAdjustedTempSpecificDay(int day, int hour, int x, int y, int z)
	{
		float temp = getTemp(day, hour, x, z);
		temp = adjustHeightToTemp(y,temp);

		return temp;
	}

	public static float getHeightAdjustedBioTemp(int day, int x, int y, int z)
	{
		float temp = getBioTemp(day, x, z);

		temp = adjustHeightToTemp(y,temp);

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
			//if(t < 0)
			//	t = 0;

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
			//if(t < 0)
			//	t = 0;

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
		int month = TFC_Time.getSeason(z);
		if(month < 9)
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

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic foliage color based on the biome temperature and rainfall
	 */
	public static int getFoliageColorEvergreen(World world, int x, int y, int z)
	{
		int month = TFC_Time.getSeason(z);
		float temp = (getTemp(x, z)+35)/(getMaxTemperature()+35);
		//float evt = (1 - (((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z).floatdata1 / 16))*0.5f;
		float rain = (TFC_Climate.getRainfall(x, y, z) / 8000);

		double var1 = Helper.clamp_float(temp, 0.0F, 1.0F);
		double var3 = Helper.clamp_float(rain, 0.0F, 1.0F);
		return ColorizerFoliageTFC.getFoliageColor(var1, var3);
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
		float rain = getRainfall(x, y, z);
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

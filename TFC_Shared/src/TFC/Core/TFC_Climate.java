package TFC.Core;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
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
	//new method to generate temperature; basically, it calculates the tilt of the earth to find out the temperature for a given day
	protected static float getTemperature(int day, int x, int z){
		float latitude = 90 * (Helper.clamp_float(z, -30000, 30000))/-30000;
		float adjustedDayOfYear = (day + (TFC_Time.daysInMonth * 2))% TFC_Time.daysInYear;
		float adjustedLatitude = (float) ((Math.cos(day/TFC_Time.daysInYear)*25)+latitude);
		float calculatedTemperature = (float) ((Math.cos(adjustedLatitude*(Math.PI/90f))*29.5f)+4.5f);
		//-25 is the default temperature for 90 degrees N/S
		if(adjustedLatitude >90 || adjustedLatitude < 90){
			calculatedTemperature = -25 - (calculatedTemperature + 25); 
		}

		return calculatedTemperature;
	}

	protected static float getTemp(int day, int x, int z)
	{
		if(manager!= null)
		{

			float zTemp = getTemperature(day,x,z);

			float rainMod = 1-getRainfall(x,150,z)/16000;
			int day2 = day - ((day/TFC_Time.daysInMonth)*TFC_Time.daysInMonth);
			int hour = (int) TFC_Time.getHour();
			float hourMod = 1;
			float dailyTemp = 0;

			if(day == TFC_Time.currentDay){
				dailyTemp = WeatherManager.getInstance().getDailyTemp(day);
				if(hour < 0)
					hour = 23 + hour;
				

				if(hour < 12)
					hourMod = ((float)hour/11) * 0.3F;
				else
					hourMod = 0.3F - (((float)(hour-12)/11) * 0.3F);
			}

			float temp = 0;

			
			temp += (hourMod*(zTemp + dailyTemp));
			temp += (8*rainMod);


			return temp;
		}
		return -10;
	}

	protected static float getBioTemp(int day, int x, int z)
	{
		if(manager!= null)
		{
			return getTemp(TFC_Time.daysInYear/2,x,z);
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
		float average;
		float temp = (getTemp(x, z)/getMaxTemperature());
		

		float rain = (TFC_Climate.getRainfall(x, y, z) / 8000);

		//double var1 = Helper.clamp_float(temp, 0.0F, 1.0F);
		//double var3 = Helper.clamp_float(rain, 0.0F, 1.0F);

		double var0 = Helper.clamp_float(temp, 0.0F, 1.0F);
		double var2 = Helper.clamp_float(rain, 0.0F, 1.0F);

		int g = (int)(var0 < 0.3 ? 100*(1.3+var0):(var0*50)+160);		

		average = getAverageRedGrass(rain,temp);		
		int r = (int) Math.min(g*average,255);
		average = getAverageBlueGrass(rain,temp);
		int b = (int) Math.min(g*average,255);
		
		int finalColour = (r *65536) + (g*256) + b;
		int color = manager.getBiomeGenAt(x, z).color;
		return finalColour;//(r *65536) + (g*256) + b;
		//return ColorizerGrassTFC.getGrassColor(var1, var3);
	}
	
	public static float getAverageRedGrass(float rain, float temp){
		//for calculating R and B values, temporary variables
				float tempTemp = temp*2;
				float rainTemp = rain*2;
				
				//the red ratio is calculated from a 3x3 table of ratios obtained from real-life images based on temperature and humidity. The in-game
				// temp and rain is used to find an average red ratio from the table
				// four of the 9 levels are used for any calculation, the following variables representing them
				float bottomLeft;
				float bottomRight;
				float topLeft;
				float topRight;
				
				if(tempTemp > 1 && rainTemp > 1){
					tempTemp--;
					rainTemp--;
					bottomLeft = 1;
					bottomRight = 0.56f;
					topLeft = 1;
					topRight = 0.722f;
				}
				else if(tempTemp > 1 && rainTemp <= 1){
					tempTemp--;
					bottomLeft = 1;
					bottomRight = 1;
					topLeft = 1.2425f;
					topRight = 1;
				}
				else if(tempTemp <= 1 && rainTemp > 1){
					rainTemp--;
					bottomLeft = 0.7f;
					bottomRight = 0.1f;
					topLeft = 1;
					topRight = 0.56f;
				}
				else{
					bottomLeft = 1.25f;
					bottomRight = 0.7f;
					topLeft = 1;
					topRight = 1;
				}
				
				//Now for the calculations that determine what the final red ratio will be.
				float point1 = (bottomLeft - bottomRight)*rainTemp + bottomRight;
				float point2 = (topLeft - topRight)*rainTemp + topRight;
				float point3 = (topRight - bottomRight)*tempTemp + bottomRight;
				float point4 = (topLeft - bottomLeft)*tempTemp + bottomLeft;
				
				return (((point1 - point2)*tempTemp + point2)+((point3 - point4)*rainTemp + point4))/2f;
	}
	
	public static float getAverageBlueGrass(float rain, float temp){
		//for calculating R and B values, temporary variables
				float tempTemp = temp*2;
				float rainTemp = rain*2;
				
				//the blue ratio is calculated from a 3x3 table of ratios obtained from real-life images based on temperature and humidity. The in-game
				// temp and rain is used to find an average red ratio from the table
				// four of the 9 levels are used for any calculation, the following variables representing them
				float bottomLeft;
				float bottomRight;
				float topLeft;
				float topRight;
				
				if(tempTemp > 1 && rainTemp > 1){
					tempTemp--;
					rainTemp--;
					bottomLeft = 0.5f;
					bottomRight = 0.63f;
					topLeft = 0.5f;
					topRight = 0.42f;
				}
				else if(tempTemp > 1 && rainTemp <= 1){
					tempTemp--;
					bottomLeft = 0.54f;
					bottomRight = 0.5f;
					topLeft = 0.6425f;
					topRight = 0.5f;
				}
				else if(tempTemp <= 1 && rainTemp > 1){
					rainTemp--;
					bottomLeft = 0.1f;
					bottomRight = 0f;
					topLeft = 0.5f;
					topRight = 0.63f;
				}
				else{
					bottomLeft = 0.65f;
					bottomRight = 0.1f;
					topLeft = 0.54f;
					topRight = 0.5f;
				}
				
				//Now for the calculations that determine what the final red ratio will be.
				float point1 = (bottomLeft - bottomRight)*rainTemp + bottomRight;
				float point2 = (topLeft - topRight)*rainTemp + topRight;
				float point3 = (topRight - bottomRight)*tempTemp + bottomRight;
				float point4 = (topLeft - bottomLeft)*tempTemp + bottomLeft;
				
				return (((point1 - point2)*tempTemp + point2)+((point3 - point4)*rainTemp + point4))/2f;
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
		return /*(Helper.clamp_float(getTemp(x,z), 0, 25)/25f)**/8000*(float)Math.abs(Math.sin((((Math.min(Math.abs(z+0d),30000)/30000d)*(float)Math.PI/2f)+Math.PI/6f)*3));//manager.getRainfallLayerAt(x, z).floatdata1;
	}

	public static float getTerrainAdjustedRainfall(int x, int y, int z)
	{
		//float rain = manager.getRainfallLayerAt(x, z).floatdata1;
		float rain = (Helper.clamp_float(getTemp(x,z), 0, 25)/25f)*8000*(float)Math.abs(Math.sin((((Math.min(Math.abs(z+0d),30000)/30000d)*(float)Math.PI/2f)+Math.PI/6f)*3));
		ArrayList biomes = new ArrayList<TFCBiome>();
		biomes.add(TFCBiome.river);
		biomes.add(TFCBiome.ocean);
		biomes.add(TFCBiome.swampland);

		float rainMod = 1;

		float latitude = (float) ((Math.min(Math.abs(z+0d),30000)/30000d)*90);

		BiomeGenBase biome = null;


		if((latitude >=0 && latitude < 30)||latitude > 60){

			for(int i = 0; i < 16; i++)
			{
				biome = worldObj.getBiomeGenForCoords((x+1024)-(64*i), z);				
				if(biome.biomeID == TFCBiome.Mountains.biomeID)
					rainMod -= (i * 18);
				else if(biome.biomeID == TFCBiome.ocean.biomeID)
					rainMod += (i * 25);

			}
		}

		if(latitude >=30 && latitude <= 60){

			for(int i = 0; i < 16; i++)
			{
				biome = worldObj.getBiomeGenForCoords((x-1024)+(64*i), z);

				if(biome.biomeID == TFCBiome.Mountains.biomeID)
					rainMod -= (i * 18);
				else if(biome.biomeID == TFCBiome.ocean.biomeID)
					rainMod += (i * 25);

			}
		}

		

		float addMoisture = 1;
		for(int i = -2; i <= 2 && addMoisture == 1; i++)
		{
			for(int k = -2; k <= 2 && addMoisture == 1; k++)
			{
				biome = worldObj.getBiomeGenForCoords(x+(i * 8), z+(k * 8));
				if(biome.biomeID == TFCBiome.ocean.biomeID || biome.biomeID == TFCBiome.river.biomeID || biome.biomeID == TFCBiome.swampland.biomeID)
					addMoisture = 2f;
			}
		}


		return (rain + (rainMod * addMoisture))/2f;
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
		if(rain >= 500 && evt < 0.5 && manager.getBiomeGenAt(x, z).maxHeight < 0.15)
		{
			return true;
		}
		return false;
	}
}

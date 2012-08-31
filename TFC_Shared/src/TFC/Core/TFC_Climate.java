package TFC.Core;

import java.util.ArrayList;
import java.util.Random;

import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;

import net.minecraft.src.ChunkPosition;
import net.minecraft.src.World;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class TFC_Climate 
{
	public static World worldObj;
	public static TFCWorldChunkManager manager;

	/**
	 * All Temeprature related code
	 */

	protected static float getZFactor(int zCoord)
	{
		float factor = 0;
		float z = zCoord;

		//Clamp Z
		if(z > 40000) z = 40000;
		if(z < -40000) z = -40000;

		//Get the factor
		if(z > 0)
			factor = (40000-z)/40000;
		else
			factor = (-40000-z)/-40000;

		return factor;
	}

	protected static float getTemp(int x, int z)
	{
		float zTemp = getZFactor(z);
		zTemp = (zTemp * (getMaxTemperature()+35))-35;

		float rainMod = 1-manager.getRainfallLayerAt(x, z).floatdata1/8000;

		Random R = new Random((TFC_Time.currentDay + 1) * TFC_Time.currentYear);
		float mod = getMonthTempFactor(TFC_Time.currentMonth, x, z);
		float modLast = getMonthTempFactor(TFC_Time.lastMonth, x, z);
		int day2 = TFC_Time.getDayOfMonth();
		int hour = (int) TFC_Time.getHour()-3;

		if(hour < 0)
			hour = 23 + hour;
		float hourMod = 0;

		if(hour < 12)
			hourMod = ((float)hour/11) * 0.2F;
		else
			hourMod = 0.2F - (((float)(hour-12)/11) * 0.2F);

		float monthMod = 0;
		float temp = 0;

		float dailyTemp = WeatherManager.getInstance().getDailyTemp();

		if(modLast > mod)
		{
			monthMod = ((modLast-mod)/30)*day2;
			monthMod = (modLast - monthMod);

			temp += ((zTemp + dailyTemp));
			if(temp < 0)monthMod = 1 - monthMod;
			temp *= monthMod;
			temp += (hourMod*(zTemp + dailyTemp));
			temp += (8*rainMod);
		}
		else
		{
			monthMod = ((modLast-mod)/30)*day2;
			monthMod = (modLast + monthMod);

			temp += ((zTemp + dailyTemp));
			if(temp < 0)monthMod = 1 - monthMod;
			temp *= monthMod;
			temp += (hourMod*(zTemp + dailyTemp));
			temp += (8*rainMod);
		}

		return temp;
	}

	protected static float getTemp(int day, int x, int z)
	{
		float zTemp = getZFactor(z);
		zTemp *= (getMaxTemperature()+35)-35;

		float rainMod = 1-manager.getRainfallLayerAt(x, z).floatdata1/8000;

		Random R = new Random(day * TFC_Time.currentYear);
		float mod = getMonthTempFactor(TFC_Time.currentMonth, x, z);
		float modLast = getMonthTempFactor(TFC_Time.lastMonth, x, z);
		int day2 = day - ((day/30)*30);
		int hour = (int) 15;

		if(hour < 0)
			hour = 23 + hour;
		float hourMod = 0;

		if(hour < 12)
			hourMod = ((float)hour/11) * 0.2F;
		else
			hourMod = 0.2F - (((float)(hour-12)/11) * 0.2F);

		float monthMod = 0;
		float temp = 0;

		float dailyTemp = WeatherManager.getInstance().getDailyTemp();

		if(modLast > mod)
		{
			monthMod = ((modLast-mod)/30)*day2;
			monthMod = (modLast - monthMod);

			temp += ((zTemp + dailyTemp));
			if(temp < 0)monthMod = 1 - monthMod;
			temp *= monthMod;
			temp += (hourMod*(zTemp + dailyTemp));
			temp += (8*rainMod);
		}
		else
		{
			monthMod = ((modLast-mod)/30)*day2;
			monthMod = (modLast + monthMod);

			temp += ((zTemp + dailyTemp));
			if(temp < 0)monthMod = 1 - monthMod;
			temp *= monthMod;
			temp += (hourMod*(zTemp + dailyTemp));
			temp += (8*rainMod);
		}

		return temp;
	}

	protected static float getBioTemp(int day, int x, int z)
	{
		float zTemp = getZFactor(z);
		zTemp *= (getMaxTemperature()+35)-35;

		float rainMod = 1-manager.getRainfallLayerAt(x, z).floatdata1/8000;

		Random R = new Random(day * TFC_Time.currentYear);
		float mod = getMonthTempFactor(TFC_Time.currentMonth, x, z);
		float modLast = getMonthTempFactor(TFC_Time.lastMonth, x, z);
		int day2 = day - ((day/30)*30);

		float hourMod = 0.2f;

		float monthMod = 0;
		float temp = 0;

		if(modLast > mod)
		{
			monthMod = ((modLast-mod)/30)*day2;
			monthMod = (modLast - monthMod);

			temp += ((zTemp));
			if(temp < 0)monthMod = 1 - monthMod;
			temp *= monthMod;
			temp += (hourMod*(zTemp));
			temp += (8*rainMod);
		}
		else
		{
			monthMod = ((modLast-mod)/30)*day2;
			monthMod = (modLast + monthMod);

			temp += ((zTemp));
			if(temp < 0)monthMod = 1 - monthMod;
			temp *= monthMod;
			temp += (hourMod*(zTemp));
			temp += (8*rainMod);
		}

		return temp;
	}

	protected static float getMonthTempFactor(int month, int x, int z)
	{
		float factor = getZFactor(z);

		float diff = 1-factor;

		switch(month)
		{
		case 11:
			return 1-((diff/6)*4);
		case 0:
			return 1-((diff/6)*3);
		case 1:
			return 1-((diff/6)*2);
		case 2:
			return 1-((diff/6)*1);
		case 3:
			return 1-((diff/6)); 
		case 4:
			return 1F;
		case 5:
			return 1-((diff/6)*1.2f);
		case 6:
			return 1-((diff/6)*2.4f);
		case 7:
			return 1-((diff/6)*3.6f);
		case 8:
			return 1-((diff/6)*4.8f);
		case 9:
			return factor;
		case 10:
			return 1-((diff/6)*5);
		default:
			return 1;
		}
	}

	protected static float getTempSpecificDay(int day, int x, int z)
	{
		return getTemp(day+1, x, z);
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
			temp -= temp * (y-180)/90;

		return temp;
	}

	public static float adjustHeightToTemp(int y, float temp)
	{
		if(y > 180)
			temp -= temp * (y-180)/90;

		return temp;
	}

	public static float getHeightAdjustedTempSpecificDay(int day, int x, int y, int z)
	{
		float temp = getTempSpecificDay(day, x, z);

		if(y > 180)
			temp -= temp * (y-180)/90;

		return temp;
	}

	public static float getHeightAdjustedBioTemp(int day, int x, int y, int z)
	{
		float temp = getBioTemp(day, x, z);

		if(y > 180)
			temp -= temp * (y-180)/90;

		return temp;
	}

	public static float getMaxTemperature()
	{
		return 24;
	}

	public static float getBioTemperatureHeight(int x, int y, int z)
	{
		float temp = 0;
		for(int i = 0; i < 24; i++)
		{
			float t = getHeightAdjustedBioTemp(i*15, x, y, z);
			if(t < 0)
				t = 0;

			temp += t;
		}
		return temp/24;
	}

	public static float getBioTemperature(int x, int z)
	{
		float temp = 0;
		for(int i = 0; i < 24; i++)
		{
			float t = getBioTemp(i*15, x, z);
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
		if(TFC_Time.currentMonth < 6)
		{
			double var1 = (double)Helper.clamp_float(getTemp(x, z)/getMaxTemperature(), 0.0F, 1.0F);

			double[] var3 = new double[5];

			var3[0] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z).floatdata1 / 8000, 0.0F, 1.0F);
			var3[1] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x+1, z).floatdata1 / 8000, 0.0F, 1.0F);
			var3[2] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x-1, z).floatdata1 / 8000, 0.0F, 1.0F);
			var3[3] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z+1).floatdata1 / 8000, 0.0F, 1.0F);
			var3[4] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z-1).floatdata1 / 8000, 0.0F, 1.0F);
			//var3[5] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x+5, z).floatdata1 / 8000, 0.0F, 1.0F);
			//var3[6] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x-5, z).floatdata1 / 8000, 0.0F, 1.0F);
			//var3[7] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z+5).floatdata1 / 8000, 0.0F, 1.0F);
			//var3[8] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z-5).floatdata1 / 8000, 0.0F, 1.0F);

			double var4 = (var3[0]+var3[1]+var3[2]+var3[3]+var3[4]/*+var3[5]+var3[6]+var3[7]+var3[8]*/)/5;
			//double var4 = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getRainfallLayerAt(x, z).floatdata1 / 8000, 0.0F, 1.0F);

			return ColorizerGrassTFC.getGrassColor(var1, var4);
		}
		else
			return ColorizerGrassTFC.getGrassDead();
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic foliage color based on the biome temperature and rainfall
	 */
	public static int getFoliageColor(World world, int x, int y, int z)
	{
		if(TFC_Time.currentMonth < 9)
		{
			float temp = getTemp(x, z)/getMaxTemperature();
			//float evt = (1 - (((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z).floatdata1 / 16))*0.5f;
			float rain = (((TFCWorldChunkManager)world.provider.worldChunkMgr).getRainfallLayerAt(x, z).floatdata1 / 8000);
			double var1 = (double)Helper.clamp_float(temp, 0.0F, 1.0F);
			double var3 = (double)Helper.clamp_float(rain, 0.0F, 1.0F);
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
		biomes.add(TFCBiome.Mountains);
		
		float rainMod = 1;
		ChunkPosition pos = null;
		
		for(int i = 0; i < 8; i++)
		{
			pos = manager.findBiomePosition((x-512)+(64*i), z, 1, biomes, worldObj.rand);
			if(pos != null)
			{
				if(worldObj.getBiomeGenForCoords(pos.x, pos.z) == TFCBiome.Mountains)
					rainMod = 1 - (i * 0.125f);
				else if(worldObj.getBiomeGenForCoords(pos.x, pos.z) == TFCBiome.ocean)
					rainMod = 1 + (i * 0.125f);
			}
		}
		return rain*rainMod;
	}
}

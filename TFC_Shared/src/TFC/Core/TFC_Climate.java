package TFC.Core;

import java.util.Random;

import TFC.WorldGen.TFCWorldChunkManager;

import net.minecraft.src.World;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class TFC_Climate 
{
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

	protected static float getTemp(int z)
	{
		float zTemp = getZFactor(z);
		zTemp *= (getMaxTemperature()+35)-35;
		
		Random R = new Random((TFC_Time.currentDay + 1) * TFC_Time.currentYear);
		float mod = getMonthTempFactor(TFC_Time.currentMonth, z);
		float modLast = getMonthTempFactor(TFC_Time.lastMonth , z);
		int day2 = TFC_Time.getDayOfMonth();
		int hour = (int) TFC_Time.getHour()-3;

		if(hour < 0)
			hour = 23 + hour;
		float hourMod = 0;

		if(hour < 12)
			hourMod = ((float)hour/11) * 0.2F;
		else
			hourMod = 0.2F - (((float)(hour-12)/11) * 0.2F);

		float m = 0;
		float temp = 0;

		if(modLast > mod)
		{
			m = ((modLast-mod)/30)*day2;
			m = (modLast - m);
			temp = ((zTemp+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(zTemp+WeatherManager.getInstance().getDailyTemp())); 
		}
		else
		{
			m = ((mod-modLast)/30)*day2;
			m = (modLast + m);
			temp = ((zTemp+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(zTemp+WeatherManager.getInstance().getDailyTemp()));
		}

		return temp;
	}
	
	protected static float getTemp(int day, int z)
	{
		float zTemp = getZFactor(z);
		zTemp *= (getMaxTemperature()+35)-35;
		
		Random R = new Random(day * TFC_Time.currentYear);
		float mod = getMonthTempFactor(TFC_Time.currentMonth, z);
		float modLast = getMonthTempFactor(TFC_Time.lastMonth , z);
		int day2 = day - ((day/30)*30);
		int hour = (int) 15;

		if(hour < 0)
			hour = 23 + hour;
		float hourMod = 0;

		if(hour < 12)
			hourMod = ((float)hour/11) * 0.2F;
		else
			hourMod = 0.2F - (((float)(hour-12)/11) * 0.2F);

		float m = 0;
		float temp = 0;

		if(modLast > mod)
		{
			m = ((modLast-mod)/30)*day2;
			m = (modLast - m);
			temp = ((zTemp+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(zTemp+WeatherManager.getInstance().getDailyTemp(day))); 
		}
		else
		{
			m = ((mod-modLast)/30)*day2;
			m = (modLast + m);
			temp = ((zTemp+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(zTemp+WeatherManager.getInstance().getDailyTemp(day)));
		}

		return temp;
	}
	
	protected static float getBioTemp(int day, int z)
	{
		float zTemp = getZFactor(z);
		zTemp *= (getMaxTemperature()+35)-35;
		
		Random R = new Random(day * TFC_Time.currentYear);
		float mod = getMonthTempFactor(TFC_Time.currentMonth, z);
		float modLast = getMonthTempFactor(TFC_Time.lastMonth , z);
		int day2 = day - ((day/30)*30);

		float hourMod = 0.2f;

		float m = 0;
		float temp = 0;

		if(modLast > mod)
		{
			m = ((modLast-mod)/30)*day2;
			m = (modLast - m);
			temp = ((zTemp+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(zTemp)); 
		}
		else
		{
			m = ((mod-modLast)/30)*day2;
			m = (modLast + m);
			temp = ((zTemp+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(zTemp));
		}

		return temp;
	}

	protected static float getMonthTempFactor(int month, int z)
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
	
	protected static float getTempSpecificDay(int z, int day)
	{
		return getTemp(day+1, z);
	}

	public static float getHeightAdjustedTemp(int y, int z)
	{
		float temp = getTemp(z);

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
	
	public static float getHeightAdjustedTempSpecificDay(int y, int z, int day)
	{
		float temp = getTempSpecificDay(day, z);

		if(y > 180)
			temp -= temp * (y-180)/90;
		
		return temp;
	}
	
	public static float getHeightAdjustedBioTemp(int day, int y, int z)
	{
		float temp = getBioTemp(day, z);

		if(y > 180)
			temp -= temp * (y-180)/90;
		
		return temp;
	}
	
	public static float getMaxTemperature()
	{
		return 37;
	}
	
	public static float getBioTemperatureHeight(int y, int z)
	{
		float temp = 0;
		for(int i = 0; i < 24; i++)
		{
			float t = getHeightAdjustedBioTemp(i*15, y, z);
			if(t < 0)
				t = 0;
			
			temp += t;
		}
		return temp/24;
	}
	
	public static float getBioTemperature(int z)
	{
		float temp = 0;
		for(int i = 0; i < 24; i++)
		{
			float t = getBioTemp(i*15, z);
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
			double var1 = (double)Helper.clamp_float(getTemp(z)/getMaxTemperature(), 0.0F, 1.0F);
			
			double[] var3 = new double[9];
			
			var3[0] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z).floatdata1, 0.0F, 1.0F);
			var3[1] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x+10, z).floatdata1, 0.0F, 1.0F);
			var3[2] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x-10, z).floatdata1, 0.0F, 1.0F);
			var3[3] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z+10).floatdata1, 0.0F, 1.0F);
			var3[4] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z-10).floatdata1, 0.0F, 1.0F);
			var3[5] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x+5, z).floatdata1, 0.0F, 1.0F);
			var3[6] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x-5, z).floatdata1, 0.0F, 1.0F);
			var3[7] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z+5).floatdata1, 0.0F, 1.0F);
			var3[8] = (double)Helper.clamp_float(((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z-5).floatdata1, 0.0F, 1.0F);
			
			double var4 = (var3[0]+var3[1]+var3[2]+var3[3]+var3[4]+var3[5]+var3[6]+var3[7]+var3[8])/9;
			
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
			float temp = getTemp(z)/getMaxTemperature();
			float humidity = ((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(x, z).floatdata1;
			double var1 = (double)Helper.clamp_float(temp, 0.0F, 1.0F);
			double var3 = (double)Helper.clamp_float(humidity, 0.0F, 1.0F);
			return ColorizerFoliageTFC.getFoliageColor(var1, var3);
		}
		else
			return ColorizerFoliageTFC.getFoliageDead();
	}
}

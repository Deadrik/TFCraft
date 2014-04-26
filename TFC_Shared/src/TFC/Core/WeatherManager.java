package TFC.Core;

import java.util.Random;


public class WeatherManager
{
	protected static final WeatherManager instance = new WeatherManager();
	private static Random rand = new Random();
	public static final WeatherManager getInstance()
	{
		return instance;
	}
	public long seed = 0;

	public WeatherManager()
	{

	}

	public float getDailyTemp()
	{
		rand.setSeed(seed+TFC_Time.getTotalDays());

		return (rand.nextInt(200)-100)/10;
	}

	public float getDailyTemp(int day)
	{
		rand.setSeed(seed + day);

		return (rand.nextInt(200)-100)/20;
	}

	public float getWeeklyTemp(int week)
	{
		rand.setSeed(seed + week);

		return (rand.nextInt(200)-100)/10;
	}

	public static int getDayOfWeek(long day)
	{        
		long days = day / 6;
		long days2 = day - (days*6);
		return (int)days2;
	}

	public static boolean canSnow(int x, int y, int z)
	{
		if(TFC_Climate.getHeightAdjustedTemp(x, y, z) <= 0)
			return true;
		return false;
	}  
}

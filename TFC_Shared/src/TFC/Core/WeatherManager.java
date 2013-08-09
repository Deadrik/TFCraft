package TFC.Core;

import java.util.Random;

import TFC.TerraFirmaCraft;


public class WeatherManager
{
    protected static final WeatherManager instance = new WeatherManager();
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
        Random r = new Random(seed+TFC_Time.getTotalDays());

        return (r.nextInt(200)-100)/10;
    }
    
    public float getDailyTemp(int day)
    {
        Random r = new Random(seed + day);

        return (r.nextInt(200)-100)/20;
    }
    
    public float getWeeklyTemp(int week)
    {
        Random r = new Random(seed + week);

        return (r.nextInt(200)-100)/10;
    }
    
    public static int getDayOfWeek(long day)
    {        
        long days = day / 6;
        long days2 = day - (days*6);
        return (int)days2;
    }
    
}

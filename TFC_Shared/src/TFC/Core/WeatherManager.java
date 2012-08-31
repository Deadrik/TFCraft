package TFC.Core;

import java.util.Random;

import net.minecraft.src.TerraFirmaCraft;

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
        Random r = new Random(seed+TFC_Time.totalDays());

        return (r.nextInt(200)-100)/10;
    }
    
    public float getDailyTemp(int day)
    {
        Random r = new Random(seed + day);

        return (r.nextInt(200)-100)/10;
    }
    
}

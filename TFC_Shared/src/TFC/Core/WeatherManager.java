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
        
        float temp = (r.nextInt(100)-50)/10;

        return temp;
    }
    
    public float getDailyTemp(int day)
    {
        Random r = new Random(seed + day);
        
        float temp = (r.nextInt(100)-50)/10;

        return temp;
    }
    
}

package TFC.Core;

import java.util.Random;

import net.minecraft.src.mod_TFC;

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
        Random r = new Random(seed+TFCSeasons.totalDays());
        
        float temp = (r.nextInt(100)-50)/10;

        return temp;
    }
    
}

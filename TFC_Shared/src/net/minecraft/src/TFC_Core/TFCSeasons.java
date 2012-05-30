package net.minecraft.src.TFC_Core;

import net.minecraft.src.World;

public class TFCSeasons
{
    public static String[] seasons = { "Spring", "Summer", "Autumn", "Winter"};
    public static String[] Days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static int currentSeason = 0;
    
    public static String getSeason()
    {
        return seasons[0];
    }
    
    public static int getDayOfWeek(World world)
    {
        long t = world.getWorldInfo().getWorldTime();
        long day = (t/36000)+1;
        long month = day / 30;
        long year = month / 12;
        
        long days = day / 7;
        long days2 = day - (days*7);
        return (int)days2;
    }
}

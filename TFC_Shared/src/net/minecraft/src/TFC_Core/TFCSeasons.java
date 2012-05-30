package net.minecraft.src.TFC_Core;

import net.minecraft.src.World;

public class TFCSeasons
{
    public static String[] seasons = { "Early Spring","Spring","Late Spring", "Early Summer", "Summer", "Late Summer", "Early Autumn", "Autumn", "Late Autumn", "Early Winter", "Winter", "Late Winter"};
    public static String[] months  = { "March","April","May", "June", "July", "August", "September", "October", "November", "December", "January", "February"};
    public static String[] Days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static int currentSeason = 0;
    
    public static String getSeason()
    {
        return seasons[0];
    }
    
    public static int getDayOfWeek(World world)
    {
        long t = world.getWorldInfo().getWorldTime();
        long day = totalDays(world)+1;
        
        long days = day / 7;
        long days2 = day - (days*7);
        return (int)days2;
    }
    
    public static long totalDays(World world)
    {
        long t = world.getWorldInfo().getWorldTime();
        return (t/36000);
    }
    
    public static long totalMonths(World world)
    {
        return totalDays(world) / 30;
    }
    
    public static long totalYears(World world)
    {
        return totalMonths(world) / 12;
    }
}

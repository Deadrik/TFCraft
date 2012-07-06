package TFC.Core;

import net.minecraft.src.World;

public class TFCSeasons
{
    public static String[] seasons = { "Early Spring","Spring","Late Spring", "Early Summer", "Summer", "Late Summer", "Early Autumn", "Autumn", "Late Autumn", "Early Winter", "Winter", "Late Winter"};
    public static String[] months  = { "March","April","May", "June", "July", "August", "September", "October", "November", "December", "January", "February"};
    public static String[] Days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    
    public static int currentDay = 0;
    public static int lastMonth = 11;
    public static int currentMonth = 0;
    public static int currentYear = 0;
    private static long time = 0;
    
    public static final int January = 10;
    public static final int February = 11;
    public static final int March = 0;
    public static final int April = 1;
    public static final int May = 2;
    public static final int June = 3;
    public static final int July = 4;
    public static final int August = 5;
    public static final int September = 6;
    public static final int October = 7;
    public static final int November = 8;
    public static final int December = 9;
    
    
    public static void UpdateSeasons(World world)
    {
        time = world.getWorldInfo().getWorldTime();
        int m = getMonth();
        int m1 = m - 1;
        
        if(m1 < 0)
            m1 = 11;
        
        lastMonth = m1;
        currentMonth = m;
        currentYear = getYear();
    }
    
    public static String getSeason()
    {
        return seasons[getMonth()];
    }
    
    public static long getTotalTicks()
    {
        return time;
    }
    
    public static int getDayOfWeek()
    {
        long day = totalDays()+1;
        
        long days = day / 7;
        long days2 = day - (days*7);
        return (int)days2;
    }
    
    public static int getDayOfMonth()
    {
        long month = totalMonths();
        
        long days = 30*month;
        long days2 = totalDays() - days;
        return 1+(int)days2;
    }
    
    public static int getDayOfYear()
    {
        long year = getYear();
        
        long years = 12960000*year;
        long years2 = time - years;;
        return (int) (years2/24000);
    }
    
    public static int getMonth()
    {
        long totalmonths = totalMonths();
        long totalmonths2 = totalmonths / 12;
        long totalmonths3 = totalmonths-(totalmonths2*12);
        return (int)totalmonths3;
    }
    
    public static int getYear()
    {
        long totalmonths = totalMonths();
        long totalmonths2 = totalmonths / 12;
        return (int)totalmonths2;
    }
    
    public static long totalDays()
    {
        return (time/24000);
    }
    
    public static long totalMonths()
    {
        return totalDays() / 30;
    } 
    
    public static long totalYears()
    {
        return totalMonths() / 12;
    }
    
    public static long getHour()
    {
        long h = (time - ((time / 24000)*24000))/1000;
        h -= 6;
        if(h < 0)
            h = 23 + h;
        h -= 12;
        if(h < 0)
            h = 23+h;
        return  h;
    }
}

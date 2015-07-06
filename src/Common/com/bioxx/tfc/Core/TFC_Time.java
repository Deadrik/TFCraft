package com.bioxx.tfc.Core;

import net.minecraft.world.World;

import com.bioxx.tfc.api.TFCOptions;


public class TFC_Time
{
	public static String[] SEASONS = { TFC_Core.translate("gui.Calendar.EarlySpring"),
		TFC_Core.translate("gui.Calendar.Spring"), TFC_Core.translate("gui.Calendar.LateSpring"),
		TFC_Core.translate("gui.Calendar.EarlySummer"), TFC_Core.translate("gui.Calendar.Summer"),
		TFC_Core.translate("gui.Calendar.LateSummer"), TFC_Core.translate("gui.Calendar.EarlyAutumn"),
		TFC_Core.translate("gui.Calendar.Autumn"), TFC_Core.translate("gui.Calendar.LateAutumn"),
		TFC_Core.translate("gui.Calendar.EarlyWinter"), TFC_Core.translate("gui.Calendar.Winter"),
		TFC_Core.translate("gui.Calendar.LateWinter")};
	public static String[] MONTHS  = { TFC_Core.translate("gui.Calendar.March"),
		TFC_Core.translate("gui.Calendar.April"),TFC_Core.translate("gui.Calendar.May"),
		TFC_Core.translate("gui.Calendar.June"), TFC_Core.translate("gui.Calendar.July"),
		TFC_Core.translate("gui.Calendar.August"), TFC_Core.translate("gui.Calendar.September"),
		TFC_Core.translate("gui.Calendar.October"), TFC_Core.translate("gui.Calendar.November"),
		TFC_Core.translate("gui.Calendar.December"), TFC_Core.translate("gui.Calendar.January"),
		TFC_Core.translate("gui.Calendar.February")};
	public static String[] DAYS = { TFC_Core.translate("gui.Calendar.Sunday"),
		TFC_Core.translate("gui.Calendar.Monday"), TFC_Core.translate("gui.Calendar.Tuesday"),
		TFC_Core.translate("gui.Calendar.Wednesday"), TFC_Core.translate("gui.Calendar.Thursday"),
		TFC_Core.translate("gui.Calendar.Friday"), TFC_Core.translate("gui.Calendar.Saturday")};

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

	public static final long hourLength = 1000;
	public static final int dayLength = 24000;
	public static final int hoursInDay = (int) (dayLength / hourLength);

	/**
	 * This is the year length ratio for use when your numbers are based on a 360 day year and 
	 * you want to bring it in line with the current year length.
	 */
	public static float timeRatio360 = TFCOptions.yearLength/360f;
	/**
	 * This is the year length ratio for use when your numbers are based on a 96 day year and 
	 * you want to bring it in line with the current year length.
	 */
	public static float timeRatio96 = TFCOptions.yearLength/96f;

	public static int daysInYear = TFCOptions.yearLength;
	public static int daysInMonth = daysInYear/12;
	public static long ticksInYear = daysInYear * dayLength;
	public static long ticksInMonth = daysInMonth * dayLength;
	public static long startTime = ticksInMonth * 3;

	public static void setYearLength(int length)
	{
		daysInYear = length;
		daysInMonth = daysInYear/12;
		ticksInYear = daysInYear * dayLength;
		ticksInMonth = daysInMonth * dayLength;
		startTime = ticksInMonth * 3;
	}

	public static void UpdateTime(World world)
	{
		time = world.getWorldInfo().getWorldTime();

		if(time < startTime)
		{
			world.getWorldInfo().setWorldTime(startTime);
			world.getWorldInfo().incrementTotalWorldTime(startTime);
		}

		int m = getMonth();
		int m1 = m - 1;

		if(m1 < 0)
			m1 = 11;

		lastMonth = m1;
		currentDay = getDayOfYear();
		currentMonth = m;
		currentYear = getYear();
	}

	public static String getDateStringFromHours(int tHours)
	{
		int tDays = tHours / hoursInDay;

		int day = tDays % daysInMonth;
		int tMonths = tDays / daysInMonth;

		int month = tMonths % 12;
		int year = tMonths / 12;

		// time messed up by another mod?
		if (tHours < 0) {
		    day += daysInMonth - 1;  // -1 to compensate negative hour
		    month += 12 - 1;  // -1 to compensate daysInMonth added above
		    year -= 1;  // to compensate the 12 months added above
		}

		// year changes at January, not March
		if (month >= January) {
		    year += 1;
		}

		int d = day + 1;
		String m = TFC_Time.MONTHS[month];
		int y = 1000 + year;

		String date = d + " " + m + ", " + y;

		return date;
	}

	public static int getHoursInMonth()
	{
		return hoursInDay*daysInMonth;
	}

	public static String getSeason()
	{
		return SEASONS[getMonth()];
	}

	public static long getTotalTicks()
	{
		return time;
	}

	public static int getDayOfWeek()
	{
		long day = getTotalDays()+1;
		long days = day / 7;
		long days2 = day - (days*7);
		return (int)days2;
	}

	public static int getDayOfWeek(int tDays)
	{
		long day = tDays+1;
		long days = day / 7;
		long days2 = day - (days*7);
		return (int)days2;
	}

	public static int getDayOfMonth()
	{
		long month = getTotalMonths();
		long days = daysInMonth*month;
		long days2 = getTotalDays() - days;
		return 1+(int)days2;
	}

	public static int getDayOfMonth(int tDays)
	{
		int months = tDays/daysInMonth;
		int rem = tDays-(months*daysInMonth);
		return 1+rem;
	}

	public static int getDayOfYear()
	{
		long year = getYear();
		long years = (ticksInYear)*year;
		long years2 = time - years;
		return (int) (years2/dayLength);
	}

	public static int getDayOfYearFromTick(long tick)
	{
		long years = (tick / (ticksInYear));
		long years2 = tick - (ticksInYear * years);
		return (int) (years2/dayLength);
	}

	public static int getDayOfYearFromDays(long days)
	{
		long years = (days / daysInYear);
		long years2 = days - (daysInYear * years);
		return (int)years2;
	}

	/**Explicit month value, use getSeason(int zCoord) for anything related to a season, ie Summer, Winter etc.
	 * 
	 * @return		explicit month value
	 */
	public static int getMonth()
	{
		long totalmonths = getTotalMonths();
		long totalmonths2 = totalmonths / 12;
		long totalmonths3 = totalmonths-(totalmonths2 * 12);
		return (int)totalmonths3;
	}
	/**Southern hemisphere reverses the season. Use getMonth() for the explicit month
	 * 
	 * @param z		integer z-coordinate
	 * @return		adjusted season value
	 */
	public static int getSeasonAdjustedMonth(int z)
	{
		if(z > 0)
			return (getMonth()+6)%12;
		return getMonth();
	}

	public static int getYear()
	{
		long totalmonths = getTotalMonths();
		long totalmonths2 = totalmonths / 12;
		return (int)totalmonths2;
	}

	public static int getTotalDays()
	{
		return (int)Math.floor(((float)time/(float)dayLength));
	}

	public static long getTotalHours()
	{
		return (time/hourLength);
	}

	public static long getTotalMonths()
	{
		return getTotalDays() / daysInMonth;
	} 

	public static long getTotalYears()
	{
		return getTotalMonths() / 12;
	}

	public static int getHour()
	{
		int th = (int) getTotalHours();
		int h = getHourOfDayFromTotalHours(th);
		return h;
	}

	public static int getHourOfDayFromTotalHours(int th)
	{
		int h = (th + 6) % hoursInDay;  //gives us the remainder, days start at 6:00
		return h;
	}

	public static int getDayFromTotalHours(int th)
	{
		return th / hoursInDay;
	}

	public static int getDayFromTotalHours(long th)
	{
		return (int) (th / hoursInDay);
	}

	public static boolean isSpring(int z)
	{
		int day = (getDayOfYear() + (z > 0 ? (daysInYear) / 2 : 0)) % daysInYear;
		return (day >= 20 && day <= 111);
	}

	public static boolean isSummer(int z)
	{
		int day = (getDayOfYear() + (z > 0 ? (daysInYear) / 2 : 0)) % daysInYear;
		return (day >= 112 && day <= 202);
	}

	public static boolean isFall(int z)
	{
		int day = (getDayOfYear() + (z > 0 ? (daysInYear) / 2 : 0)) % daysInYear;
		return (day >= 203 && day <= 293);
	}

	public static boolean isWinter(int z)
	{
		int day = (getDayOfYear() + (z > 0 ? (daysInYear) / 2 : 0)) % daysInYear;
		return (day >= 294 || day < 20);
	}

	public static int getMonthFromDayOfYear(int day)
	{
		if(day < 0)
			day = daysInYear + day;
		return day / (daysInMonth);
	}

	/**
	 * Season is reversed in southern Hemisphere
	 * @param day		day of year
	 * @param z			integer z-coordinate
	 * @return			adjusted season value
	 */
	public static int getSeasonFromDayOfYear(int day, int z)
	{
		if(day < 0)
			day = daysInYear + day;
		return ((day / (daysInMonth))+(z > 0 ? 6 : 0)) % 12;
	}

	public static int getDayOfMonthFromDayOfYear(int day)
	{
		if(day < 0)
			day = daysInYear + day;
		return (day - ((int)Math.floor((day / daysInMonth)) * daysInMonth));
	}

	public static int getPrevMonth()
	{
		return lastMonth;
	}

	public static int getPrevMonth(int month)
	{
		if(month == 0)
			return 11;
		return month - 1;
	}

	public static float getYearRatio(float expectedDays)
	{
		return expectedDays / daysInYear;
	}

	public static int getMonthsSinceDay(int totalDay)
	{
		int days = TFC_Time.getTotalDays() - totalDay;
		return days / TFC_Time.daysInMonth;
	}

}

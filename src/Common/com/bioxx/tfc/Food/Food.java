package com.bioxx.tfc.Food;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Food 
{
	public static final int DRYHOURS = 4;

	private static NBTTagCompound getProcTag(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("Processing Tag"))
		{
			return (NBTTagCompound) is.getTagCompound().getTag("Processing Tag");
		}
		else
			return new NBTTagCompound();
	}

	private static void setProcTag(ItemStack is, NBTTagCompound nbt)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setTag("Processing Tag", nbt);
	}

	public static boolean isBrined(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.getBoolean("Brined");
	}

	public static void setBrined(ItemStack is, boolean value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setBoolean("Brined", value);
		setProcTag(is, nbt);
	}

	public static boolean isPickled(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.getBoolean("Pickled");
	}

	public static void setPickled(ItemStack is, boolean value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setBoolean("Pickled", value);
		setProcTag(is, nbt);
	}

	public static boolean isSalted(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.getBoolean("Salted");
	}

	public static void setSalted(ItemStack is, boolean value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setBoolean("Salted", value);
		setProcTag(is, nbt);
	}

	public static boolean isCooked(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.getFloat("Cooked") > 600;
	}

	public static float getCooked(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.getFloat("Cooked");
	}

	public static void setCooked(ItemStack is, float value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setFloat("Cooked", value);
		setProcTag(is, nbt);
	}

	public static int[] getCookedProfile(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		if(nbt.hasKey("CookedProfile"))
			return nbt.getIntArray("CookedProfile");
		return new int[] {0,0,0,0,0};
	}

	public static void setCookedProfile(ItemStack is, int[] value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setIntArray("CookedProfile", value);
		setProcTag(is, nbt);
	}

	public static int[] getFuelProfile(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		if(nbt.hasKey("FuelProfile"))
			return nbt.getIntArray("FuelProfile");
		return new int[] {0,0,0,0,0};
	}

	public static void setFuelProfile(ItemStack is, int[] value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setIntArray("FuelProfile", value);
		setProcTag(is, nbt);
	}

	public static boolean isSmoked(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.hasKey("FuelProfile") && !isSameSmoked(getFuelProfile(is), new int[] {0,0,0,0,0});
	}

	public static boolean isSameSmoked(ItemStack is1, ItemStack is2)
	{
		int[] f1 = getFuelProfile(is1);
		int[] f2 = getFuelProfile(is2);
		if(f1[0] != f2[0] || f1[1] != f2[1] || f1[2] != f2[2] || f1[3] != f2[3] || f1[4] != f2[4])
			return false;
		return true;
	}

	public static boolean isSameSmoked(int[] f1, int[] f2)
	{
		if(f1[0] != f2[0] || f1[1] != f2[1] || f1[2] != f2[2] || f1[3] != f2[3] || f1[4] != f2[4])
			return false;
		return true;
	}

	public static void setDecay(ItemStack is, float value)
	{
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setFloat("foodDecay", value);
	}

	public static float getDecay(ItemStack is)
	{
		NBTTagCompound nbt = is.getTagCompound();
		return nbt.getFloat("foodDecay");
	}

	public static void setWeight(ItemStack is, float value)
	{
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setFloat("foodWeight", value);
	}

	public static float getWeight(ItemStack is)
	{
		NBTTagCompound nbt = is.getTagCompound();
		return nbt.getFloat("foodWeight");
	}

	public static boolean isDried(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.getShort("Dried") >= DRYHOURS;
	}

	public static short getDried(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.getShort("Dried");
	}

	public static void setDried(ItemStack is, int value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setShort("Dried", (short)value);
		setProcTag(is, nbt);
	}

	public static short getSmokeCounter(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.getShort("SmokeCounter");
	}

	public static void setSmokeCounter(ItemStack is, int value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setShort("SmokeCounter", (short)value);
		setProcTag(is, nbt);
	}

	public static int getCookedColorMultiplier(ItemStack is)
	{
		float cookedLevel = Food.getCooked(is);
		int r = 255 - (int)(160 * (Math.max(cookedLevel-600, 0) / 600f)); 
		int b = 255 - (int)(160 * (Math.max(cookedLevel-600, 0) / 600f));
		int g = 255 - (int)(160 * (Math.max(cookedLevel-600, 0) / 600f));
		int rbg = (r << 16) + (b << 8) + g;
		return rbg;
	}
}

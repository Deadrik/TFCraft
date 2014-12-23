package com.bioxx.tfc.api;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.api.Interfaces.IFood;

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

	public static boolean areEqual(ItemStack is1, ItemStack is2)
	{
		if(isBrined(is1) != isBrined(is2))
			return false;
		if(isPickled(is1) != isPickled(is2))
			return false;
		if(isCooked(is1) != isCooked(is2))
			return false;
		if(isDried(is1) != isDried(is2))
			return false;
		if(isSmoked(is1) != isSmoked(is2))
			return false;
		if(isSalted(is1) != isSalted(is2))
			return false;
		return true;
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
		if(value > getWeight(is))
			is.stackSize = 0;
	}

	public static float getDecay(ItemStack is)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if(nbt != null)
			return nbt.getFloat("foodDecay");
		else return 0;
	}

	public static void setDecayTimer(ItemStack is, int value)
	{
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setInteger("decayTimer", value);
	}

	public static int getDecayTimer(ItemStack is)
	{
		NBTTagCompound nbt = is.getTagCompound();
		return nbt.getInteger("decayTimer");
	}

	public static void setWeight(ItemStack is, float value)
	{
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setFloat("foodWeight", value);
		if(getDecay(is) > value || value <= 0)
			is.stackSize = 0;
	}

	public static float getWeight(ItemStack is)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if(nbt != null)
			return nbt.getFloat("foodWeight");
		else return 0;
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

	public static void setSweetMod(ItemStack is, int val)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setInteger("tasteSweetMod", val);
	}

	public static void setSourMod(ItemStack is, int val)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setInteger("tasteSourMod", val);
	}

	public static void setSaltyMod(ItemStack is, int val)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setInteger("tasteSaltyMod", val);
	}

	public static void setBitterMod(ItemStack is, int val)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setInteger("tasteBitterMod", val);
	}

	public static void setSavoryMod(ItemStack is, int val)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setInteger("tasteUmamiMod", val);
	}

	public static void adjustFlavor(ItemStack is, Random R)
	{
		Food.setSweetMod(is, R.nextInt(17) - 8);
		Food.setSourMod(is, R.nextInt(17) - 8);
		Food.setSaltyMod(is, R.nextInt(17) - 8);
		Food.setBitterMod(is, R.nextInt(17) - 8);
		Food.setSavoryMod(is, R.nextInt(17) - 8);
	}

	public static void setMealSkill(ItemStack is, int val)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setInteger("mealSkill", val);
	}

	public static int getMealSkill(ItemStack is)
	{
		return is.getTagCompound().getInteger("mealSkill");
	}

	public static boolean hasMealSkill(ItemStack is)
	{
		return is.getTagCompound().hasKey("mealSkill");
	}

	public static int[] getFoodTasteProfile(ItemStack is)
	{
		int[] profile = new int[5];
		if(is != null && is.getItem() instanceof IFood)
		{
			profile[0] = ((IFood)is.getItem()).getTasteSweet(is);
			profile[1] = ((IFood)is.getItem()).getTasteSour(is);
			profile[2] = ((IFood)is.getItem()).getTasteSalty(is);
			profile[3] = ((IFood)is.getItem()).getTasteBitter(is);
			profile[4] = ((IFood)is.getItem()).getTasteSavory(is);
		}
		return profile;
	}

	public static boolean isInfused(ItemStack is)
	{
		if (is.hasTagCompound())
			return is.getTagCompound().hasKey("Infusion");
		else
			return false;
	}

	public static String getInfusion(ItemStack is)
	{
		return is.getTagCompound().getString("Infusion");
	}

	public static void setInfusion(ItemStack is, String val)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setString("Infusion", val);
	}
}

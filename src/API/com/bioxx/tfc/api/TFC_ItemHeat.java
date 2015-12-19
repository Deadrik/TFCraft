package com.bioxx.tfc.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.Core.TFC_Core;

public class TFC_ItemHeat 
{
	public static String getHeatColor(float temp, float meltTemp)
	{
		String phrase = "";
		if(temp < 80)
		{
			phrase = TFC_Core.translate("gui.ItemHeat.Warming");
			if(temp>(80 * 0.2))
				phrase = phrase + "\u2605";
			if(temp>(80 * 0.4))
				phrase = phrase + "\u2605";
			if(temp>(80 * 0.6))
				phrase = phrase + "\u2605";
			if(temp>(80 * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 80 && temp < 210)
		{
			phrase = TFC_Core.translate("gui.ItemHeat.Hot");
			if(temp>80+((210-80) * 0.2))
				phrase = phrase + "\u2605";
			if(temp>80+((210-80) * 0.4))
				phrase = phrase + "\u2605";
			if(temp>80+((210-80) * 0.6))
				phrase = phrase + "\u2605";
			if(temp>80+((210-80) * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 210 &&  temp < 480)
		{
			phrase = TFC_Core.translate("gui.ItemHeat.VeryHot");
			if(temp>210+((480-210) * 0.2))
				phrase = phrase + "\u2605";
			if(temp>210+((480-210) * 0.4))
				phrase = phrase + "\u2605";
			if(temp>210+((480-210) * 0.6))
				phrase = phrase + "\u2605";
			if(temp>210+((480-210) * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 480 &&  temp < 580)
		{
			phrase = "\2474" + TFC_Core.translate("gui.ItemHeat.FaintRed");
			if(temp>480+((580-480) * 0.2))
				phrase = phrase + "\u2605";
			if(temp>480+((580-480) * 0.4))
				phrase = phrase + "\u2605";
			if(temp>480+((580-480) * 0.6))
				phrase = phrase + "\u2605";
			if(temp>480+((580-480) * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 580 &&  temp < 730)
		{
			phrase = "\2474" + TFC_Core.translate("gui.ItemHeat.DarkRed");
			if(temp>580+((730-580) * 0.2))
				phrase = phrase + "\u2605";
			if(temp>580+((730-580) * 0.4))
				phrase = phrase + "\u2605";
			if(temp>580+((730-580) * 0.6))
				phrase = phrase + "\u2605";
			if(temp>580+((730-580) * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 730 &&  temp < 930)
		{
			phrase = "\247c" + TFC_Core.translate("gui.ItemHeat.BrightRed");
			if(temp>730+((930-730) * 0.2))
				phrase = phrase + "\u2605";
			if(temp>730+((930-730) * 0.4))
				phrase = phrase + "\u2605";
			if(temp>730+((930-730) * 0.6))
				phrase = phrase + "\u2605";
			if(temp>730+((930-730) * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 930 &&  temp < 1100)
		{
			phrase = "\2476" + TFC_Core.translate("gui.ItemHeat.Orange");
			if(temp>930+((1100-930) * 0.2))
				phrase = phrase + "\u2605";
			if(temp>930+((1100-930) * 0.4))
				phrase = phrase + "\u2605";
			if(temp>930+((1100-930) * 0.6))
				phrase = phrase + "\u2605";
			if(temp>930+((1100-930) * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1100 &&  temp < 1300)
		{
			phrase = "\247e" + TFC_Core.translate("gui.ItemHeat.Yellow");
			if(temp>1100+((1300-1100) * 0.2))
				phrase = phrase + "\u2605";
			if(temp>1100+((1300-1100) * 0.4))
				phrase = phrase + "\u2605";
			if(temp>1100+((1300-1100) * 0.6))
				phrase = phrase + "\u2605";
			if(temp>1100+((1300-1100) * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1300 &&  temp < 1400)
		{
			phrase = "\247e" + TFC_Core.translate("gui.ItemHeat.YellowWhite");
			if(temp>1300+((1400-1300) * 0.2))
				phrase = phrase + "\u2605";
			if(temp>1300+((1400-1300) * 0.4))
				phrase = phrase + "\u2605";
			if(temp>1300+((1400-1300) * 0.6))
				phrase = phrase + "\u2605";
			if(temp>1300+((1400-1300) * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1400 &&  temp < 1500)
		{
			phrase = "\247f" + TFC_Core.translate("gui.ItemHeat.White");
			if(temp>1400+((1500-1400) * 0.2))
				phrase = phrase + "\u2605";
			if(temp>1400+((1500-1400) * 0.4))
				phrase = phrase + "\u2605";
			if(temp>1400+((1500-1400) * 0.6))
				phrase = phrase + "\u2605";
			if(temp>1400+((1500-1400) * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1500)
			phrase = "\247f" + TFC_Core.translate("gui.ItemHeat.BrilliantWhite");

		if(temp > meltTemp)
			phrase = phrase + "\247f - " + TFC_Core.translate("gui.ItemHeat.Liquid");

		return phrase;
	}

	public static String getHeatColorFood(float temp, float meltTemp)
	{
		if(temp < meltTemp)
		{
			if(temp < meltTemp*0.1F)
				return TFC_Core.translate("gui.FoodHeat.Cold");
			else if(temp >= meltTemp*0.1F && temp < meltTemp*0.4F)
				return "\2474" + TFC_Core.translate("gui.FoodHeat.Warm");
			else if(temp >= meltTemp*0.4F && temp < meltTemp*0.8F)
				return "\2474" + TFC_Core.translate("gui.ItemHeat.Hot");
			else
				return "\2474" + TFC_Core.translate("gui.ItemHeat.VeryHot");
		}
		return TFC_Core.translate("gui.ClearSlot");
	}

	public static String getHeatColorTorch(float temp, float meltTemp)
	{
		if(temp < meltTemp)
		{
			if(temp > 0 && temp < meltTemp*0.8F)
				return TFC_Core.translate("gui.Torch.CatchingFire");
			else if(temp >= meltTemp*0.8F)
				return "\2474" + TFC_Core.translate("gui.Torch.Lit");
		}
		return TFC_Core.translate("gui.ClearSlot");
	}

	public static Boolean getIsLiquid(ItemStack is)
	{
		return getTemp(is) >= isCookable(is);
	}

	public static float isCookable(ItemStack is)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		if(is != null && manager!=null)
		{
			HeatIndex hi = manager.findMatchingIndex(is);
			if(hi != null)
				return hi.meltTemp;
			else
				return -1;
		}
		else
			return -1;
	}

	public static float getSpecificHeat(ItemStack is)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		if(is != null && manager!=null)
		{
			HeatIndex hi = manager.findMatchingIndex(is);
			if(hi != null)
				return hi.specificHeat;
			else
				return 1;
		}
		else
			return 1;
	}

	public static float isCookable(Metal m)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager!=null)
		{
			HeatIndex hi = manager.findMatchingIndex(new ItemStack(m.meltedItem));
			if(hi != null)
				return hi.meltTemp;
			else
				return -1;
		}
		else
			return -1;
	}

	public static float getTemp(ItemStack is)
	{
		if(hasTemp(is))
		{
			return is.getTagCompound().getFloat("temperature");
		}
		return 0;
	}

	public static boolean hasTemp(ItemStack is)
	{
		if(is != null)
		{
			if(is.hasTagCompound() && is.getTagCompound().hasKey("temperature"))
				return true;
		}
		return false;
	}

	public static float getTempIncrease(ItemStack is)
	{
		byte debugBump = 0;
		if(TFCOptions.enableDebugMode)
			debugBump = 2;
		return TFCOptions.tempIncreaseMultiplier * getSpecificHeat(is) + debugBump;
	}

	public static float getTempDecrease(ItemStack is)
	{
		if(TFCOptions.enableDebugMode)
			return 0;
		return TFCOptions.tempDecreaseMultiplier * getSpecificHeat(is);
	}

	public static void handleItemHeat(ItemStack is)
	{
		if (is != null)
		{
			if(is.hasTagCompound())
			{
				NBTTagCompound comp = is.getTagCompound();
				if(hasTemp(is))
				{
					float temp = getTemp(is);
					if(temp > 0)
					{
						temp -= getTempDecrease(is);
						comp.setFloat("temperature",temp);
					}
					if(temp <= 0)
						comp.removeTag("temperature");
					if(comp.hasNoTags())
						is.stackTagCompound = null;
				}
			}
		}
	}

	public static Boolean setTemp(ItemStack is, float temp)
	{
		if(is != null)
		{
			if(is.hasTagCompound())
				is.getTagCompound().setFloat("temperature", temp);
			else if(isCookable(is) != -1)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setFloat("temperature", temp);
				is.setTagCompound(nbt);
			}
		}
		else
			return false;

		if(temp <= 0)
			removeTempTag(is);

		return true;
	}

	public static void removeTempTag(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("temperature"))
		{
			is.getTagCompound().removeTag("temperature");
		}
		if(is.hasTagCompound() && is.getTagCompound().hasNoTags())
			is.stackTagCompound = null;
	}
}

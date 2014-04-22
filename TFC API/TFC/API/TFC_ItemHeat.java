package TFC.API;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import TFC.Core.Util.StringUtil;

public class TFC_ItemHeat 
{
	public static String getHeatColor(int temp, int meltTemp)
	{
		// \u2605 equals *
		String phrase = "";
		if(temp < 100)
		{
			phrase = StringUtil.localize("gui.ItemHeat.Warming");
			if(temp>(80 * 0.2))
				phrase = phrase + "\u2605";
			if(temp>(80 * 0.4))
				phrase = phrase + "\u2605";
			if(temp>(80 * 0.6))
				phrase = phrase + "\u2605";
			if(temp>(80 * 0.8))
				phrase = phrase + "\u2605";
		}
		else if(temp >= 100 && temp < 200)
		{
			phrase = StringUtil.localize("gui.ItemHeat.Hot");
			if(temp>120)
				phrase = phrase + "\u2605";
			if(temp>140)
				phrase = phrase + "\u2605";
			if(temp>160)
				phrase = phrase + "\u2605";
			if(temp>180)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 200 &&  temp < 400)
		{
			phrase = StringUtil.localize("gui.ItemHeat.VeryHot");
			if(temp>240)
				phrase = phrase + "\u2605";
			if(temp>280)
				phrase = phrase + "\u2605";
			if(temp>320)
				phrase = phrase + "\u2605";
			if(temp>360)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 400 &&  temp < 500)
		{
			phrase = "\2474" + StringUtil.localize("gui.ItemHeat.FaintRed");
			if(temp>420)
				phrase = phrase + "\u2605";
			if(temp>440)
				phrase = phrase + "\u2605";
			if(temp>460)
				phrase = phrase + "\u2605";
			if(temp>480)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 500 &&  temp < 700)
		{
			phrase = "\2474" + StringUtil.localize("gui.ItemHeat.DarkRed");
			if(temp>540)
				phrase = phrase + "\u2605";
			if(temp>580)
				phrase = phrase + "\u2605";
			if(temp>620)
				phrase = phrase + "\u2605";
			if(temp>660)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 700 &&  temp < 800)
		{
			phrase = "\247c" + StringUtil.localize("gui.ItemHeat.BrightRed");
			if(temp>720)
				phrase = phrase + "\u2605";
			if(temp>740)
				phrase = phrase + "\u2605";
			if(temp>760)
				phrase = phrase + "\u2605";
			if(temp>780)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 800 &&  temp < 900)
		{
			phrase = "\2476" + StringUtil.localize("gui.ItemHeat.Orange");
			if(temp>920)
				phrase = phrase + "\u2605";
			if(temp>940)
				phrase = phrase + "\u2605";
			if(temp>960)
				phrase = phrase + "\u2605";
			if(temp>980)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 900 &&  temp < 1000)
		{
			phrase = "\247e" + StringUtil.localize("gui.ItemHeat.Yellow");
			if(temp>920)
				phrase = phrase + "\u2605";
			if(temp>940)
				phrase = phrase + "\u2605";
			if(temp>960)
				phrase = phrase + "\u2605";
			if(temp>980)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1000 &&  temp < 1100)
		{
			phrase = "\247e" + StringUtil.localize("gui.ItemHeat.YellowWhite");
			if(temp>1020)
				phrase = phrase + "\u2605";
			if(temp>1040)
				phrase = phrase + "\u2605";
			if(temp>1060)
				phrase = phrase + "\u2605";
			if(temp>1080)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1100 &&  temp < 1200)
		{
			phrase = "\247f" + StringUtil.localize("gui.ItemHeat.White");
			if(temp>1120)
				phrase = phrase + "\u2605";
			if(temp>1140)
				phrase = phrase + "\u2605";
			if(temp>1160)
				phrase = phrase + "\u2605";
			if(temp>1180)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1200)
			phrase = "\247f" + StringUtil.localize("gui.ItemHeat.BrilliantWhite");

		if(temp > meltTemp)
			phrase = phrase + "\247f - " + StringUtil.localize("gui.ItemHeat.Liquid");

		return phrase;
	}

	public static String getHeatColorFood(int temp, int meltTemp)
	{
		if(temp < meltTemp)
			if(temp < 40)
				return StringUtil.localize("gui.FoodHeat.Cold");
			else if(temp < 80)
				return "\2474" + StringUtil.localize("gui.FoodHeat.Warm");
			else if(temp < 120)
				return "\2474" + StringUtil.localize("gui.ItemHeat.Hot");
			else
				return "\2474" + StringUtil.localize("gui.ItemHeat.VeryHot");

		return StringUtil.localize("gui.ClearSlot");
	}

	public static String getHeatColorTorch(float temp, float meltTemp)
	{
		if(temp < meltTemp)
			if(temp < 80)
				return StringUtil.localize("gui.Torch.CatchingFire");

		return StringUtil.localize("gui.ClearSlot");
	}

	public static Boolean getIsLiquid(ItemStack is)
	{       
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager != null && is != null)
		{
			HeatIndex hi = manager.findMatchingIndex(is);
			if(hi != null && is.hasTagCompound())
			{
				short temp = 0;
				if(is.getTagCompound().hasKey("temp"))
					temp = is.getTagCompound().getShort("temp");
				return temp >= hi.ticksToCook;
			} else
				return false;
		} else
			return false;
	}

	public static int IsCookable(ItemStack is)
	{       
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager!=null)
		{
			HeatIndex hi = manager.findMatchingIndex(is);
			if(hi != null)
				return hi.ticksToCook;
			else
				return -1;
		} else
			return -1;
	}

	public static int IsCookable(Metal m)
	{       
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager!=null)
		{
			HeatIndex hi = manager.findMatchingIndex(new ItemStack(Item.itemsList[m.MeltedItemID]));
			if(hi != null)
				return hi.ticksToCook;
			else
				return -1;
		} else
			return -1;
	}

	public static short GetTemp(ItemStack is)
	{
		if(HasTemp(is))
		{
			return is.getTagCompound().getShort("temp");
		} 
		return 0;
	}

	public static boolean HasTemp(ItemStack is)
	{
		if(is != null)
		{
			if(is.hasTagCompound() && is.getTagCompound().hasKey("temp"))
				return true;
		} 
		return false;
	}

	public static short getTempIncrease(ItemStack is)
	{
		byte debugBump = 0;
		if(TFCOptions.enableDebugMode)
			debugBump = 2;
		return (short)(1 + debugBump);
	}

	public static void HandleItemHeat(ItemStack is)
	{
		if (is != null)
		{
			if(is.hasTagCompound())
			{
				NBTTagCompound comp = is.getTagCompound();
				if(comp.hasKey("temp"))
				{
					short temp = comp.getShort("temp");
					if(temp > 0)
					{
						temp -= 1;
						comp.setShort("temp",temp);
					}
					if(temp <= 0)
						comp.removeTag("temp");
					if(comp.getTags().size() == 0)
						is.stackTagCompound = null;
				}
			}
		}
	}

	public static Boolean SetTemp(ItemStack is, int Temp)
	{
		if(is != null)
		{
			if(is.hasTagCompound())
				is.getTagCompound().setShort("temp", (short)Temp);
			else if(IsCookable(is) != -1)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setShort("temp", (short)Temp);
				is.setTagCompound(nbt);
			}
		} 
		else return false;

		if(Temp <= 0)
			RemoveTempTag(is);

		return true;
	}

	public static void RemoveTempTag(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("temp"))
		{
			is.getTagCompound().removeTag("temp");
		}
	}
}

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
		if(temp < 400)
		{
			phrase = StringUtil.localize("gui.ItemHeat.Warming");
			if(temp>80)
				phrase = phrase + "\u2605";
			if(temp>160)
				phrase = phrase + "\u2605";
			if(temp>240)
				phrase = phrase + "\u2605";
			if(temp>320)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 400 && temp < 800)
		{
			phrase = StringUtil.localize("gui.ItemHeat.Hot");
			if(temp>480)
				phrase = phrase + "\u2605";
			if(temp>560)
				phrase = phrase + "\u2605";
			if(temp>640)
				phrase = phrase + "\u2605";
			if(temp>720)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 800 &&  temp < 1100)
		{
			phrase = StringUtil.localize("gui.ItemHeat.VeryHot");
			if(temp>860)
				phrase = phrase + "\u2605";
			if(temp>920)
				phrase = phrase + "\u2605";
			if(temp>980)
				phrase = phrase + "\u2605";
			if(temp>1140)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1100 &&  temp < 1400)
		{
			phrase = "\2474" + StringUtil.localize("gui.ItemHeat.FaintRed");
			if(temp>1160)
				phrase = phrase + "\u2605";
			if(temp>1220)
				phrase = phrase + "\u2605";
			if(temp>1280)
				phrase = phrase + "\u2605";
			if(temp>1340)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1400 &&  temp < 1700)
		{
			phrase = "\2474" + StringUtil.localize("gui.ItemHeat.DarkRed");
			if(temp>1460)
				phrase = phrase + "\u2605";
			if(temp>1520)
				phrase = phrase + "\u2605";
			if(temp>1580)
				phrase = phrase + "\u2605";
			if(temp>1640)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 1700 &&  temp < 2000)
		{
			phrase = "\247c" + StringUtil.localize("gui.ItemHeat.BrightRed");
			if(temp>1760)
				phrase = phrase + "\u2605";
			if(temp>1820)
				phrase = phrase + "\u2605";
			if(temp>1880)
				phrase = phrase + "\u2605";
			if(temp>1940)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 2000 &&  temp < 2400)
		{
			phrase = "\2476" + StringUtil.localize("gui.ItemHeat.Orange");
			if(temp>2080)
				phrase = phrase + "\u2605";
			if(temp>2160)
				phrase = phrase + "\u2605";
			if(temp>2240)
				phrase = phrase + "\u2605";
			if(temp>2320)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 2400 &&  temp < 2700)
		{
			phrase = "\247e" + StringUtil.localize("gui.ItemHeat.Yellow");
			if(temp>2460)
				phrase = phrase + "\u2605";
			if(temp>2520)
				phrase = phrase + "\u2605";
			if(temp>2580)
				phrase = phrase + "\u2605";
			if(temp>2640)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 2700 &&  temp < 3000)
		{
			phrase = "\247e" + StringUtil.localize("gui.ItemHeat.YellowWhite");
			if(temp>2760)
				phrase = phrase + "\u2605";
			if(temp>2820)
				phrase = phrase + "\u2605";
			if(temp>2880)
				phrase = phrase + "\u2605";
			if(temp>2940)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 3000 &&  temp < 3500)
		{
			phrase = "\247f" + StringUtil.localize("gui.ItemHeat.White");
			if(temp>3100)
				phrase = phrase + "\u2605";
			if(temp>3200)
				phrase = phrase + "\u2605";
			if(temp>3300)
				phrase = phrase + "\u2605";
			if(temp>3400)
				phrase = phrase + "\u2605";
		}
		else if(temp >= 3500)
			phrase = "\247f" + StringUtil.localize("gui.ItemHeat.BrilliantWhite");

		if(temp > meltTemp)
			phrase = phrase + "\247f - " + StringUtil.localize("gui.ItemHeat.Liquid");

		return phrase;
	}

	public static String getHeatColorFood(int temp, int meltTemp)
	{
		if(temp < meltTemp)
			if(temp < 50)
				return StringUtil.localize("gui.FoodHeat.Cold");
			else if(temp < 100)
				return "\2474" + StringUtil.localize("gui.FoodHeat.Warm");
			else if(temp < 150)
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
		if(is.hasTagCompound() && is.getTagCompound().hasNoTags())
			is.stackTagCompound = null;
	}
}

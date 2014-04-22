package TFC.API;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;


public class HeatRegistry
{
	private static final HeatRegistry instance = new HeatRegistry();
	public static final HeatRegistry getInstance()
	{
		return instance;
	}

	private List heatList;

	private HeatRegistry()
	{
		heatList = new ArrayList();
	}

	public void addIndex(HeatIndex index)
	{
		heatList.add(index);
	}

	public HeatIndex findMatchingIndex(ItemStack input)
	{
		for (int k = 0; k < heatList.size(); k++)
		{
			HeatIndex tempIndex = (HeatIndex)heatList.get(k);
			if (tempIndex.matches(input))
			{
				return tempIndex;
			}
		}

		return null;
	}

	public Boolean getIsLiquid(ItemStack is)
	{       
		HeatIndex hi = instance.findMatchingIndex(is);
		if(hi != null && is.hasTagCompound())
		{
			float temp = 0;
			if(TFC_ItemHeat.HasTemp(is)) {
				temp = TFC_ItemHeat.GetTemp(is);
			}
			return temp >= hi.ticksToCook;
		} else {
			return false;
		}
	}

	public float getMeltingPoint(ItemStack is)
	{       
		HeatIndex hi = findMatchingIndex(is);
		if(hi != null)
		{
			return hi.ticksToCook;
		} else {
			return -1;
		}

	}
}

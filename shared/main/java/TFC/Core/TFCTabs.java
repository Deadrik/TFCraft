package TFC.Core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import TFC.Core.Util.StringUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCTabs extends CreativeTabs 
{	
	public static CreativeTabs TFCMisc = new TFCTabs("TFCMisc");
	public static CreativeTabs TFCTools = new TFCTabs("TFCTools");
	public static CreativeTabs TFCWeapons = new TFCTabs("TFCWeapons");
	public static CreativeTabs TFCUnfinished = new TFCTabs("TFCUnfinished");
	public static CreativeTabs TFCMaterials = new TFCTabs("TFCMaterials");
	public static CreativeTabs TFCArmor = new TFCTabs("TFCArmor");
	public static CreativeTabs TFCPottery = new TFCTabs("TFCPottery");

	private int itemIndex;
	private ItemStack is;
	public TFCTabs(String par2Str) 
	{
		super(par2Str);
	}
	public TFCTabs(String par2Str, int icon) 
	{
		super(par2Str);
		itemIndex = icon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getTabIconItemIndex()
	{
		return itemIndex;
	}

	public void setTabIconItemIndex(int i)
	{
		is = new ItemStack(Item.itemsList[i]);
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return is;
	}

	public void setTabIconItemStack(ItemStack i)
	{
		is = i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return StringUtil.localize("itemGroup." + this.getTabLabel());
	}

}

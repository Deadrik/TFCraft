package TFC.Core;

import TFC.TFCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class TFCTabs extends CreativeTabs 
{	
	public static CreativeTabs TFCTools = new TFCTabs("TFCTools");
	public static CreativeTabs TFCUnfinished = new TFCTabs("TFCUnfinished");
	public static CreativeTabs TFCArmor = new TFCTabs("TFCArmor");
	
	private int itemIndex;
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
        itemIndex = i;
    }

}

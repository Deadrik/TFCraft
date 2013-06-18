package TFC.API;

import net.minecraft.creativetab.CreativeTabs;
import TFC.Core.Util.StringUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCTabs extends CreativeTabs 
{	
	public static CreativeTabs TFCTools = new TFCTabs("TFCTools");
	public static CreativeTabs TFCUnfinished = new TFCTabs("TFCUnfinished");
	public static CreativeTabs TFCMaterials = new TFCTabs("TFCMaterials");
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

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return StringUtil.localize("itemGroup." + this.getTabLabel());
	}

}

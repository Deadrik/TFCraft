package TFC.Items;

import TFC.Enums.EnumSize;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemMiscTool extends ItemTerra
{

	public ItemMiscTool(int i) 
	{
		super(i);
		this.setTabToDisplayOn(CreativeTabs.tabMisc);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}	
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
}

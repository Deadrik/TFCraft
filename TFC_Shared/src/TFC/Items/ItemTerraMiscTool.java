package TFC.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemTerraMiscTool extends ItemTerra
{

	public ItemTerraMiscTool(int i) 
	{
		super(i);
		this.setTabToDisplayOn(CreativeTabs.tabMisc);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}	
}

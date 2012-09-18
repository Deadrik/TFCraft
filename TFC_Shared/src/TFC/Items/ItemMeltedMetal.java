package TFC.Items;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemMeltedMetal extends ItemTerra
{
	public ItemMeltedMetal(int i) 
	{
		super(i);
		setMaxDamage(100);
		this.setTabToDisplayOn(CreativeTabs.tabMaterials);
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites.png";
	}	
	
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.HEAVY;
	}
	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public boolean canStack() 
	{
		return false;
	}
}

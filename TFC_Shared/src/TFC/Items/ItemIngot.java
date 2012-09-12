package TFC.Items;

import TFC.Enums.EnumSize;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemIngot extends ItemTerra
{

	public ItemIngot(int i) 
	{
		super(i);
		this.size = EnumSize.MEDIUM;
		this.setTabToDisplayOn(CreativeTabs.tabMaterials);
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this));
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites.png";
	}

}

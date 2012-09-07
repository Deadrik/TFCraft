package TFC.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemTerraIngot extends ItemTerra
{

	public ItemTerraIngot(int i) 
	{
		super(i);
		this.maxStackSize = 8;
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

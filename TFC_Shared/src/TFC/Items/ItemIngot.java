package TFC.Items;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemIngot extends ItemTerra
{
	EnumSize size = EnumSize.SMALL;
	public ItemIngot(int i) 
	{
		super(i);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@Override
	public EnumSize getSize() {
		return size;
	}
	
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.HEAVY;
	}
	
	public ItemIngot setSize(EnumSize s)
	{
		size = s;
		return this;
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

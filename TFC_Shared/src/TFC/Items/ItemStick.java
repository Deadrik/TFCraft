package TFC.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemStick extends ItemTerra
{
	public ItemStick(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return EnumSize.TINY;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		return EnumWeight.LIGHT;
	}
}

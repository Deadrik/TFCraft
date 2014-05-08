package TFC.Items.Tools;

import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumItemReach;
import TFC.API.Enums.EnumSize;
import TFC.Items.ItemTerra;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemSpindle extends ItemTerra
{
	public ItemSpindle(int i)
	{
		super(i);
		this.setMaxDamage(40);
		this.setFolder("tools/");

		this.setSize(EnumSize.VERYSMALL);
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		return multimap;
	}
	
	@Override
	public EnumItemReach getReach(ItemStack is){
		return EnumItemReach.SHORT;
	}

	@Override
	public int getItemStackLimit()
	{
		return 1;
	}
}
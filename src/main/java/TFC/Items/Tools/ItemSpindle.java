package TFC.Items.Tools;

import TFC.API.Enums.EnumSize;
import TFC.Items.ItemTerra;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemSpindle extends ItemTerra
{
	public ItemSpindle()
	{
		super();
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
}
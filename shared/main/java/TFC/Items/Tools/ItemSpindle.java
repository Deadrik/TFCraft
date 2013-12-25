package TFC.Items.Tools;

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
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.VERYSMALL;
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		return multimap;
	}
}
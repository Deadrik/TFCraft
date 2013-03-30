package TFC.Items;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public class ItemAnvil2 extends ItemTerraBlock
{
	public ItemAnvil2(int i) 
	{
		super(i);
		this.MetaNames = new String[]{"Bismuth Bronze", "Black Bronze", "Rose Gold"};
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight() {
		return EnumWeight.HEAVY;
	}
}

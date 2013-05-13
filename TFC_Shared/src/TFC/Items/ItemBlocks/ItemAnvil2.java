package TFC.Items.ItemBlocks;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public class ItemAnvil2 extends ItemTerraBlock
{
	public ItemAnvil2(int i) 
	{
		super(i);
		this.MetaNames = new String[]{ "Rose Gold", "Bismuth Bronze", "Black Bronze"};
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

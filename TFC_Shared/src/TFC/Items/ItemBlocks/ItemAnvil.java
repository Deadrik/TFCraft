package TFC.Items.ItemBlocks;

import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemAnvil extends ItemTerraBlock
{
	public ItemAnvil(int i) 
	{
		super(i);
		this.MetaNames = new String[]{"Stone", "Copper", "Bronze", "Wrought Iron", "Steel", "Black Steel", "Blue Steel", "Red Steel"};
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

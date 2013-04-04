package TFC.Items;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

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

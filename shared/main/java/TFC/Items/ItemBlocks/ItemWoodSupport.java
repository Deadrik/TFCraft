package TFC.Items.ItemBlocks;

import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;

public class ItemWoodSupport extends ItemTerraBlock
{
	public ItemWoodSupport(int i) 
	{
		super(i);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.MetaNames = Global.WOOD_ALL;
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
}

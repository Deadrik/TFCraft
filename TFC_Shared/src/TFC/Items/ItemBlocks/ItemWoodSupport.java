package TFC.Items.ItemBlocks;

import TFC.Enums.EnumSize;

public class ItemWoodSupport extends ItemTerraBlock
{
	public ItemWoodSupport(int i) 
	{
		super(i);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.MetaNames = new String[]{"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
				"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
	}
	
	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
}

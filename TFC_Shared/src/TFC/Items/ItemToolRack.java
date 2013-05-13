package TFC.Items;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.Items.ItemBlocks.ItemTerraBlock;

public class ItemToolRack extends ItemTerraBlock
{	
	public ItemToolRack(int id) {
		super(id);
		this.MetaNames = new String[]{"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
				"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight() {
		return EnumWeight.LIGHT;
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}
}

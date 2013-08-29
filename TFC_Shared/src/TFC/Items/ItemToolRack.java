package TFC.Items;

import net.minecraft.item.ItemStack;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Items.ItemBlocks.ItemTerraBlock;

public class ItemToolRack extends ItemTerraBlock
{	
	public ItemToolRack(int id) {
		super(id);
		this.MetaNames = Global.WOOD_ALL;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.LIGHT;
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}
}

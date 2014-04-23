package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemPipeValve extends ItemTerraBlock
{
	public ItemPipeValve(int i) 
	{
		super(i);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{

	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.MEDIUM;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.MEDIUM;
	}
}
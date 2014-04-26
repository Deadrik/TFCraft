package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemPipeValve extends ItemTerraBlock
{
	public ItemPipeValve(Block par1) 
	{
		super(par1);
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
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
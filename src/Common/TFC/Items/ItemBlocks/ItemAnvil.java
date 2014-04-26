package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import TFC.API.ISmeltable;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public abstract class ItemAnvil extends ItemTerraBlock implements ISmeltable
{
	public ItemAnvil(Block par1)
	{
		super(par1);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.HEAVY;
	}

	@Override
	public short GetMetalReturnAmount(ItemStack is) {

		return 1400;
	}

	@Override
	public boolean isSmeltable(ItemStack is) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public EnumTier GetSmeltTier(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumTier.TierI;
	}
}

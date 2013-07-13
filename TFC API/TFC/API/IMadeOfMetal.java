package TFC.API;

import net.minecraft.item.ItemStack;

public interface IMadeOfMetal 
{
	public Metal GetMetalType(ItemStack is);
	
	/**
	 * @return Percent of a full Unshaped Mold 0-100. Beyond 100 will give more than one mold worth of metal.
	 */
	public int GetMetalReturnAmount(ItemStack is);
}

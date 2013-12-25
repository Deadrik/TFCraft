package TFC.API;

import net.minecraft.item.ItemStack;

public interface ISmeltable 
{
	public Metal GetMetalType(ItemStack is);
	
	/**
	 * @return Percent of a full Unshaped Mold 0-100. Beyond 100 will give more than one mold worth of metal.
	 */
	public short GetMetalReturnAmount(ItemStack is);
	
	public boolean isSmeltable(ItemStack is);
	
	public EnumTier GetSmeltTier(ItemStack is);
	
	public enum EnumTier
	{
		TierI(1),TierII(2),TierIII(3),TierIV(4), TierV(5), TierVI(6), TierVII(7), TierVIII(8), TierIX(9), TierX(10);
		
		public int tier;
		
		EnumTier(int t)
		{
			tier = t;
		}
	}
}

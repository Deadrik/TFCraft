package TFC.Items;

import net.minecraft.src.*;

public class ItemCustomSeeds extends Item
{
	/**
	 * The type of block this seed turns into (wheat or pumpkin stems for instance)
	 */
	private int blockType;

	/** BlockID of the block the seeds can be planted on. */
	private int soilBlockID;
	private int soilBlockID2;

	public ItemCustomSeeds(int par1, int par2, int par3, int par4)
	{
		super(par1);
		this.blockType = par2;
		this.soilBlockID = par3;
		this.soilBlockID2 = par4;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
	{
		if (par7 != 1)
		{
			return false;
		}
		else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6))
		{
			int var8 = par3World.getBlockId(par4, par5, par6);

			if ((var8 == this.soilBlockID || var8 == this.soilBlockID2) && par3World.isAirBlock(par4, par5 + 1, par6))
			{
				par3World.setBlockWithNotify(par4, par5 + 1, par6, this.blockType);
				--par1ItemStack.stackSize;
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}

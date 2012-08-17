package TFC.Items;

import TFC.TileEntities.TileEntityCrop;
import net.minecraft.src.*;

public class ItemCustomSeeds extends Item
{
	/**
	 * The type of block this seed turns into (wheat or pumpkin stems for instance)
	 */
	private int cropId;

	/** BlockID of the block the seeds can be planted on. */
	private int soilBlockID;
	private int soilBlockID2;

	public ItemCustomSeeds(int par1, int cropId)
	{
		super(par1);
		this.soilBlockID = mod_TFC.tilledSoil.blockID;
		this.soilBlockID2 = mod_TFC.tilledSoil2.blockID;
		this.cropId = cropId;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int i, int j, int k, int par7)
	{
		if (par7 != 1)
		{
			return false;
		}
		else if (par2EntityPlayer.canPlayerEdit(i, j, k) && par2EntityPlayer.canPlayerEdit(i, j + 1, k))
		{
			int var8 = world.getBlockId(i, j, k);

			if ((var8 == this.soilBlockID || var8 == this.soilBlockID2) && world.isAirBlock(i, j + 1, k))
			{
				world.setBlockWithNotify(i, j + 1, k, Block.crops.blockID);
				TileEntityCrop te = ((TileEntityCrop)world.getBlockTileEntity(i, j+1, k));
				te.cropId = cropId;
				te.broadcast();
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

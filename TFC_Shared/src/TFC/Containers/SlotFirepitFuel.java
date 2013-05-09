package TFC.Containers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TFCBlocks;
import TFC.TFCItems;

public class SlotFirepitFuel extends Slot

{
	public SlotFirepitFuel(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == TFCItems.Logs.itemID || itemstack.itemID == Block.wood.blockID || itemstack.itemID == TFCBlocks.Peat.blockID) {
			return true;
		}
		return false;
	}

	@Override
	public int getSlotStackLimit()
    {
        return 1;
    }

	@Override
	public void putStack(ItemStack par1ItemStack) {
		if (par1ItemStack != null) par1ItemStack.stackSize = 1;
		super.putStack(par1ItemStack);
	}
}

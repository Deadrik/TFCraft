package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TFCBlocks;
import TFC.TFCItems;

public class SlotFirepitIn extends Slot
{
	public SlotFirepitIn(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	@Override
	public int getSlotStackLimit()
    {
	    return 1;
    }
	
	@Override
	public boolean isItemValid(ItemStack is)
    {
		if(is.itemID == TFCItems.Logs.itemID || is.itemID == TFCBlocks.Peat.blockID)
			return false;
        return true;
    }
	
	@Override
	public void putStack(ItemStack par1ItemStack) {
		if (par1ItemStack != null) par1ItemStack.stackSize = 1;
		super.putStack(par1ItemStack);
	}
}

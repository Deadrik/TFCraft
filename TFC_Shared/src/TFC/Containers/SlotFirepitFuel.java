package TFC.Containers;

import net.minecraft.src.*;

public class SlotFirepitFuel extends Slot

{
	public SlotFirepitFuel(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == TFCItems.Logs.shiftedIndex || itemstack.itemID == Block.wood.blockID || itemstack.itemID == TFCBlocks.terraPeat.blockID) {
			return true;
		}
		return false;
	}

//	public void onPickupFromSlot(ItemStack itemstack)
//	{
//		super.onPickupFromSlot(itemstack);
//	}
//	
	public int getSlotStackLimit()
    {
        return 1;
    }
//    
//    public void putStack(ItemStack par1ItemStack)
//    {
//        if(par1ItemStack != null){
//        ItemStack is = par1ItemStack;
//        par1ItemStack.stackSize -= 1;
//        is.stackSize = 1;
//        super.putStack(is);
//        }
//    }
}

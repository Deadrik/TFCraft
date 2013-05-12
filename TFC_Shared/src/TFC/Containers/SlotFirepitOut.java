package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TFCItems;

public class SlotFirepitOut extends Slot
{
	int slotMax;
	public SlotFirepitOut(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
		slotMax = 64;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if(itemstack.itemID == TFCItems.CeramicMold.itemID) {
			slotMax = 1;
			return true;
		}
		return false;
	}
	
	@Override
	public void putStack(ItemStack par1ItemStack) {
		if (par1ItemStack != null && par1ItemStack.itemID == TFCItems.CeramicMold.itemID) {
			par1ItemStack.stackSize = 1;
			slotMax = 1;
		} else {
			slotMax = 64;
		}
		super.putStack(par1ItemStack);
	}

	@Override
	public int getSlotStackLimit() {
		return slotMax;
	}
	
}

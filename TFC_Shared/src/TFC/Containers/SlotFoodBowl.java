package TFC.Containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotFoodBowl extends Slot {

	public SlotFoodBowl(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if(par1ItemStack.itemID == Item.bowlEmpty.itemID) {
			return true;
		}
		return false;
	}
}

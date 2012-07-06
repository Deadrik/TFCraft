package TFC.Containers;

import net.minecraft.src.*;

public class SlotScribePaper extends Slot
{
	EntityPlayer player;
	Container container;
	public SlotScribePaper(EntityPlayer entityplayer, IInventory iinventory, ContainerTerraScribe scribecontainer, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		player = entityplayer;
		container = scribecontainer;
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == Item.paper.shiftedIndex)
		{
			return true;
		}
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}

	public void onSlotChanged()
	{
		inventory.onInventoryChanged();
		//Updates the scribing table. The inventory doesn't matter.
		container.onCraftMatrixChanged(inventory);
	}
}

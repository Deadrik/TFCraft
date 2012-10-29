package TFC.Containers;

import TFC.*;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

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

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == Item.paper.shiftedIndex)
		{
			return true;
		}
		return false;
	}

	@Override
	public void onSlotChanged()
	{
		inventory.onInventoryChanged();
		//Updates the scribing table. The inventory doesn't matter.
		container.onCraftMatrixChanged(inventory);
	}
}

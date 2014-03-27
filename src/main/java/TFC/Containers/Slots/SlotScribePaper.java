package TFC.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TFCItems;
import TFC.Containers.ContainerScribe;

public class SlotScribePaper extends Slot
{
	EntityPlayer player;
	Container container;
	public SlotScribePaper(EntityPlayer entityplayer, IInventory iinventory, ContainerScribe scribecontainer, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		player = entityplayer;
		container = scribecontainer;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() == Items.paper || itemstack.getItem() == TFCItems.writabeBookTFC)
			return true;
		return false;
	}

	@Override
	public void onSlotChanged()
	{
		inventory.markDirty();
		//Updates the scribing table. The inventory doesn't matter.
		container.onCraftMatrixChanged(inventory);
	}
}

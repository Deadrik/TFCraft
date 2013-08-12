package TFC.Core.Player;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class PlayerInventory 
{
	public static void buildInventoryLayout(Container container, IInventory inventory, int x, int y, int barX, int barY)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlotToContainer(container, new Slot(inventory, k + i * 9 + 9, x + k * 18, y + i * 18));
			}
		}

		for(int j = 0; j < 9; j++)
		{
			addSlotToContainer(container, new Slot(inventory, j, barX + j * 18, barY));
		}
	}

	protected static Slot addSlotToContainer(Container container, Slot par1Slot)
	{
		par1Slot.slotNumber = container.inventorySlots.size();
		container.inventorySlots.add(par1Slot);
		container.inventoryItemStacks.add((Object)null);
		return par1Slot;
	}
}

package TFC.Containers;

import TFC.TileEntities.TileEntityTerraSluice;
import net.minecraft.src.*;

public class ContainerTerraSluice extends Container
{
	private TileEntityTerraSluice sluice;
	private int coolTime;
	private int freezeTime;
	private int itemFreezeTime;


	public ContainerTerraSluice(InventoryPlayer inventoryplayer, TileEntityTerraSluice tileentitysluice)
	{
		sluice = tileentitysluice;
		coolTime = 0;
		freezeTime = 0;
		itemFreezeTime = 0;

		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 0, 116, 16));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 1, 134, 16));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 2, 152, 16));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 3, 116, 34));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 4, 134, 34));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 5, 152, 34));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 6, 116, 52));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 7, 134, 52));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 8, 152, 52));
		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}

		}

		for(int j = 0; j < 9; j++)
		{
			addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
		}

	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}


	public ItemStack transferStackInSlot(int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slotpaper = (Slot)inventorySlots.get(1);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i <= 8)
			{
//				if(!ModLoader.getMinecraftInstance().thePlayer.inventory.addItemStackToInventory(itemstack1.copy()))
//				{
//					return null;
//				}
				slot.putStack(null);
			}
			if(itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			} else
			{
				slot.onSlotChanged();
			}
		}
		return null;
	}


	public void updateCraftingResults()
	{
		super.updateCraftingResults();
		for(int i = 0; i < inventorySlots.size(); i++)
		{
			ICrafting icrafting = (ICrafting)inventorySlots.get(i);
		}
	}


}
